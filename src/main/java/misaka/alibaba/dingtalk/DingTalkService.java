package misaka.alibaba.dingtalk;

import artoria.beans.BeanUtils;
import artoria.cache.Cache;
import artoria.codec.Base64Utils;
import artoria.exception.ExceptionUtils;
import artoria.exchange.JsonUtils;
import artoria.lang.Dict;
import artoria.net.HttpClient;
import artoria.net.HttpMethod;
import artoria.net.HttpRequest;
import artoria.net.HttpResponse;
import artoria.time.DateUtils;
import artoria.util.Assert;
import artoria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static artoria.common.Constants.UTF_8;

public class DingTalkService {
    private static Logger log = LoggerFactory.getLogger(DingTalkService.class);
    private final HttpClient httpClient;
    private final String appSecret;
    private final String appKey;
    private final Cache cache;

    public DingTalkService(HttpClient httpClient, Cache cache, String appKey, String appSecret) {
        this.httpClient = httpClient;
        this.appSecret = appSecret;
        this.appKey = appKey;
        this.cache = cache;
    }

    // 扫码登录第三方网站
    // https://developers.dingtalk.com/document/app/scan-qr-code-to-login-isvapp

    /**
     * 个人免登场景的签名计算方法
     * @see <a href="https://developers.dingtalk.com/document/app/signature-calculation-for-logon-free-scenarios-1">个人免登场景的签名计算方法</a>
     */
    public String calcSignature(String timeInMillis) {
        try {
            // 根据timestamp, appSecret计算签名值
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(appSecret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signatureBytes = mac.doFinal(timeInMillis.getBytes("UTF-8"));
            String signature = Base64Utils.encodeToString(signatureBytes);
            Assert.notBlank(signature, "Signature must not blank. ");
            return URLEncoder.encode(signature, "UTF-8");
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    /**
     * 钉钉获取企业内部应用的access_token
     * @see <a href="https://developers.dingtalk.com/document/app/obtain-orgapp-token">获取企业内部应用的access_token</a>
     */
    public Dict getAccessToken() {
        try {
            // https://oapi.dingtalk.com/gettoken?appkey=appkey&appsecret=appsecret
            String key = "ding-talk-access-token-key";
            Dict result = cache.get(key, Dict.class);
            if (result != null) { return result; }
            synchronized (this) {
                result = cache.get(key, Dict.class);
                if (result != null) { return result; }
                HttpRequest request = new HttpRequest();
                request.setMethod(HttpMethod.GET);
                request.setCharset(UTF_8);
                request.setUrl("https://oapi.dingtalk.com/gettoken");
                request.addParameter("appsecret", appSecret);
                request.addParameter("appkey", appKey);
                HttpResponse response = httpClient.execute(request);
                String bodyAsString = response.getBodyAsString();
                log.info("DingTalk get token: {} - {}", appKey, bodyAsString);
                result = JsonUtils.parseObject(bodyAsString, Dict.class);
                String errcode = result.getString("errcode");
                String errmsg = result.getString("errmsg");
                if (!"0".equals(errcode)) {
                    throw new DingTalkException(errmsg+" ("+errcode+")");
                }
                result.delete("errcode", "errmsg");
                cache.putIfAbsent(key, result, 600, TimeUnit.SECONDS);
                // {"errcode":0,"access_token":"1111222233334444","errmsg":"ok","expires_in":7200}
                return result;
            }
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    /**
     * 根据sns临时授权码获取用户信息
     * @see <a href="https://developers.dingtalk.com/document/app/obtain-the-user-information-based-on-the-sns-temporary-authorization">根据sns临时授权码获取用户信息</a>
     */
    public Dict getUserInfo(String tmpAuthCode) {
        try {
            // https://oapi.dingtalk.com/sns/getuserinfo_bycode?accessKey=ACCESS_KEY&timestamp=TIMESTAMP&signature=SIGNATURE
            HttpRequest request = new HttpRequest();
            request.setMethod(HttpMethod.POST);
            request.setCharset(UTF_8);
            request.addHeader("Content-Type", "application/json");
            String timeInMillis = String.valueOf(DateUtils.getTimeInMillis());
            String signature = calcSignature(timeInMillis);
            request.setUrl("https://oapi.dingtalk.com/sns/getuserinfo_bycode?accessKey="+appKey+"&timestamp="+timeInMillis+"&signature="+signature);
            request.setBody(JsonUtils.toJsonString(Dict.of("tmp_auth_code", tmpAuthCode)));
            HttpResponse response = httpClient.execute(request);
            String bodyAsString = response.getBodyAsString();
            log.info("DingTalk get user info: {} - {}", appKey, bodyAsString);
            Dict parseObject = JsonUtils.parseObject(bodyAsString, Dict.class);
            String errcode = parseObject.getString("errcode");
            String errmsg = parseObject.getString("errmsg");
            if (!"0".equals(errcode)) {
                throw new DingTalkException(errmsg+" ("+errcode+")");
            }
            Map<String, Object> userInfo = BeanUtils.beanToMap(parseObject.get("user_info"));
            if (userInfo == null) {
                throw new DingTalkException("User info is null.");
            }
            // {"nick":"nick name","unionid":"dingdkjjojoixxxx","openid":"dingsdsqwlklklxxxx","main_org_auth_high_level":true}
            return Dict.of(userInfo);
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    /**
     * 获取空间列表
     * @see <a href="https://developers.dingtalk.com/document/app/queries-a-space-list">获取空间列表</a>
     */
    public Dict getSpaces(Dict dict) {
        try {
            // https://api.dingtalk.com/v1.0/drive/spaces?unionId=String&spaceType=String&nextToken=String&maxResults=Integer
            HttpRequest request = new HttpRequest();
            request.setMethod(HttpMethod.GET);
            request.setCharset(UTF_8);
            request.addHeader("x-acs-dingtalk-access-token", String.valueOf(getAccessToken().get("access_token")));
            request.setUrl("https://api.dingtalk.com/v1.0/drive/spaces");
            request.addParameter("unionId", dict.get("unionId"));
            request.addParameter("spaceType", dict.get("spaceType"));
            request.addParameter("nextToken", dict.get("nextToken"));
            request.addParameter("maxResults", dict.get("maxResults"));
            HttpResponse response = httpClient.execute(request);
            String bodyAsString = response.getBodyAsString();
            log.info("DingTalk get spaces: {} - {}", appKey, bodyAsString);
            return JsonUtils.parseObject(bodyAsString, Dict.class);
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    /**
     * 查询文件（夹）列表
     * @see <a href="https://developers.dingtalk.com/document/app/obtain-the-file-list">查询文件（夹）列表</a>
     */
    public Dict getFiles(Dict dict) {
        try {
            // https://api.dingtalk.com/v1.0/drive/spaces/{spaceId}/files?unionId=String&parentId=String&nextToken=String&maxResults=Integer
            HttpRequest request = new HttpRequest();
            request.setMethod(HttpMethod.GET);
            request.setCharset(UTF_8);
            request.addHeader("x-acs-dingtalk-access-token", String.valueOf(getAccessToken().get("access_token")));
            String spaceId = dict.getString("spaceId");
            request.setUrl("https://api.dingtalk.com/v1.0/drive/spaces/" + spaceId + "/files");
            request.addParameter("unionId", dict.get("unionId"));
            request.addParameter("parentId", dict.get("parentId"));
            request.addParameter("nextToken", dict.get("nextToken"));
            request.addParameter("maxResults", dict.get("maxResults"));
            HttpResponse response = httpClient.execute(request);
            String bodyAsString = response.getBodyAsString();
//            log.info("DingTalk get spaces: {} - {}", appKey, bodyAsString);
            return JsonUtils.parseObject(bodyAsString, Dict.class);
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    /**
     * 获取文件下载信息
     * @see <a href="https://developers.dingtalk.com/document/app/obtain-download-file-info">获取文件下载信息</a>
     */
    public Dict downloadInfo(Dict dict) {
        try {
            // https://api.dingtalk.com/v1.0/drive/spaces/{spaceId}/files/{fileId}/downloadInfos?unionId=String
            HttpRequest request = new HttpRequest();
            request.setMethod(HttpMethod.GET);
            request.setCharset(UTF_8);
            request.addHeader("x-acs-dingtalk-access-token", String.valueOf(getAccessToken().get("access_token")));
            String spaceId = dict.getString("spaceId");
            String fileId = dict.getString("fileId");
            request.setUrl("https://api.dingtalk.com/v1.0/drive/spaces/"+spaceId+"/files/"+fileId+"/downloadInfos");
            request.addParameter("unionId", dict.get("unionId"));
            HttpResponse response = httpClient.execute(request);
            String bodyAsString = response.getBodyAsString();
            int statusCode = response.getStatusCode();
            log.info("DingTalk download info: {} - {} - {}", appKey, statusCode, bodyAsString);
            Dict parseObject = JsonUtils.parseObject(bodyAsString, Dict.class);
            String code = parseObject.getString("code");
            String message = parseObject.getString("message");
            boolean isError = StringUtils.isNotBlank(code)
                    && StringUtils.isNotBlank(message)
                    && !String.valueOf(statusCode).startsWith("20");
            if (isError) {
                throw new DingTalkException(message+" ("+code+")");
            }
            return parseObject.getDict("downloadInfo");
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }


    /*<dependency>
        <groupId>com.aliyun</groupId>
        <artifactId>alibaba-dingtalk-service-sdk</artifactId>
        <version>1.0.1</version>
    </dependency>*/

}

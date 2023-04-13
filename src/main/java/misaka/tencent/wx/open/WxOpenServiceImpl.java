package misaka.tencent.wx.open;

import artoria.data.bean.BeanUtils;
import artoria.data.json.JsonUtils;
import artoria.exception.ExceptionUtils;
import artoria.net.HttpUtils;
import artoria.util.Assert;
import artoria.util.StringUtils;
import artoria.util.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import static artoria.common.Constants.SIX;
import static artoria.common.Constants.ZERO;

/**
 * WeiXin open platform service implementation.
 * @author Kahle
 */
public class WxOpenServiceImpl implements WxOpenService {
    private static final String CODE_TO_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    private static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    private static Logger log = LoggerFactory.getLogger(WxOpenServiceImpl.class);
    private String appSecret;
    private String appId;

    public WxOpenServiceImpl(String appId, String appSecret) {
        Assert.notBlank(appSecret, "Parameter \"appSecret\" must not blank. ");
        Assert.notBlank(appId, "Parameter \"appId\" must not blank. ");
        this.appSecret = appSecret;
        this.appId = appId;
    }

    @Override
    public WxCode2AccessTokenResult code2AccessToken(String code) {
        try {
            Assert.notBlank(code, "Parameter \"code\" must not blank. ");
            Map<String, Object> params = new HashMap<String, Object>(SIX);
            params.put("grant_type", "authorization_code");
            params.put("secret", appSecret);
            params.put("appid", appId);
            params.put("code", code);
            log.info("The parameters of calling WeiXin open platform api \"code2AccessToken\" is \"{}\". ", params);
            String response = HttpUtils.get(CODE_TO_ACCESS_TOKEN_URL, params);
            log.info("The response after calling WeiXin open platform api \"code2AccessToken\" is \"{}\". ", response);
            if (StringUtils.isBlank(response)) {
                throw new WxOpenException(
                        "WeiXin open platform api \"code2AccessToken\" call failed because there was no return value. "
                );
            }
            ParameterizedType type = TypeUtils.parameterizedOf(Map.class, String.class, Object.class);
            Map<String, Object> responseData = JsonUtils.parseObject(response, type);
            Integer errCode = (Integer) responseData.get("errcode");
            String errMsg = (String) responseData.get("errmsg");
            if (errCode != null && !errCode.equals(ZERO)) {
                throw new WxOpenException(
                        "The error message returned by WeiXin server after failed call is \"" + errMsg + "\". "
                );
            }
            WxCode2AccessTokenResult wxCode2AccessTokenResult = new WxCode2AccessTokenResult();
            wxCode2AccessTokenResult.setErrCode(errCode);
            wxCode2AccessTokenResult.setErrMsg(errMsg);
            wxCode2AccessTokenResult.setAccessToken((String) responseData.get("access_token"));
            wxCode2AccessTokenResult.setRefreshToken((String) responseData.get("refresh_token"));
            wxCode2AccessTokenResult.setExpiresIn((Long) responseData.get("expires_in"));
            wxCode2AccessTokenResult.setScope((String) responseData.get("scope"));
            wxCode2AccessTokenResult.setOpenId((String) responseData.get("openid"));
            wxCode2AccessTokenResult.setUnionId((String) responseData.get("unionid"));
            return wxCode2AccessTokenResult;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    @Override
    public WxCode2AccessTokenResult refreshToken(String refreshToken) {
        try {
            Assert.notBlank(refreshToken, "Parameter \"refreshToken\" must not blank. ");
            Map<String, Object> params = new HashMap<String, Object>(SIX);
            params.put("refresh_token", refreshToken);
            params.put("grant_type", "refresh_token");
            params.put("appid", appId);

            log.info("The parameters of calling WeiXin open platform api \"refreshToken\" is \"{}\". ", params);
            String response = HttpUtils.get(REFRESH_TOKEN_URL, params);
            log.info("The response after calling WeiXin open platform api \"refreshToken\" is \"{}\". ", response);
            if (StringUtils.isBlank(response)) {
                throw new WxOpenException(
                        "WeiXin open platform api \"refreshToken\" call failed because there was no return value. "
                );
            }
            ParameterizedType type = TypeUtils.parameterizedOf(Map.class, String.class, Object.class);
            Map<String, Object> responseData = JsonUtils.parseObject(response, type);
            Integer errCode = (Integer) responseData.get("errcode");
            String errMsg = (String) responseData.get("errmsg");
            if (errCode != null && !errCode.equals(ZERO)) {
                throw new WxOpenException(
                        "The error message returned by WeiXin server after failed call is \"" + errMsg + "\". "
                );
            }
            WxCode2AccessTokenResult wxCode2AccessTokenResult = new WxCode2AccessTokenResult();
            wxCode2AccessTokenResult.setErrCode(errCode);
            wxCode2AccessTokenResult.setErrMsg(errMsg);
            wxCode2AccessTokenResult.setAccessToken((String) responseData.get("access_token"));
            wxCode2AccessTokenResult.setRefreshToken((String) responseData.get("refresh_token"));
            wxCode2AccessTokenResult.setExpiresIn((Long) responseData.get("expires_in"));
            wxCode2AccessTokenResult.setScope((String) responseData.get("scope"));
            wxCode2AccessTokenResult.setOpenId((String) responseData.get("openid"));
            wxCode2AccessTokenResult.setUnionId((String) responseData.get("unionid"));
            return wxCode2AccessTokenResult;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    @Override
    public WxOpenUserInfo getUserInfo(String accessToken, String openId, String language) {
        try {
            Assert.notBlank(accessToken, "Parameter \"accessToken\" must not blank. ");
            Assert.notBlank(openId, "Parameter \"openId\" must not blank. ");
            Map<String, Object> params = new HashMap<String, Object>(SIX);
            params.put("access_token", accessToken);
            params.put("openid", openId);
            if (StringUtils.isNotBlank(language)) {
                params.put("lang", language);
            }

            log.info("The parameters of calling WeiXin open platform api \"userinfo\" is \"{}\". ", params);
            String response = HttpUtils.get(USER_INFO_URL, params);
            log.info("The response after calling WeiXin open platform api \"userinfo\" is \"{}\". ", response);
            if (StringUtils.isBlank(response)) {
                throw new WxOpenException(
                        "WeiXin open platform api \"userinfo\" call failed because there was no return value. "
                );
            }
            ParameterizedType type = TypeUtils.parameterizedOf(Map.class, String.class, Object.class);
            Map<String, Object> responseData = JsonUtils.parseObject(response, type);
            Integer errCode = (Integer) responseData.get("errcode");
            String errMsg = (String) responseData.get("errmsg");
            if (errCode != null && !errCode.equals(ZERO)) {
                throw new WxOpenException(
                        "The error message returned by WeiXin server after failed call is \"" + errMsg + "\". "
                );
            }
            WxOpenUserInfo wxOpenUserInfo = BeanUtils.mapToBean(responseData, WxOpenUserInfo.class);
//            wxOpenUserInfo.setErrCode(errCode);
//            wxOpenUserInfo.setErrMsg(errMsg);
//            wxOpenUserInfo.setOpenId((String) responseData.get("openid"));
//            wxOpenUserInfo.setUnionId((String) responseData.get("unionid"));
//            wxOpenUserInfo.setHeadImgUrl((String) responseData.get("headimgurl"));
            return wxOpenUserInfo;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

}

package misaka.tencent.wx.miniapp;

import artoria.codec.Base64Utils;
import artoria.crypto.*;
import artoria.exception.ExceptionUtils;
import artoria.exchange.JsonUtils;
import artoria.net.HttpUtils;
import artoria.util.Assert;
import artoria.util.StringUtils;
import artoria.util.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import static artoria.common.Constants.*;

/**
 * WeiXin mini app service implementation.
 * @author Kahle
 */
public class WxMiniAppServiceImpl implements WxMiniAppService {
    private static final String JS_CODE_TO_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static Logger log = LoggerFactory.getLogger(WxMiniAppServiceImpl.class);
    private String appSecret;
    private String appId;

    public WxMiniAppServiceImpl(String appId, String appSecret) {
        Assert.notBlank(appSecret, "Parameter \"appSecret\" must not blank. ");
        Assert.notBlank(appId, "Parameter \"appId\" must not blank. ");
        this.appSecret = appSecret;
        this.appId = appId;
    }

    @Override
    public WxCode2SessionResult code2Session(String code) {
        try {
            Assert.notBlank(code, "Parameter \"code\" must not blank. ");
            Map<String, Object> params = new HashMap<String, Object>(SIX);
            params.put("grant_type", "authorization_code");
            params.put("secret", appSecret);
            params.put("appid", appId);
            params.put("js_code", code);
            log.info("The parameters of calling WeiXin mini app api \"code2Session\" is \"{}\". ", params);
            String response = HttpUtils.get(JS_CODE_TO_SESSION_URL, params);
            log.info("The response after calling WeiXin mini app api \"code2Session\" is \"{}\". ", response);
            if (StringUtils.isBlank(response)) {
                throw new WxMiniAppException(
                        "WeiXin mini app api \"code2Session\" call failed because there was no return value. "
                );
            }
            ParameterizedType type = TypeUtils.parameterizedOf(Map.class, String.class, Object.class);
            Map<String, Object> responseData = JsonUtils.parseObject(response, type);
            Integer errCode = (Integer) responseData.get("errcode");
            String errMsg = (String) responseData.get("errmsg");
            /*
             * Error code
             *  -1        System busy
             *  0         Request success
             *  40029     invalid code
             *  45011     frequency limitation. Each user is 100 times per minute.
             */
            if (errCode != null && !errCode.equals(ZERO)) {
                log.info("The error message returned by WeiXin server after failed call is \"" + errMsg + "\". ");
            }
            WxCode2SessionResult wxCode2SessionResult = new WxCode2SessionResult();
            wxCode2SessionResult.setErrCode(errCode);
            wxCode2SessionResult.setErrMsg(errMsg);
            wxCode2SessionResult.setOpenId((String) responseData.get("openid"));
            wxCode2SessionResult.setUnionId((String) responseData.get("unionid"));
            wxCode2SessionResult.setSessionKey((String) responseData.get("session_key"));
            return wxCode2SessionResult;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    @Override
    public WxMiniAppUserInfo decryptUserInfo(String encryptedData, String sessionKey, String iv) {
        try {
            Assert.notBlank(encryptedData, "Parameter \"encryptedData\" must not blank. ");
            Assert.notBlank(sessionKey, "Parameter \"sessionKey\" must not blank. ");
            Assert.notBlank(iv, "Parameter \"iv\" must not blank. ");
            byte[] keyBytes = Base64Utils.decodeFromString(sessionKey);
            byte[] ivBytes = Base64Utils.decodeFromString(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
            SecretKey secretKey = KeyUtils.parseSecretKey(AES, keyBytes);
            SymmetricCrypto userInfoCrypto = new SimpleSymmetricCrypto();
            userInfoCrypto.setPadding(Padding.PKCS7_PADDING);
            userInfoCrypto.setAlgorithm(AES);
            userInfoCrypto.setMode(Mode.CBC);
            userInfoCrypto.setSecretKey(secretKey);
            userInfoCrypto.setAlgorithmParameterSpec(ivParameterSpec);
            byte[] encryptedBytes = Base64Utils.decodeFromString(encryptedData);
            log.info(
                    "The parameter for decrypting WeiXin user information is \"sessionKey={}, iv={}, encryptedData={}\". "
                    , sessionKey
                    , iv
                    , encryptedData
            );
            byte[] decryptBytes = userInfoCrypto.decrypt(encryptedBytes);
            String userInfoJson = new String(decryptBytes, UTF_8);
            log.info("The result after decrypting WeiXin user information is \"{}\". ", userInfoJson);
            return JsonUtils.parseObject(userInfoJson, WxMiniAppUserInfo.class);
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

}

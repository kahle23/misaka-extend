package misaka.tencent.wx.miniapp;

/**
 * WeiXin mini app service.
 * @author Kahle
 */
public interface WxMiniAppService {

    /**
     * Use the js code to the WeiXin server in exchange for a session.
     * @param code The value obtained through "wx.login()"
     * @return WeiXin session object
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">auth.code2Session</a>
     */
    WxCode2SessionResult code2Session(String code);

    /**
     * Decrypt sensitive information of WeiXin user.
     * @param encryptedData Encrypted data
     * @param sessionKey Session key
     * @param iv Vector quantity
     * @return WeiXin user information
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/user-info/wx.getUserInfo.html">wx.getUserInfo(Object object)</a>
     */
    WxMiniAppUserInfo decryptUserInfo(String encryptedData, String sessionKey, String iv);

}

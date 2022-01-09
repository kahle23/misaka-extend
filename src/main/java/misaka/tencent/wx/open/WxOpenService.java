package misaka.tencent.wx.open;

/**
 * WeiXin open platform service.
 * @author Kahle
 */
public interface WxOpenService {

    /**
     * Code to access token.
     * @see <a href="https://developers.weixin.qq.com/doc/oplatform/Mobile_App/WeChat_Login/Development_Guide.html">WeChat Login</a>
     */
    WxCode2AccessTokenResult code2AccessToken(String code);

    /**
     * Refresh token.
     */
    WxCode2AccessTokenResult refreshToken(String refreshToken);

    /**
     * Get user info.
     */
    WxOpenUserInfo getUserInfo(String accessToken, String openId, String language);

}

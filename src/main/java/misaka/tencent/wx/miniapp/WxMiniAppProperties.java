package misaka.tencent.wx.miniapp;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * WeiXin mini app properties.
 * @author Kahle
 */
@ConfigurationProperties("wx.miniapp")
public class WxMiniAppProperties {
    /**
     * Enabled.
     */
    private Boolean enabled;
    /**
     * App id.
     */
    private String appId;
    /**
     * App secret.
     */
    private String appSecret;

    public Boolean getEnabled() {

        return enabled;
    }

    public void setEnabled(Boolean enabled) {

        this.enabled = enabled;
    }

    public String getAppId() {

        return appId;
    }

    public void setAppId(String appId) {

        this.appId = appId;
    }

    public String getAppSecret() {

        return appSecret;
    }

    public void setAppSecret(String appSecret) {

        this.appSecret = appSecret;
    }

}

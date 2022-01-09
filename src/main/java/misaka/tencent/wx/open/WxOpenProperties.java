package misaka.tencent.wx.open;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * WeiXin open platform properties.
 * @author Kahle
 */
@ConfigurationProperties("wx.open")
public class WxOpenProperties {
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

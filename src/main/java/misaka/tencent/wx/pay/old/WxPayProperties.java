package misaka.tencent.wx.pay.old;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * WeiXin pay properties.
 * @author Kahle
 */
@Deprecated
@ConfigurationProperties("wx.pay")
public class WxPayProperties {
    /**
     * Enabled.
     */
    private Boolean enabled;
    /**
     * App id.
     */
    private String appId;
    /**
     * Mch id.
     */
    private String mchId;
    /**
     * Mch key.
     */
    private String mchKey;

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

    public String getMchId() {

        return mchId;
    }

    public void setMchId(String mchId) {

        this.mchId = mchId;
    }

    public String getMchKey() {

        return mchKey;
    }

    public void setMchKey(String mchKey) {

        this.mchKey = mchKey;
    }

}

package misaka.tencent.wx.pay;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "tencent.wx.pay")
public class WxPayProperties {
    private Boolean enabled;
    private List<WxPayConfig> configs;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<WxPayConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<WxPayConfig> configs) {
        this.configs = configs;
    }

    public static class WxPayConfig {
        private String customAppId;
        private String customAppTypes;
        private String payWay;
        private String appId;
        private String mchId;
        private String mchKey;
        private String sslCertPath;
        private String proxyHost;
        private int    proxyPort;

        public String getCustomAppId() {
            return customAppId;
        }

        public void setCustomAppId(String customAppId) {
            this.customAppId = customAppId;
        }

        public String getCustomAppTypes() {
            return customAppTypes;
        }

        public void setCustomAppTypes(String customAppTypes) {
            this.customAppTypes = customAppTypes;
        }

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
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

        public String getSslCertPath() {
            return sslCertPath;
        }

        public void setSslCertPath(String sslCertPath) {
            this.sslCertPath = sslCertPath;
        }

        public String getProxyHost() {
            return proxyHost;
        }

        public void setProxyHost(String proxyHost) {
            this.proxyHost = proxyHost;
        }

        public int getProxyPort() {
            return proxyPort;
        }

        public void setProxyPort(int proxyPort) {
            this.proxyPort = proxyPort;
        }

    }

}

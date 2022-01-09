package misaka.app.push;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "misaka.app.push.jpush")
public class JPushProperties {
    private Boolean enabled;
    private List<JPushConfig> configs;

    public Boolean getEnabled() {

        return enabled;
    }

    public void setEnabled(Boolean enabled) {

        this.enabled = enabled;
    }

    public List<JPushConfig> getConfigs() {

        return configs;
    }

    public void setConfigs(List<JPushConfig> configs) {

        this.configs = configs;
    }

    public static class JPushConfig {
        private String customAppId;
        private String masterSecret;
        private String appKey;

        public String getCustomAppId() {

            return customAppId;
        }

        public void setCustomAppId(String customAppId) {

            this.customAppId = customAppId;
        }

        public String getMasterSecret() {

            return masterSecret;
        }

        public void setMasterSecret(String masterSecret) {

            this.masterSecret = masterSecret;
        }

        public String getAppKey() {

            return appKey;
        }

        public void setAppKey(String appKey) {

            this.appKey = appKey;
        }

    }

}

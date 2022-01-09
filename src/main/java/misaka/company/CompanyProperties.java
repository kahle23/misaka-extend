package misaka.company;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "misaka.company")
public class CompanyProperties {
    private ProviderType providerType = ProviderType.NONE;
    private YonyouConfig yonyouConfig;

    public ProviderType getProviderType() {

        return providerType;
    }

    public void setProviderType(ProviderType providerType) {

        this.providerType = providerType;
    }

    public YonyouConfig getYonyouConfig() {

        return yonyouConfig;
    }

    public void setYonyouConfig(YonyouConfig yonyouConfig) {

        this.yonyouConfig = yonyouConfig;
    }

    public enum ProviderType {
        /**
         * None.
         */
        NONE,
        /**
         * Yonyou cloud (https://yonyoucloud.com).
         */
        YONYOU,
        ;
    }

    public static class YonyouConfig {
        private String baseInfoApiCode;
        private String searchApiCode;
        private Integer timeout;

        public String getBaseInfoApiCode() {

            return baseInfoApiCode;
        }

        public void setBaseInfoApiCode(String baseInfoApiCode) {

            this.baseInfoApiCode = baseInfoApiCode;
        }

        public String getSearchApiCode() {

            return searchApiCode;
        }

        public void setSearchApiCode(String searchApiCode) {

            this.searchApiCode = searchApiCode;
        }

        public Integer getTimeout() {

            return timeout;
        }

        public void setTimeout(Integer timeout) {

            this.timeout = timeout;
        }

    }

}

package misaka.company;

import artoria.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "misaka.company.enabled", havingValue = "true")
@EnableConfigurationProperties({CompanyProperties.class})
public class CompanyAutoConfiguration {
    private static Logger log = LoggerFactory.getLogger(CompanyAutoConfiguration.class);

    @Autowired
    public CompanyAutoConfiguration(CompanyProperties properties) {
        CompanyProperties.ProviderType providerType = properties.getProviderType();
        Assert.notNull(providerType, "Parameter \"providerType\" must not null. ");
        CompanyProvider companyProvider = null;
        if (CompanyProperties.ProviderType.YONYOU.equals(providerType)) {
            companyProvider = handleYonyou(properties);
        }
//        else if (CompanyProperties.ProviderType.YONYOU.equals(providerType)) {
//        }
        //else {
        //}
        if (companyProvider != null) {
            CompanyUtils.setCompanyProvider(companyProvider);
        }
    }

    protected CompanyProvider handleYonyou(CompanyProperties properties) {
        CompanyProperties.YonyouConfig yonyouConfig = properties.getYonyouConfig();
        Assert.notNull(yonyouConfig, "Parameter \"yonyouConfig\" must not null. ");
        String baseInfoApiCode = yonyouConfig.getBaseInfoApiCode();
        String searchApiCode = yonyouConfig.getSearchApiCode();
        Integer timeout = yonyouConfig.getTimeout();
        CompanyProvider companyProvider =
                new YonyouCompanyProvider(baseInfoApiCode, searchApiCode, timeout);
        return companyProvider;
    }

}

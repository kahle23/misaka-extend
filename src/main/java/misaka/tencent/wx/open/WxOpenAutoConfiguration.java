package misaka.tencent.wx.open;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WeiXin open platform auto configuration.
 * @author Kahle
 */
@Configuration
@EnableConfigurationProperties({WxOpenProperties.class})
@ConditionalOnProperty(name = "misaka.tencent.wx.open.enabled", havingValue = "true")
public class WxOpenAutoConfiguration {
    private static Logger log = LoggerFactory.getLogger(WxOpenAutoConfiguration.class);
    private final WxOpenProperties wxOpenProperties;

    @Autowired
    public WxOpenAutoConfiguration(WxOpenProperties wxOpenProperties) {

        this.wxOpenProperties = wxOpenProperties;
    }

    @Bean
    public WxOpenService wxOpenService() {
        String appSecret = wxOpenProperties.getAppSecret();
        String appId = wxOpenProperties.getAppId();
        WxOpenService wxOpenService = new WxOpenServiceImpl(appId, appSecret);
        log.info("WeiXin open platform service was initialized success. ");
        return wxOpenService;
    }

}

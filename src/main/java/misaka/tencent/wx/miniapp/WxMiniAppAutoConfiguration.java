package misaka.tencent.wx.miniapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WeiXin mini app auto configuration.
 * @author Kahle
 */
@Configuration
@EnableConfigurationProperties({WxMiniAppProperties.class})
@ConditionalOnProperty(name = "misaka.tencent.wx.miniapp.enabled", havingValue = "true")
public class WxMiniAppAutoConfiguration {
    private static Logger log = LoggerFactory.getLogger(WxMiniAppAutoConfiguration.class);
    private final WxMiniAppProperties wxMiniAppProperties;

    @Autowired
    public WxMiniAppAutoConfiguration(WxMiniAppProperties wxMiniAppProperties) {

        this.wxMiniAppProperties = wxMiniAppProperties;
    }

    @Bean
    public WxMiniAppService wxMiniAppService() {
        String appSecret = wxMiniAppProperties.getAppSecret();
        String appId = wxMiniAppProperties.getAppId();
        WxMiniAppService wxMiniAppService = new WxMiniAppServiceImpl(appId, appSecret);
        log.info("WeiXin mini app service was initialized success. ");
        return wxMiniAppService;
    }

}

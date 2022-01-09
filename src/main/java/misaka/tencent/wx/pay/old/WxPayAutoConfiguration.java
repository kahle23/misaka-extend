package misaka.tencent.wx.pay.old;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WeiXin pay auto configuration.
 * @author Kahle
 */
@Configuration
@EnableConfigurationProperties({WxPayProperties.class})
@ConditionalOnProperty(name = "wx.pay.enabled", havingValue = "true")
public class WxPayAutoConfiguration {
    private static Logger log = LoggerFactory.getLogger(WxPayAutoConfiguration.class);
    private final WxPayProperties wxPayProperties;

    @Autowired
    public WxPayAutoConfiguration(WxPayProperties wxPayProperties) {

        this.wxPayProperties = wxPayProperties;
    }

    @Bean
    public WxPayService wxPayService() {
        String appId = wxPayProperties.getAppId();
        String mchId = wxPayProperties.getMchId();
        String mchKey = wxPayProperties.getMchKey();
        WxPayService wxPayService = new WxPayServiceImpl(appId, mchId, mchKey);
        log.info("WeiXin pay service was initialized success. ");
        return wxPayService;
    }

}

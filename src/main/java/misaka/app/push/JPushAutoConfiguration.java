package misaka.app.push;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.connection.HttpProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnProperty(name = "misaka.app.push.jpush.enabled", havingValue = "true")
@EnableConfigurationProperties({JPushProperties.class})
public class JPushAutoConfiguration {
    private static Logger log = LoggerFactory.getLogger(JPushAutoConfiguration.class);

    @Autowired
    public JPushAutoConfiguration(JPushProperties jPushProperties) {
        List<JPushProperties.JPushConfig> configs = jPushProperties.getConfigs();
        for (JPushProperties.JPushConfig config : configs) {
            String customAppId = config.getCustomAppId();
            String masterSecret = config.getMasterSecret();
            String appKey = config.getAppKey();
            JPushProvider jPushProvider = new JPushProvider(
                    masterSecret, appKey, (HttpProxy) null, (ClientConfig) null);
            PushUtils.register(customAppId, jPushProvider);
        }
    }

}

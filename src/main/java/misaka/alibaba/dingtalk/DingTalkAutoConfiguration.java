package misaka.alibaba.dingtalk;

import artoria.cache.SimpleCache;
import artoria.lang.ReferenceType;
import artoria.net.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "misaka.alibaba.dingtalk.enabled", havingValue = "true")
@EnableConfigurationProperties({DingTalkProperties.class})
public class DingTalkAutoConfiguration {
    private static Logger log = LoggerFactory.getLogger(DingTalkAutoConfiguration.class);
    private DingTalkService dingTalkService;

    @Autowired
    public DingTalkAutoConfiguration(DingTalkProperties dingTalkProperties) {
        String appSecret = dingTalkProperties.getAppSecret();
        String appKey = dingTalkProperties.getAppKey();
        SimpleCache cache = new SimpleCache("ding-talk-cache", 0, 600000, ReferenceType.SOFT);
        cache.setRecordLog(true);
        dingTalkService = new DingTalkService(HttpUtils.getHttpClient(), cache, appKey, appSecret);
    }

    @Bean
    public DingTalkService dingTalkService() {

        return dingTalkService;
    }

}

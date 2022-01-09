package misaka.tencent.wx.pay;

import artoria.util.StringUtils;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import misaka.pay.PayProvider;
import misaka.pay.PayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static artoria.common.Constants.COMMA;
import static artoria.common.Constants.ZERO;

@Configuration
@ConditionalOnProperty(name = "misaka.tencent.wx.pay.enabled", havingValue = "true")
@EnableConfigurationProperties({WxPayProperties.class})
public class WxPayAutoConfiguration {
    private static Logger log = LoggerFactory.getLogger(WxPayAutoConfiguration.class);

    @Autowired
    public WxPayAutoConfiguration(WxPayProperties wxPayProperties) {
        List<WxPayProperties.WxPayConfig> configs = wxPayProperties.getConfigs();
        for (WxPayProperties.WxPayConfig config : configs) {
            String customAppTypes = config.getCustomAppTypes();
            String customAppId = config.getCustomAppId();
            String payWay = config.getPayWay();
            String appId = config.getAppId();
            String mchId = config.getMchId();
            String mchKey = config.getMchKey();
            String sslCertPath = config.getSslCertPath();
//            String proxyHost = config.getProxyHost();
//            int proxyPort = config.getProxyPort();

            WxPayConfig wxPayConfig = new WxPayConfig();
            wxPayConfig.setAppId(appId);
            wxPayConfig.setMchId(mchId);
            wxPayConfig.setMchKey(mchKey);
            if (StringUtils.isNotBlank(sslCertPath)) {
                wxPayConfig.setKeyPath(sslCertPath);
            }
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);

            PayProvider payProvider = new WxPayProvider(wxPayService);
            String[] typeArray = customAppTypes != null
                    ? customAppTypes.split(COMMA) : new String[ZERO];
            PayUtils.register(customAppId, typeArray, payWay, payProvider);
        }
    }

}

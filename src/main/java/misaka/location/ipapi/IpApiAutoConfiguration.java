package misaka.location.ipapi;

import artoria.action.ActionUtils;
import misaka.location.ip.IpQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * Network physical address auto configuration.
 * @author Kahle
 */
@Configuration
public class IpApiAutoConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(IpApiAutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        IpApiIpActionHandler provider = new IpApiIpActionHandler();
        ActionUtils.registerHandler(IpQuery.class, provider);
        ActionUtils.registerHandler(IpQuery.class, "ipapi", provider);
    }

    @Override
    public void destroy() throws Exception {
    }

}

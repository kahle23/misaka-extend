package misaka.bank.supfree;

import artoria.action.ActionUtils;
import misaka.bank.BankCardQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * Supfree net.
 * @author Kahle
 */
@Configuration
public class SupfreeNetAutoConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(SupfreeNetAutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        SupfreeBankCardActionHandler provider = new SupfreeBankCardActionHandler();
        ActionUtils.registerHandler(BankCardQuery.class, provider);
        ActionUtils.registerHandler(BankCardQuery.class, "supfree", provider);
    }

    @Override
    public void destroy() throws Exception {
    }

}

package misaka.bank.supfree;

import artoria.query.QueryUtils;
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
        SupfreeBankCardQueryHandler provider = new SupfreeBankCardQueryHandler();
        QueryUtils.registerHandler(BankCardQuery.class, provider);
        QueryUtils.registerHandler(BankCardQuery.class, "supfree", provider);
    }

    @Override
    public void destroy() throws Exception {
    }

}

package misaka.bank;

import artoria.crypto.EncryptUtils;
import artoria.file.Csv;
import artoria.io.IOUtils;
import artoria.util.ClassLoaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

import static artoria.common.Constants.DEFAULT_ENCODING_NAME;

/**
 * Bank card issuer auto configuration.
 * @author Kahle
 */
@Configuration
@ConditionalOnProperty(name = "misaka.bank.bank-card-issuer.enabled", havingValue = "true")
public class BankCardIssuerAutoConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(BankCardIssuerAutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        Class<?> callingClass = BankCardIssuerAutoConfiguration.class;
        String resourceName = "bank_card_issuer.data";
        InputStream inputStream =
                ClassLoaderUtils.getResourceAsStream(resourceName, callingClass);
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        byte[] decrypt = EncryptUtils.decrypt(byteArray);
        Csv csv = new Csv();
        csv.setCharset(DEFAULT_ENCODING_NAME);
        csv.readFromByteArray(decrypt);
        BankCardIssuerProvider provider = new FileBasedBankCardIssuerProvider(csv);
        BankCardUtils.setBankCardIssuerProvider(provider);
    }

    @Override
    public void destroy() throws Exception {
    }

}

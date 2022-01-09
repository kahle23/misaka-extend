package misaka.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Area information auto configuration.
 * @author Kahle
 */
@Configuration
@ConditionalOnProperty(name = "misaka.location.enabled", havingValue = "true")
public class GeolocationAutoConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(GeolocationAutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
//        Class<?> callingClass = GeolocationAutoConfiguration.class;
//        String resourceName = "area_info.data";
//        InputStream inputStream =
//                ClassLoaderUtils.getResourceAsStream(resourceName, callingClass);
//        byte[] byteArray = IOUtils.toByteArray(inputStream);
//        byte[] decrypt = EncryptUtils.decrypt(byteArray);
//        Csv csv = new Csv();
//        csv.setCharset(DEFAULT_ENCODING_NAME);
//        csv.readFromByteArray(decrypt);
        LocationProvider provider = new FileBasedGeolocationProvider();
        LocationUtils.setLocationProvider(provider);
    }

    @Override
    public void destroy() throws Exception {
    }

}

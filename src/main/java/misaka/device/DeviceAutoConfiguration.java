package misaka.device;

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

@Configuration
@ConditionalOnProperty(name = "misaka.device.enabled", havingValue = "true")
public class DeviceAutoConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(DeviceAutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        Class<?> callingClass = DeviceAutoConfiguration.class;
        String resourceName = "device_info.data";
        InputStream inputStream =
                ClassLoaderUtils.getResourceAsStream(resourceName, callingClass);
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        byte[] decrypt = EncryptUtils.decrypt(byteArray);
        Csv csv = new Csv();
        csv.setCharset(DEFAULT_ENCODING_NAME);
        csv.readFromByteArray(decrypt);
        DeviceUtils.setDeviceProvider(new FileBasedDeviceProvider(csv));
    }

    @Override
    public void destroy() throws Exception {
    }

}

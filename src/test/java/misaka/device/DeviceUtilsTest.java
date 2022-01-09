package misaka.device;

import artoria.exception.ExceptionUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Boolean.TRUE;

@Ignore
public class DeviceUtilsTest {
    private static Logger log = LoggerFactory.getLogger(DeviceUtilsTest.class);

    @Test
    public void test1() {
        try {
            new DeviceAutoConfiguration().afterPropertiesSet();
            Device device = DeviceUtils.info("SM901", null);
            log.info(JSON.toJSONString(device, TRUE));
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

}

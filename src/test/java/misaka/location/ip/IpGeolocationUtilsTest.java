package misaka.location.ip;

import artoria.exchange.FastJsonProvider;
import artoria.exchange.JsonUtils;
import com.alibaba.fastjson.JSON;
import misaka.location.LocationUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class IpGeolocationUtilsTest {
    private static Logger log = LoggerFactory.getLogger(IpGeolocationUtilsTest.class);

    @Test
    public void test1() {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        LocationUtils.setIpGeolocationProvider(new IpApiIpGeolocationProvider());
        IpGeolocation ipGeolocation = LocationUtils.ipGeolocation("223.98.40.191");
        log.info("{}", JSON.toJSONString(ipGeolocation));
        ipGeolocation = LocationUtils.ipGeolocation("106.57.23.1");
        log.info("{}", JSON.toJSONString(ipGeolocation));
        ipGeolocation = LocationUtils.ipGeolocation("120.231.22.221");
        log.info("{}", JSON.toJSONString(ipGeolocation));
        ipGeolocation = LocationUtils.ipGeolocation("220.243.135.165");
        log.info("{}", JSON.toJSONString(ipGeolocation));
        ipGeolocation = LocationUtils.ipGeolocation("117.136.7.206");
        log.info("{}", JSON.toJSONString(ipGeolocation));
        ipGeolocation = LocationUtils.ipGeolocation("122.238.172.155");
        log.info("{}", JSON.toJSONString(ipGeolocation));
        log.info("{}", ipGeolocation);
    }

}

package misaka.location.ip;

import artoria.exchange.JsonUtils;
import artoria.net.HttpUtils;
import artoria.util.MapUtils;
import artoria.util.StringUtils;
import artoria.util.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Network physical address provider by website(http://ip-api.com).
 * @see <a href="http://ip-api.com/">IP Geolocation API</a>
 * @author Kahle
 */
public class IpApiIpGeolocationProvider implements IpGeolocationProvider {
    private static Logger log = LoggerFactory.getLogger(IpApiIpGeolocationProvider.class);

    @Override
    public IpGeolocation info(String ipAddress) {
        String jsonString = HttpUtils.get("http://ip-api.com/json/" + ipAddress + "?lang=zh-CN");
        if (StringUtils.isBlank(jsonString)) { return null; }
        ParameterizedType type = TypeUtils.parameterizedOf(Map.class, String.class, String.class);
        Map<String, String> map = JsonUtils.parseObject(jsonString, type);
        if (MapUtils.isEmpty(map)) { return null; }
        IpGeolocation ipGeolocation = new IpGeolocation();
        ipGeolocation.setIpAddress(ipAddress);
        ipGeolocation.setCountry(map.get("country"));
        ipGeolocation.setCountryCode(map.get("countryCode"));
        ipGeolocation.setRegion(map.get("regionName"));
        ipGeolocation.setRegionCode(map.get("region"));
        ipGeolocation.setCity(map.get("city"));
        ipGeolocation.setCityCode(null);
        ipGeolocation.setIsp(map.get("isp"));
        ipGeolocation.setOrg(map.get("org"));
        ipGeolocation.setTimezone(map.get("timezone"));
        ipGeolocation.setZip(map.get("zip"));
        ipGeolocation.setAs(map.get("as"));
        String lat = map.get("lat");
        String lon = map.get("lon");
        try {
            BigDecimal latitude = lat != null ? new BigDecimal(lat) : null;
            BigDecimal longitude = lon != null ? new BigDecimal(lon) : null;
            ipGeolocation.setLatitude(latitude);
            ipGeolocation.setLongitude(longitude);
        }
        catch (Exception e) {
            log.info("Parse latitude and longitude to double error", e);
        }
        return ipGeolocation;
    }

}

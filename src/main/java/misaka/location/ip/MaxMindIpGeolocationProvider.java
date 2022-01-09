package misaka.location.ip;

import artoria.exception.ExceptionUtils;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.net.InetAddress;

/**
 * Network physical address provider by MaxMind GeoIP.
 * @see <a href="https://dev.maxmind.com/geoip/">MaxMind GeoIP</a>
 * @author Kahle
 */
public class MaxMindIpGeolocationProvider implements IpGeolocationProvider {
    private static Logger log = LoggerFactory.getLogger(MaxMindIpGeolocationProvider.class);
    private final File providerDbPath;

    public MaxMindIpGeolocationProvider(String providerDbPathStr) {

        this(new File(providerDbPathStr));
    }

    public MaxMindIpGeolocationProvider(File providerDbPath) {
        // http://dev.maxmind.com/geoip/geoip2/geolite2/
        this.providerDbPath = providerDbPath;
        log.info("MaxMind Ip Geolocation Provider database path: {}", providerDbPath);
    }

    @Override
    public IpGeolocation info(String ipAddress) {
        try {
//            String dbPath = "e:\\GeoLite2-City.mmdb";
            // 语言：de、pt-BR、fr、en、ru、zh-CN、es、ja
            String lang = "en";
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            DatabaseReader dbReader = new DatabaseReader.Builder(providerDbPath).build();
            CityResponse response = dbReader.city(inetAddress);

            Country country = response.getCountry();
            Subdivision subdivision = response.getMostSpecificSubdivision();
            City city = response.getCity();
            Postal postal = response.getPostal();
            Location location = response.getLocation();

            IpGeolocation ipGeolocation = new IpGeolocation();
            ipGeolocation.setCountry(country.getNames().get(lang));
            ipGeolocation.setCountryCode(country.getIsoCode());
            ipGeolocation.setRegion(subdivision.getNames().get(lang));
            ipGeolocation.setRegionCode(subdivision.getIsoCode());
            ipGeolocation.setCity(city.getNames().get(lang));
//            ipGeolocation.setCityCode();
//            ipGeolocation.setDistrict();
//            ipGeolocation.setDistrictCode();
//            ipGeolocation.setStreet();
//            ipGeolocation.setStreetCode();
            ipGeolocation.setLatitude(BigDecimal.valueOf(location.getLatitude()));
            ipGeolocation.setLongitude(BigDecimal.valueOf(location.getLongitude()));
//            ipGeolocation.setElevation();
//            ipGeolocation.set;
            return ipGeolocation;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

}

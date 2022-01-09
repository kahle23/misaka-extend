package misaka.location;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class FileBasedGeolocationProviderTest {
    private static Logger log = LoggerFactory.getLogger(FileBasedGeolocationProviderTest.class);
    private static FileBasedGeolocationProvider fileBasedAreaInfoProvider = new FileBasedGeolocationProvider();

    @Test
    public void test1() throws Exception {
        log.info("{}", fileBasedAreaInfoProvider.info("230603"));
        log.info("{}", fileBasedAreaInfoProvider.search("230000"));
        log.info("{}", fileBasedAreaInfoProvider.search("230600"));
        log.info("{}", fileBasedAreaInfoProvider.search("230603"));
        log.info("{}", fileBasedAreaInfoProvider.subLocations(null, null));
        log.info("{}", fileBasedAreaInfoProvider.subLocations("CN", "country"));
        log.info("{}", fileBasedAreaInfoProvider.subLocations("230000", "region"));
        log.info("{}", fileBasedAreaInfoProvider.subLocations("230600", "city"));
        log.info("{}", fileBasedAreaInfoProvider.subLocations("230603", "district"));
    }

}

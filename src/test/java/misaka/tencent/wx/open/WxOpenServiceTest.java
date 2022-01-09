package misaka.tencent.wx.open;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class WxOpenServiceTest {
    private static Logger log = LoggerFactory.getLogger(WxOpenServiceTest.class);
    private static String appSecret = "appSecret";
    private static String appId = "appId";
    private static WxOpenService wxOpenService = new WxOpenServiceImpl(appId, appSecret);

    @Test
    public void test1() {
    }

}

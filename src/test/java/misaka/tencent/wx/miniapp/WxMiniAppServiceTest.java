package misaka.tencent.wx.miniapp;

import artoria.data.json.support.FastJsonProvider;
import artoria.exchange.JsonUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Provider;
import java.security.Security;

@Ignore
public class WxMiniAppServiceTest {
    private static Logger log = LoggerFactory.getLogger(WxMiniAppServiceTest.class);
    private static String appSecret = "appSecret";
    private static String appId = "appId";
    private static WxMiniAppService wxMiniAppService = new WxMiniAppServiceImpl(appId, appSecret);

    static {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        Provider provider = null;
        try {
            provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
        }
        catch (NoClassDefFoundError e) {
            // Ignore.
        }
        if (provider != null) {
            Security.addProvider(provider);
        }
    }

    @Test
    public void decryptUserInfoTest() {
        String encryptedData = "encryptedData";
        String sessionKey = "sessionKey";
        String iv = "iv";
        WxMiniAppUserInfo userInfo = wxMiniAppService.decryptUserInfo(encryptedData, sessionKey, iv);
        log.info("{}", JSON.toJSONString(userInfo));
    }

}

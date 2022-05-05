package misaka.alibaba.dingtalk;

import artoria.cache.SimpleCache;
import artoria.exchange.FastJsonProvider;
import artoria.exchange.JsonUtils;
import artoria.lang.Dict;
import artoria.lang.ReferenceType;
import artoria.net.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class DingTalkServiceTest {
    private static Logger log = LoggerFactory.getLogger(DingTalkServiceTest.class);
    private DingTalkService dingTalkService = new DingTalkService(HttpUtils.getHttpClient(),
            new SimpleCache("ding-talk-cache", 0, 600000, ReferenceType.SOFT),
            "", "");

    @Test
    public void test1() {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        Dict accessToken = dingTalkService.getAccessToken();
        log.info("{}", JSON.toJSONString(accessToken));
    }

    @Test
    public void test2() {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        Dict userInfo = dingTalkService.getUserInfo("803ddd731c803fbcb66037ffec36d594");
        log.info("{}", JSON.toJSONString(userInfo));
    }

    @Test
    public void test3() {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        Dict spaces = dingTalkService.getSpaces(Dict.of("unionId", "m7gLx4qO4uPyGAiEiE")
                .set("spaceType", "org").set("nextToken", null).set("maxResults", 50));
        // spaceId = 2016270227
        log.info("{}", JSON.toJSONString(spaces, true));
    }

    @Test
    public void test4() {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        Dict spaces = dingTalkService.getFiles(Dict.of("spaceId", "4906264950")
                .set("unionId", "4WRZpNfA4G95wwiEiE").set("parentId", "39438616813").set("nextToken", "").set("maxResults", 50));
        log.info("{}", JSON.toJSONString(spaces, true));
    }

    @Test
    public void test5() {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        Dict downloadInfo = dingTalkService.downloadInfo(Dict.of("spaceId", "4906264950")
                .set("fileId", "39438724473").set("unionId", "4WRZpNfA4GX5wwiEiE"));
        log.info("{}", JSON.toJSONString(downloadInfo, true));
    }

}

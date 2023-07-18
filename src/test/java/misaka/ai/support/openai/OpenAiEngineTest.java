package misaka.ai.support.openai;

import artoria.ai.AiUtils;
import artoria.data.Dict;
import artoria.data.json.JsonUtils;
import artoria.data.json.support.FastJsonProvider;
import com.alibaba.fastjson.JSON;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

@Ignore
public class OpenAiEngineTest {
    private static final Logger log = LoggerFactory.getLogger(OpenAiEngineTest.class);
    private static final String engineName = "openai";

    static {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        OpenAiEngine aiEngine = new OpenAiEngine("apiKey");
        aiEngine.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 58591)));
        AiUtils.registerHandler(engineName, aiEngine);
    }

    @Test
    public void test1() {
        String strategy = "models";
        Dict execute = AiUtils.execute(null, engineName, strategy, Dict.class);
        log.info(JSON.toJSONString(execute, true));
    }

    @Test
    public void test2() {
        String strategy = "completion";
        String execute = AiUtils.execute("what is ai?", engineName, strategy, String.class);
        log.info(execute);
//        Dict execute = AiUtils.execute("what is ai?", engineName, strategy, Dict.class);
//        log.info(JSON.toJSONString(execute, Boolean.TRUE));
    }

}

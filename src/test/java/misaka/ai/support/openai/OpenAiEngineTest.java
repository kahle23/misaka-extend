package misaka.ai.support.openai;

import artoria.ai.AiUtils;
import artoria.data.json.JsonUtils;
import artoria.data.json.support.FastJsonProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;

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
        String execute = AiUtils.execute("what is ai?", engineName, String.class);
        log.info(execute);
//        Dict execute = AiUtils.execute("what is ai?", engineName, Dict.class);
//        log.info(JSON.toJSONString(execute, Boolean.TRUE));
    }

}

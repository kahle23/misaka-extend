package misaka.ai.support.openai;

import artoria.ai.AiUtils;
import misaka.ai.support.yucongmin.YuCongMingAiHandlerTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class OpenAiEngineTest {
    private static final Logger log = LoggerFactory.getLogger(YuCongMingAiHandlerTest.class);
    private static final String engineName = "openai";

    static {
        SimpleOpenAiEngine aiEngine = new SimpleOpenAiEngine("apiKey");
        aiEngine.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 58591)));
        AiUtils.registerHandler(engineName, aiEngine);
    }

    @Test
    public void test1() {
        String execute = AiUtils.execute("what is AI?", engineName, String.class);
        log.info(execute);
    }

}

package misaka.ai.support.ycm;

import artoria.ai.AiUtils;
import artoria.ai.support.SimpleAiMessage;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class YcmAiEngineTest {
    private static final Logger log = LoggerFactory.getLogger(YcmAiEngineTest.class);
    private static final String engineName = "ycm";

    static {
        YcmAiEngine aiEngine = new YcmAiEngine(
                "accessKey", "secretKey");
        AiUtils.registerHandler(engineName, aiEngine);
    }

    @Test
    public void test1() {
        String execute = AiUtils.execute("what is AI?", engineName, String.class);
        log.info(execute);
    }

    @Test
    public void test2() {
        SimpleAiMessage message = new SimpleAiMessage("hello, world!");
        message.setModel("1651468516836098050");
        String execute = AiUtils.execute(message, engineName, String.class);
        log.info(execute);
    }

}

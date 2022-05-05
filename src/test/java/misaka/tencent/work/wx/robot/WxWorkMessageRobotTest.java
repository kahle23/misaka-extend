package misaka.tencent.work.wx.robot;

import artoria.bot.MessageBot;
import artoria.exchange.FastJsonProvider;
import artoria.exchange.JsonUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class WxWorkMessageRobotTest {
    private static Logger log = LoggerFactory.getLogger(WxWorkMessageRobotTest.class);
    private static MessageBot wxWorkMessageBot = new WxWorkMessageRobot("key123456");

    @Test
    public void test1() {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        for (int i = 0; i < 10; i++) {
            Object send = wxWorkMessageBot.send("Hello, World! ");
            log.info("Send result: {}", send);
        }
    }

}

package misaka.tencent.work.wx.robot;

import artoria.bot.MessageBot;
import artoria.exception.ExceptionUtils;
import artoria.exchange.JsonUtils;
import artoria.lang.Dict;
import artoria.net.HttpMethod;
import artoria.net.HttpRequest;
import artoria.net.HttpResponse;
import artoria.net.HttpUtils;
import artoria.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static artoria.common.Constants.UTF_8;

/**
 * Work WeChat message robot.
 * @author Kahle
 */
public class WxWorkMessageRobot implements MessageBot {
    private static Logger log = LoggerFactory.getLogger(WxWorkMessageRobot.class);
    private final String url;

    public WxWorkMessageRobot(String key) {
        Assert.notBlank(key, "Parameter \"key\" must not blank. ");
        this.url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key="+key;
    }

    @Override
    public Object send(Object message) {
        try {
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.setUrl(url);
            httpRequest.setMethod(HttpMethod.POST);
            httpRequest.setCharset(UTF_8);
            httpRequest.addHeader("Content-Type", "application/json");

            Dict dict = Dict.of("msgtype", "text").set("text", Dict.of("content", message));

            httpRequest.setBody(JsonUtils.toJsonString(dict));
            log.info("WxWorkMessageRobot send \"{}\". ", JsonUtils.toJsonString(httpRequest));
            HttpResponse httpResponse = HttpUtils.getHttpClient().execute(httpRequest);
            String bodyAsString = httpResponse.getBodyAsString();
            log.info("WxWorkMessageRobot receive \"{}\". ", bodyAsString);
            return bodyAsString;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

}

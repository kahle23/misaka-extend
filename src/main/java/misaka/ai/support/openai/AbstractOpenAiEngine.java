package misaka.ai.support.openai;

import artoria.ai.support.AbstractClassicAiEngine;
import artoria.data.Dict;
import artoria.data.bean.BeanUtils;
import artoria.util.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Proxy;
import java.util.Map;

/**
 * The abstract ai engine based on the openai api.
 * @see <a href="https://platform.openai.com/docs/api-reference">API REFERENCE</>
 * @author Kahle
 */
public abstract class AbstractOpenAiEngine extends AbstractClassicAiEngine {
    private static final Logger log = LoggerFactory.getLogger(AbstractOpenAiEngine.class);
    protected static final String PROMPT_KEY = "prompt";
    protected static final String ERROR_KEY  = "error";
    protected static final String MODEL_KEY  = "model";
    private final String apiKey;
    private Proxy proxy;

    public AbstractOpenAiEngine(String apiKey) {
        Assert.notBlank(apiKey, "Parameter \"apiKey\" must not blank. ");
        this.apiKey = apiKey;
    }

    public Proxy getProxy() {

        return proxy;
    }

    public void setProxy(Proxy proxy) {

        this.proxy = proxy;
    }

    protected String getApiKey() {

        return apiKey;
    }

    protected Dict convertToDict(Object input) {
        if (input instanceof Dict) {
            return (Dict) input;
        }
        else if (input instanceof Map) {
            return Dict.of((Map<?, ?>) input);
        }
        else {
            return Dict.of(BeanUtils.beanToMap(input));
        }
    }

    protected HttpResponse post(String url, String body) {
        HttpRequest request = HttpRequest.post(url)
                .header("Authorization", "Bearer " + getApiKey())
                .body(body);
        if (getProxy() != null) {
            request.setProxy(getProxy());
        }
        return request.execute();
    }

    protected void checkResult(Dict error) {
        if (MapUtil.isEmpty(error)) { return; }
        String message = error.getString("message");
        String code = error.getString("code");
        Assert.state(StrUtil.isBlank(code), message);
    }

    @Override
    public Object execute(Object input, String strategy, Class<?> clazz) {
        if ("chat".equals(strategy)) {
            return chat(input, strategy, clazz);
        }
        else {
            return completion(input, strategy, clazz);
        }
    }

    protected Object completion(Object input, String strategy, Class<?> clazz) {

        throw new UnsupportedOperationException();
    }

    protected Object chat(Object input, String strategy, Class<?> clazz) {

        throw new UnsupportedOperationException();
    }

}

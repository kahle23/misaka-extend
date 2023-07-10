package misaka.ai.support.openai;

import artoria.data.Dict;
import artoria.util.Assert;
import artoria.util.StringUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleOpenAiEngine extends AbstractOpenAiEngine {
    private static final Logger log = LoggerFactory.getLogger(SimpleOpenAiEngine.class);

    public SimpleOpenAiEngine(String apiKey) {

        super(apiKey);
    }

    @Override
    protected Object completion(Object input, String strategy, Class<?> clazz) {
        Assert.notNull(input, "Parameter \"input\" must not null. ");
        Dict data;
        if (input instanceof String) {
            data = Dict.of(PROMPT_KEY, input);
        }
        else { data = convertToDict(input); }

        String prompt = data.getString(PROMPT_KEY);
        String model = data.getString(MODEL_KEY);
        if (StringUtils.isBlank(model)) {
            data.set(MODEL_KEY, "text-davinci-003");
        }
        Assert.notBlank(prompt, "Parameter \"prompt\" must not blank. ");

        HttpResponse response = post("https://api.openai.com/v1/completions"
                , JSONUtil.toJsonStr(data));
//        response.bodyStream()
        String body = response.body();
        log.info(body);

        JSONObject result = JSONUtil.parseObj(body);
        checkResult(result.get(ERROR_KEY, Dict.class));

        if (CharSequence.class.isAssignableFrom(clazz)) {
            JSONArray choices = result.getJSONArray("choices");
            if (CollUtil.isNotEmpty(choices)) {
                JSONObject first = (JSONObject) CollUtil.getFirst(choices);
                return first != null ? first.getStr("text") : null;
            }
            return null;
        }
        else {
            return body;
        }
    }

}

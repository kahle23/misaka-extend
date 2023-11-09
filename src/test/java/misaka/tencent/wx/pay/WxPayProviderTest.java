package misaka.tencent.wx.pay;

import artoria.data.AppType;
import artoria.data.json.JsonUtils;
import artoria.data.json.support.FastJsonHandler;
import artoria.generator.id.IdUtils;
import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import misaka.pay.*;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Boolean.TRUE;

@Ignore
public class WxPayProviderTest {
    private static Logger log = LoggerFactory.getLogger(WxPayProviderTest.class);
    private static WxPayProvider wxPayProvider;

    static {
        String mchKey = "mchKey";
        String mchId = "mchId";
        String appId = "appId";
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(appId);
        wxPayConfig.setMchId(mchId);
        wxPayConfig.setMchKey(mchKey);
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig);
        wxPayProvider = new WxPayProvider(wxPayService);
        JsonUtils.registerHandler("default", new FastJsonHandler());
    }

    @Test
    public void test1() {
        String outTradeId = IdUtils.nextString("uuid");
        log.info("outTradeId: {}", outTradeId);
        OrderPayModel orderPayModel = new OrderPayModel();
        orderPayModel.setAppType(AppType.APP_ANDROID.name());
        orderPayModel.setOutTradeId(outTradeId);
        orderPayModel.setTitle("APP支付测试-支付");
        orderPayModel.setDescription("这是一个支付测试");
        orderPayModel.setTotalAmount("0.01");
        orderPayModel.setNotifyUrl("http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");
        orderPayModel.put("openId", "openId");
        OrderPayResult orderPayResult = wxPayProvider.payOrder(orderPayModel);
        log.info(JSON.toJSONString(orderPayResult, TRUE));
    }

    @Test
    public void test2() {
        OrderQueryModel orderQueryModel = new OrderQueryModel();
//        orderQueryModel.setTradeId("TradeId");
        orderQueryModel.setOutTradeId("OutTradeId");
        OrderQueryResult orderQueryResult = wxPayProvider.queryOrder(orderQueryModel);
        log.info(JSON.toJSONString(orderQueryResult, TRUE));
    }

    @Test
    public void test3() {
        OrderCloseModel orderCloseModel = new OrderCloseModel();
//        orderCloseModel.setTradeId("TradeId");
        orderCloseModel.setOutTradeId("OutTradeId");
        OrderCloseResult orderCloseResult = wxPayProvider.closeOrder(orderCloseModel);
        log.info(JSON.toJSONString(orderCloseResult, TRUE));
    }

    @Test
    public void test4() {
        PayNotifyModel payNotifyModel = new PayNotifyModel();
        payNotifyModel.setNotify("<xml><appid></transaction_id>\n" +
                "</xml>");
        PayNotifyResult payNotifyResult = wxPayProvider.payNotify(payNotifyModel);
        log.info(JSON.toJSONString(payNotifyResult, TRUE));
    }

}

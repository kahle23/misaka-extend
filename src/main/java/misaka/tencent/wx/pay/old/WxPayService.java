package misaka.tencent.wx.pay.old;

import java.util.Map;

/**
 * WeiXin pay service.
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/index.html">微信支付文档</a>
 * @author Kahle
 */
@Deprecated
public interface WxPayService {

    /**
     * 微信支付创建签名.
     * @param params 待签名的参数
     * @param signAlgorithm 签名算法
     * @param ignoredParams 需要忽略的参数的 KEY
     * @return 签名的结果
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=4_3">签名算法</a>
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=20_1">签名校验工具</a>
     */
    String createSignature(Map<?, ?> params, String signAlgorithm, String... ignoredParams);

    /**
     * 微信支付根据预支付ID创建付款信息.
     * @param wxUnifiedOrderResult 微信支付统一下单结果对象
     * @return 付款信息结果对象
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=5">小程序调起支付API</a>
     */
    WxPaymentInfoResult createPaymentInfo(WxUnifiedOrderResult wxUnifiedOrderResult);

    /**
     * 微信支付统一下单.
     * @param wxUnifiedOrderRequest 统一下单请求对象
     * @return 统一下单结果对象
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1">统一下单</a>
     */
    WxUnifiedOrderResult unifiedOrder(WxUnifiedOrderRequest wxUnifiedOrderRequest);

    /**
     * 微信支付获取付款信息.
     * @param wxUnifiedOrderRequest 统一下单请求对象
     * @return 付款信息结果对象
     */
    WxPaymentInfoResult paymentInfo(WxUnifiedOrderRequest wxUnifiedOrderRequest);

    /**
     * 解析微信订单支付通知的内容.
     * @param notify 通知的内容
     * @param signType 签名类型（默认走回调返回的，其次走输入的）
     * @return 订单支付的通知的结果对象
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7&index=8">支付结果通知</a>
     */
    WxOrderPayNotifyResult parseOrderPayNotify(String notify, String signType);

}

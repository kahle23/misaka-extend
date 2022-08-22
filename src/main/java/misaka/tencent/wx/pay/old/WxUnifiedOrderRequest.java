package misaka.tencent.wx.pay.old;

import artoria.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static artoria.common.Constants.TWENTY;

/**
 * 微信支付统一下单请求对象.
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1">统一下单</a>
 * @author Kahle
 */
@Deprecated
public class WxUnifiedOrderRequest implements Serializable {
    /**
     * 小程序ID (Required:YES).
     * 微信分配的小程序ID.
     */
    private String appId;
    /**
     * 商户号 (Required:YES).
     * 微信支付分配的商户号.
     */
    private String mchId;
    /**
     * 设备号 (Required:NO).
     * 自定义参数.
     */
    private String deviceInfo;
    /**
     * 随机字符串 (Required:YES).
     * 随机字符串，长度要求在32位以内.
     */
    private String nonceStr;
    /**
     * 签名 (Required:YES).
     * 通过签名算法计算得出的签名值.
     */
    private String sign;
    /**
     * 签名类型 (Required:NO).
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5.
     */
    private String signType;
    /**
     * 商品描述 (Required:YES).
     * 商品简单描述，该字段请按照规范传递.
     */
    private String body;
    /**
     * 商品详情 (Required:NO).
     * 商品详细描述.
     */
    private String detail;
    /**
     * 附加数据 (Required:NO).
     * 在查询API和支付通知中原样返回，可作为自定义参数使用.
     */
    private String attach;
    /**
     * 商户订单号 (Required:YES).
     * 商户系统内部订单号，要求32个字符内.
     */
    private String outTradeNo;
    /**
     * 标价币种 (Required:NO).
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY.
     */
    private String feeType;
    /**
     * 标价金额 (Required:YES).
     * 订单总金额，单位为分.
     */
    private Integer totalFee;
    /**
     * 终端IP (Required:YES).
     * 调用微信支付API的机器IP（支持IPV4和IPV6）.
     */
    private String spbillCreateIp;
    /**
     * 交易起始时间 (Required:NO).
     * 订单生成时间，格式为“yyyyMMddHHmmss”.
     */
    private String timeStart;
    /**
     * 交易结束时间 (Required:NO).
     * 订单失效时间，格式为“yyyyMMddHHmmss”.
     */
    private String timeExpire;
    /**
     * 订单优惠标记 (Required:NO).
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数.
     */
    private String goodsTag;
    /**
     * 通知地址 (Required:YES).
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数.
     */
    private String notifyUrl;
    /**
     * 交易类型 (Required:YES).
     * JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付、MWEB--H5支付.
     * 不同trade_type决定了调起支付的方式，请根据支付产品正确上传.
     */
    private String tradeType;
    /**
     * 商品ID (Required:NO).
     * 此参数为二维码中包含的商品ID，商户自行定义（trade_type=NATIVE时，此参数必传）.
     */
    private String productId;
    /**
     * 指定支付方式 (Required:NO).
     * 上传此参数“no_credit”--可限制用户不能使用信用卡支付.
     */
    private String limitPay;
    /**
     * 用户标识 (Required:NO).
     * 用户在商户 appId 下的唯一标识（trade_type=JSAPI，此参数必传）.
     */
    private String openId;
    /**
     * 电子发票入口开放标识 (Required:NO).
     * Y，传入Y时，支付成功消息和支付详情页将出现开票入口.
     * 需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效.
     */
    private String receipt;
    /**
     * 场景信息 (Required:NO).
     * 该字段常用于线下活动时的场景信息上报（该字段为JSON对象数据）.
     */
    private String sceneInfo;

    public String getAppId() {

        return appId;
    }

    public void setAppId(String appId) {

        this.appId = appId;
    }

    public String getMchId() {

        return mchId;
    }

    public void setMchId(String mchId) {

        this.mchId = mchId;
    }

    public String getDeviceInfo() {

        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {

        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {

        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {

        this.nonceStr = nonceStr;
    }

    public String getSign() {

        return sign;
    }

    public void setSign(String sign) {

        this.sign = sign;
    }

    public String getSignType() {

        return signType;
    }

    public void setSignType(String signType) {

        this.signType = signType;
    }

    public String getBody() {

        return body;
    }

    public void setBody(String body) {

        this.body = body;
    }

    public String getDetail() {

        return detail;
    }

    public void setDetail(String detail) {

        this.detail = detail;
    }

    public String getAttach() {

        return attach;
    }

    public void setAttach(String attach) {

        this.attach = attach;
    }

    public String getOutTradeNo() {

        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {

        this.outTradeNo = outTradeNo;
    }

    public String getFeeType() {

        return feeType;
    }

    public void setFeeType(String feeType) {

        this.feeType = feeType;
    }

    public Integer getTotalFee() {

        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {

        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {

        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {

        this.spbillCreateIp = spbillCreateIp;
    }

    public String getTimeStart() {

        return timeStart;
    }

    public void setTimeStart(String timeStart) {

        this.timeStart = timeStart;
    }

    public String getTimeExpire() {

        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {

        this.timeExpire = timeExpire;
    }

    public String getGoodsTag() {

        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {

        this.goodsTag = goodsTag;
    }

    public String getNotifyUrl() {

        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {

        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {

        return tradeType;
    }

    public void setTradeType(String tradeType) {

        this.tradeType = tradeType;
    }

    public String getProductId() {

        return productId;
    }

    public void setProductId(String productId) {

        this.productId = productId;
    }

    public String getLimitPay() {

        return limitPay;
    }

    public void setLimitPay(String limitPay) {

        this.limitPay = limitPay;
    }

    public String getOpenId() {

        return openId;
    }

    public void setOpenId(String openId) {

        this.openId = openId;
    }

    public String getReceipt() {

        return receipt;
    }

    public void setReceipt(String receipt) {

        this.receipt = receipt;
    }

    public String getSceneInfo() {

        return sceneInfo;
    }

    public void setSceneInfo(String sceneInfo) {

        this.sceneInfo = sceneInfo;
    }

    private static final String APP_ID_KEY = "appid";
    private static final String MCH_ID_KEY = "mch_id";
    private static final String DEVICE_INFO_KEY = "device_info";
    private static final String NONCE_STR_KEY = "nonce_str";
    private static final String SIGN_KEY = "sign";
    private static final String SIGN_TYPE_KEY = "sign_type";
    private static final String BODY_KEY = "body";
    private static final String DETAIL_KEY = "detail";
    private static final String ATTACH_KEY = "attach";
    private static final String OUT_TRADE_NO_KEY = "out_trade_no";
    private static final String FEE_TYPE_KEY = "fee_type";
    private static final String TOTAL_FEE_KEY = "total_fee";
    private static final String SPBILL_CREATE_IP_KEY = "spbill_create_ip";
    private static final String TIME_START_KEY = "time_start";
    private static final String TIME_EXPIRE_KEY = "time_expire";
    private static final String GOODS_TAG_KEY = "goods_tag";
    private static final String NOTIFY_URL_KEY = "notify_url";
    private static final String TRADE_TYPE_KEY = "trade_type";
    private static final String PRODUCT_ID_KEY = "product_id";
    private static final String LIMIT_PAY_KEY = "limit_pay";
    private static final String OPEN_ID_KEY = "openid";
    private static final String RECEIPT_KEY = "receipt";
    private static final String SCENE_INFO_KEY = "scene_info";

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<String, Object>(TWENTY);
        if (StringUtils.isNotBlank(appId)) { result.put(APP_ID_KEY, appId); }
        if (StringUtils.isNotBlank(mchId)) { result.put(MCH_ID_KEY, mchId); }
        if (StringUtils.isNotBlank(deviceInfo)) { result.put(DEVICE_INFO_KEY, deviceInfo); }
        if (StringUtils.isNotBlank(nonceStr)) { result.put(NONCE_STR_KEY, nonceStr); }
        if (StringUtils.isNotBlank(sign)) { result.put(SIGN_KEY, sign); }
        if (StringUtils.isNotBlank(signType)) { result.put(SIGN_TYPE_KEY, signType); }
        if (StringUtils.isNotBlank(body)) { result.put(BODY_KEY, body); }
        if (StringUtils.isNotBlank(detail)) { result.put(DETAIL_KEY, detail); }
        if (StringUtils.isNotBlank(attach)) { result.put(ATTACH_KEY, attach); }
        if (StringUtils.isNotBlank(outTradeNo)) { result.put(OUT_TRADE_NO_KEY, outTradeNo); }
        if (StringUtils.isNotBlank(feeType)) { result.put(FEE_TYPE_KEY, feeType); }
        if (totalFee != null) { result.put(TOTAL_FEE_KEY, totalFee); }
        if (StringUtils.isNotBlank(spbillCreateIp)) { result.put(SPBILL_CREATE_IP_KEY, spbillCreateIp); }
        if (StringUtils.isNotBlank(timeStart)) { result.put(TIME_START_KEY, timeStart); }
        if (StringUtils.isNotBlank(timeExpire)) { result.put(TIME_EXPIRE_KEY, timeExpire); }
        if (StringUtils.isNotBlank(goodsTag)) { result.put(GOODS_TAG_KEY, goodsTag); }
        if (StringUtils.isNotBlank(notifyUrl)) { result.put(NOTIFY_URL_KEY, notifyUrl); }
        if (StringUtils.isNotBlank(tradeType)) { result.put(TRADE_TYPE_KEY, tradeType); }
        if (StringUtils.isNotBlank(productId)) { result.put(PRODUCT_ID_KEY, productId); }
        if (StringUtils.isNotBlank(limitPay)) { result.put(LIMIT_PAY_KEY, limitPay); }
        if (StringUtils.isNotBlank(openId)) { result.put(OPEN_ID_KEY, openId); }
        if (StringUtils.isNotBlank(receipt)) { result.put(RECEIPT_KEY, receipt); }
        if (StringUtils.isNotBlank(sceneInfo)) { result.put(SCENE_INFO_KEY, sceneInfo); }
        return result;
    }

    public void fromMap(Map<String, Object> map) {
        String appId = (String) map.get(APP_ID_KEY);
        if (StringUtils.isNotBlank(appId)) { this.appId = appId; }
        String mchId = (String) map.get(MCH_ID_KEY);
        if (StringUtils.isNotBlank(mchId)) { this.mchId = mchId; }
        String deviceInfo = (String) map.get(DEVICE_INFO_KEY);
        if (StringUtils.isNotBlank(deviceInfo)) { this.deviceInfo = deviceInfo; }
        String nonceStr = (String) map.get(NONCE_STR_KEY);
        if (StringUtils.isNotBlank(nonceStr)) { this.nonceStr = nonceStr; }
        String sign = (String) map.get(SIGN_KEY);
        if (StringUtils.isNotBlank(sign)) { this.sign = sign; }
        String signType = (String) map.get(SIGN_TYPE_KEY);
        if (StringUtils.isNotBlank(signType)) { this.signType = signType; }
        String body = (String) map.get(BODY_KEY);
        if (StringUtils.isNotBlank(body)) { this.body = body; }
        String detail = (String) map.get(DETAIL_KEY);
        if (StringUtils.isNotBlank(detail)) { this.detail = detail; }
        String attach = (String) map.get(ATTACH_KEY);
        if (StringUtils.isNotBlank(attach)) { this.attach = attach; }
        String outTradeNo = (String) map.get(OUT_TRADE_NO_KEY);
        if (StringUtils.isNotBlank(outTradeNo)) { this.outTradeNo = outTradeNo; }
        String feeType = (String) map.get(FEE_TYPE_KEY);
        if (StringUtils.isNotBlank(feeType)) { this.feeType = feeType; }
        Integer totalFee = (Integer) map.get(TOTAL_FEE_KEY);
        if (totalFee != null) { this.totalFee = totalFee; }
        String spbillCreateIp = (String) map.get(SPBILL_CREATE_IP_KEY);
        if (StringUtils.isNotBlank(spbillCreateIp)) { this.spbillCreateIp = spbillCreateIp; }
        String timeStart = (String) map.get(TIME_START_KEY);
        if (StringUtils.isNotBlank(timeStart)) { this.timeStart = timeStart; }
        String timeExpire = (String) map.get(TIME_EXPIRE_KEY);
        if (StringUtils.isNotBlank(timeExpire)) { this.timeExpire = timeExpire; }
        String goodsTag = (String) map.get(GOODS_TAG_KEY);
        if (StringUtils.isNotBlank(goodsTag)) { this.goodsTag = goodsTag; }
        String notifyUrl = (String) map.get(NOTIFY_URL_KEY);
        if (StringUtils.isNotBlank(notifyUrl)) { this.notifyUrl = notifyUrl; }
        String tradeType = (String) map.get(TRADE_TYPE_KEY);
        if (StringUtils.isNotBlank(tradeType)) { this.tradeType = tradeType; }
        String productId = (String) map.get(PRODUCT_ID_KEY);
        if (StringUtils.isNotBlank(productId)) { this.productId = productId; }
        String limitPay = (String) map.get(LIMIT_PAY_KEY);
        if (StringUtils.isNotBlank(limitPay)) { this.limitPay = limitPay; }
        String openId = (String) map.get(OPEN_ID_KEY);
        if (StringUtils.isNotBlank(openId)) { this.openId = openId; }
        String receipt = (String) map.get(RECEIPT_KEY);
        if (StringUtils.isNotBlank(receipt)) { this.receipt = receipt; }
        String sceneInfo = (String) map.get(SCENE_INFO_KEY);
        if (StringUtils.isNotBlank(sceneInfo)) { this.sceneInfo = sceneInfo; }
    }

}

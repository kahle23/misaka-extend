package misaka.tencent.wx.pay.old;

import artoria.data.Mappable;
import artoria.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static artoria.common.Constants.TWENTY;

/**
 * 微信支付统一下单结果对象.
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1">统一下单</a>
 * @author Kahle
 */
@Deprecated
public class WxUnifiedOrderResult implements Serializable, Mappable {
    /**
     * 返回状态码 (Required:YES).
     * 此字段是通信标识，非交易标识.
     */
    private String returnCode;
    /**
     * 返回信息 (Required:NO).
     * 返回信息，如非空，为错误原因.
     */
    private String returnMsg;
    /**
     * 业务结果（SUCCESS/FAIL） (Required:YES).
     */
    private String resultCode;
    /**
     * 错误代码 (Required:NO).
     */
    private String errCode;
    /**
     * 错误代码描述 (Required:NO).
     */
    private String errCodeDes;
    /**
     * 小程序ID (Required:YES).
     * 调用接口提交的小程序ID.
     */
    private String appId;
    /**
     * 商户号 (Required:YES).
     * 调用接口提交的商户号.
     */
    private String mchId;
    /**
     * 设备号 (Required:NO).
     * 自定义参数，可以为请求支付的终端设备号等.
     */
    private String deviceInfo;
    /**
     * 随机字符串 (Required:YES).
     * 微信返回的随机字符串.
     */
    private String nonceStr;
    /**
     * 交易类型 (Required:YES).
     * 交易类型，取值为：JSAPI、NATIVE、APP等.
     */
    private String tradeType;
    /**
     * 预支付交易会话标识	 (Required:YES).
     * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时.
     */
    private String prepayId;
    /**
     * 签名类型 (Required:NO).
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5.
     */
    private String signType;
    /**
     * 签名 (Required:YES).
     * 微信返回的签名值.
     */
    private String sign;
    /**
     * 二维码链接 (Required:NO).
     * 此 url 用于生成支付二维码，然后提供给用户进行扫码支付（trade_type=NATIVE 时有返回）.
     */
    private String codeUrl;
    /**
     * 支付跳转链接 (Required:NO).
     * 拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付，mweb_url的有效期为5分钟。（trade_type=MWEB 时有返回）.
     */
    private String mwebUrl;

    private String unifiedOrderRequest;
    public String getUnifiedOrderRequest() {
        return unifiedOrderRequest;
    }
    public void setUnifiedOrderRequest(String unifiedOrderRequest) {
        this.unifiedOrderRequest = unifiedOrderRequest;
    }
    private String unifiedOrderResponse;
    public String getUnifiedOrderResponse() {
        return unifiedOrderResponse;
    }
    public void setUnifiedOrderResponse(String unifiedOrderResponse) {
        this.unifiedOrderResponse = unifiedOrderResponse;
    }

    public String getReturnCode() {

        return returnCode;
    }

    public void setReturnCode(String returnCode) {

        this.returnCode = returnCode;
    }

    public String getReturnMsg() {

        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {

        this.returnMsg = returnMsg;
    }

    public String getResultCode() {

        return resultCode;
    }

    public void setResultCode(String resultCode) {

        this.resultCode = resultCode;
    }

    public String getErrCode() {

        return errCode;
    }

    public void setErrCode(String errCode) {

        this.errCode = errCode;
    }

    public String getErrCodeDes() {

        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {

        this.errCodeDes = errCodeDes;
    }

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

    public String getTradeType() {

        return tradeType;
    }

    public void setTradeType(String tradeType) {

        this.tradeType = tradeType;
    }

    public String getPrepayId() {

        return prepayId;
    }

    public void setPrepayId(String prepayId) {

        this.prepayId = prepayId;
    }

    public String getSignType() {

        return signType;
    }

    public void setSignType(String signType) {

        this.signType = signType;
    }

    public String getSign() {

        return sign;
    }

    public void setSign(String sign) {

        this.sign = sign;
    }

    public String getCodeUrl() {

        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {

        this.codeUrl = codeUrl;
    }

    public String getMwebUrl() {

        return mwebUrl;
    }

    public void setMwebUrl(String mwebUrl) {

        this.mwebUrl = mwebUrl;
    }

    private static final String RETURN_CODE_KEY = "return_code";
    private static final String RETURN_MSG_KEY = "return_msg";
    private static final String RESULT_CODE_KEY = "result_code";
    private static final String ERR_CODE_KEY = "err_code";
    private static final String ERR_CODE_DES_KEY = "err_code_des";
    private static final String APP_ID_KEY = "appid";
    private static final String MCH_ID_KEY = "mch_id";
    private static final String DEVICE_INFO_KEY = "device_info";
    private static final String NONCE_STR_KEY = "nonce_str";
    private static final String TRADE_TYPE_KEY = "trade_type";
    private static final String PREPAY_ID_KEY = "prepay_id";
    private static final String SIGN_TYPE_KEY = "sign_type";
    private static final String SIGN_KEY = "sign";
    private static final String CODE_URL_KEY = "code_url";
    private static final String MWEB_URL_KEY = "mweb_url";

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<String, Object>(TWENTY);
        if (StringUtils.isNotBlank(returnCode)) { result.put(RETURN_CODE_KEY, returnCode); }
        if (StringUtils.isNotBlank(returnMsg)) { result.put(RETURN_MSG_KEY, returnMsg); }
        if (StringUtils.isNotBlank(resultCode)) { result.put(RESULT_CODE_KEY, resultCode); }
        if (StringUtils.isNotBlank(errCode)) { result.put(ERR_CODE_KEY, errCode); }
        if (StringUtils.isNotBlank(errCodeDes)) { result.put(ERR_CODE_DES_KEY, errCodeDes); }
        if (StringUtils.isNotBlank(appId)) { result.put(APP_ID_KEY, appId); }
        if (StringUtils.isNotBlank(mchId)) { result.put(MCH_ID_KEY, mchId); }
        if (StringUtils.isNotBlank(deviceInfo)) { result.put(DEVICE_INFO_KEY, deviceInfo); }
        if (StringUtils.isNotBlank(nonceStr)) { result.put(NONCE_STR_KEY, nonceStr); }
        if (StringUtils.isNotBlank(tradeType)) { result.put(TRADE_TYPE_KEY, tradeType); }
        if (StringUtils.isNotBlank(prepayId)) { result.put(PREPAY_ID_KEY, prepayId); }
        if (StringUtils.isNotBlank(signType)) { result.put(SIGN_TYPE_KEY, signType); }
        if (StringUtils.isNotBlank(sign)) { result.put(SIGN_KEY, sign); }
        if (StringUtils.isNotBlank(codeUrl)) { result.put(CODE_URL_KEY, codeUrl); }
        if (StringUtils.isNotBlank(mwebUrl)) { result.put(MWEB_URL_KEY, mwebUrl); }
        return result;
    }

    @Override
    public void fromMap(Map<String, Object> map) {
        String returnCode = (String) map.get(RETURN_CODE_KEY);
        if (StringUtils.isNotBlank(returnCode)) { this.returnCode = returnCode; }
        String returnMsg = (String) map.get(RETURN_MSG_KEY);
        if (StringUtils.isNotBlank(returnMsg)) { this.returnMsg = returnMsg; }
        String resultCode = (String) map.get(RESULT_CODE_KEY);
        if (StringUtils.isNotBlank(resultCode)) { this.resultCode = resultCode; }
        String errCode = (String) map.get(ERR_CODE_KEY);
        if (StringUtils.isNotBlank(errCode)) { this.errCode = errCode; }
        String errCodeDes = (String) map.get(ERR_CODE_DES_KEY);
        if (StringUtils.isNotBlank(errCodeDes)) { this.errCodeDes = errCodeDes; }
        String appId = (String) map.get(APP_ID_KEY);
        if (StringUtils.isNotBlank(appId)) { this.appId = appId; }
        String mchId = (String) map.get(MCH_ID_KEY);
        if (StringUtils.isNotBlank(mchId)) { this.mchId = mchId; }
        String deviceInfo = (String) map.get(DEVICE_INFO_KEY);
        if (StringUtils.isNotBlank(deviceInfo)) { this.deviceInfo = deviceInfo; }
        String nonceStr = (String) map.get(NONCE_STR_KEY);
        if (StringUtils.isNotBlank(nonceStr)) { this.nonceStr = nonceStr; }
        String tradeType = (String) map.get(TRADE_TYPE_KEY);
        if (StringUtils.isNotBlank(tradeType)) { this.tradeType = tradeType; }
        String prepayId = (String) map.get(PREPAY_ID_KEY);
        if (StringUtils.isNotBlank(prepayId)) { this.prepayId = prepayId; }
        String signType = (String) map.get(SIGN_TYPE_KEY);
        if (StringUtils.isNotBlank(signType)) { this.signType = signType; }
        String sign = (String) map.get(SIGN_KEY);
        if (StringUtils.isNotBlank(sign)) { this.sign = sign; }
        String codeUrl = (String) map.get(CODE_URL_KEY);
        if (StringUtils.isNotBlank(codeUrl)) { this.codeUrl = codeUrl; }
        String mwebUrl = (String) map.get(MWEB_URL_KEY);
        if (StringUtils.isNotBlank(mwebUrl)) { this.mwebUrl = mwebUrl; }
    }

}

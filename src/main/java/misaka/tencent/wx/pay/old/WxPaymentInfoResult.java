package misaka.tencent.wx.pay.old;

import java.io.Serializable;

/**
 * 微信支付调起支付的结果对象.
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=5">调起支付API</a>
 * @author Kahle
 */
@Deprecated
public class WxPaymentInfoResult implements Serializable {
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
     * 微信分配的小程序ID.
     */
    private String appId;
    /**
     * 商户号 (Required:NO).
     * 微信支付分配的商户号.
     */
    private String partnerId;
    /**
     * 预支付交易会话ID (Required:YES).
     * 微信返回的支付交易会话ID.
     */
    private String prepayId;
    /**
     * 数据包 (Required:YES).
     * 统一下单接口返回的 prepay_id 参数值（因为 package 为 Java 的关键词）.
     * 提交格式如：“prepay_id=wx2017033010242291fcfe0db70013231072”.
     */
    private String packet;
    /**
     * 随机串 (Required:YES).
     * 随机字符串，不长于32位.
     */
    private String nonceStr;
    /**
     * 时间戳 (Required:YES).
     * 时间戳从1970年1月1日00:00:00至今的秒数,即当前的时间.
     */
    private String timestamp;
    /**
     * 签名方式 (Required:YES).
     * 签名类型，默认为 MD5，支持 HMAC-SHA256 和 MD5.
     * 注意此处需与统一下单的签名类型一致.
     */
    private String signType;
    /**
     * 签名 (Required:YES).
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

    public String getPartnerId() {

        return partnerId;
    }

    public void setPartnerId(String partnerId) {

        this.partnerId = partnerId;
    }

    public String getPrepayId() {

        return prepayId;
    }

    public void setPrepayId(String prepayId) {

        this.prepayId = prepayId;
    }

    public String getPacket() {

        return packet;
    }

    public void setPacket(String packet) {

        this.packet = packet;
    }

    public String getNonceStr() {

        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {

        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {

        return timestamp;
    }

    public void setTimestamp(String timestamp) {

        this.timestamp = timestamp;
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

}

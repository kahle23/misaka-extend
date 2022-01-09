package misaka.tencent.wx.pay.old;

import artoria.data.Mappable;
import artoria.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static artoria.common.Constants.THIRTY;

/**
 * 微信支付的支付结果通知对象.
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7&index=8">支付结果通知</a>
 * @author Kahle
 */
@Deprecated
public class WxOrderPayNotifyResult implements Serializable, Mappable {
    public static final String SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    public static final String FAIL = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>";
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
     * 商户号 (Required:YES).
     * 微信支付分配的商户号.
     */
    private String mchId;
    /**
     * 设备号 (Required:NO).
     * 微信支付分配的终端设备号.
     */
    private String deviceInfo;
    /**
     * 用户标识 (Required:YES).
     * 用户在商户 appId 下的唯一标识.
     */
    private String openId;
    /**
     * 是否关注公众账号 (Required:YES).
     * 用户是否关注公众账号：Y-关注、N-未关注.
     */
    private String isSubscribe;
    /**
     * 交易类型 (Required:YES).
     * JSAPI、NATIVE、APP.
     */
    private String tradeType;
    /**
     * 付款银行 (Required:YES).
     * 银行类型采用字符串类型的银行标识.
     */
    private String bankType;
    /**
     * 订单金额 (Required:YES).
     * 订单总金额，单位为分.
     */
    private String totalFee;
    /**
     * 应结订单金额 (Required:NO).
     * 应结订单金额=订单金额-非充值代金券金额，应结订单金额&lt;=订单金额.
     */
    private String settlementTotalFee;
    /**
     * 货币种类 (Required:NO).
     * 货币类型，符合 ISO4217 标准的三位字母代码（默认人民币：CNY）.
     */
    private String feeType;
    /**
     * 现金支付金额 (Required:YES).
     * 现金支付金额订单现金支付金额.
     */
    private String cashFee;
    /**
     * 现金支付货币类型 (Required:NO).
     * 货币类型，符合 ISO4217 标准的三位字母代码（默认人民币：CNY）.
     */
    private String cashFeeType;
    /**
     * 总代金券金额 (Required:NO).
     * 代金券金额&lt;=订单金额，订单金额-代金券金额=现金支付金额.
     */
    private String couponFee;
    /**
     * 代金券使用数量 (Required:NO).
     */
    private String couponCount;
//    代金券类型	coupon_type_$n	否
//    代金券ID	coupon_id_$n	否
//    单个代金券支付金额	coupon_fee_$n	否
    /**
     * 微信支付订单号 (Required:YES).
     */
    private String transactionId;
    /**
     * 商户订单号 (Required:YES).
     * 商户系统内部订单号，要求32个字符内.
     */
    private String outTradeNo;
    /**
     * 商家数据包 (Required:NO).
     * 商家数据包，原样返回.
     */
    private String attach;
    /**
     * 支付完成时间 (Required:YES).
     * 支付完成时间，格式为“yyyyMMddHHmmss”.
     */
    private String timeEnd;
    /**
     * 随机字符串 (Required:YES).
     * 随机字符串，不长于32位.
     */
    private String nonceStr;
    /**
     * 签名类型 (Required:NO).
     * 目前支持 HMAC-SHA256 和 MD5 （默认为 MD5）.
     */
    private String signType;
    /**
     * 签名 (Required:YES).
     */
    private String sign;

    private String calculatedSign;
    public String getCalculatedSign() {
        return calculatedSign;
    }
    public void setCalculatedSign(String calculatedSign) {
        this.calculatedSign = calculatedSign;
    }
    private Boolean signVerification;
    public Boolean getSignVerification() {
        return signVerification;
    }
    public void setSignVerification(Boolean signVerification) {
        this.signVerification = signVerification;
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

    public String getOpenId() {

        return openId;
    }

    public void setOpenId(String openId) {

        this.openId = openId;
    }

    public String getIsSubscribe() {

        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {

        this.isSubscribe = isSubscribe;
    }

    public String getTradeType() {

        return tradeType;
    }

    public void setTradeType(String tradeType) {

        this.tradeType = tradeType;
    }

    public String getBankType() {

        return bankType;
    }

    public void setBankType(String bankType) {

        this.bankType = bankType;
    }

    public String getTotalFee() {

        return totalFee;
    }

    public void setTotalFee(String totalFee) {

        this.totalFee = totalFee;
    }

    public String getSettlementTotalFee() {

        return settlementTotalFee;
    }

    public void setSettlementTotalFee(String settlementTotalFee) {

        this.settlementTotalFee = settlementTotalFee;
    }

    public String getFeeType() {

        return feeType;
    }

    public void setFeeType(String feeType) {

        this.feeType = feeType;
    }

    public String getCashFee() {

        return cashFee;
    }

    public void setCashFee(String cashFee) {

        this.cashFee = cashFee;
    }

    public String getCashFeeType() {

        return cashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {

        this.cashFeeType = cashFeeType;
    }

    public String getCouponFee() {

        return couponFee;
    }

    public void setCouponFee(String couponFee) {

        this.couponFee = couponFee;
    }

    public String getCouponCount() {

        return couponCount;
    }

    public void setCouponCount(String couponCount) {

        this.couponCount = couponCount;
    }

    public String getTransactionId() {

        return transactionId;
    }

    public void setTransactionId(String transactionId) {

        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {

        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {

        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {

        return attach;
    }

    public void setAttach(String attach) {

        this.attach = attach;
    }

    public String getTimeEnd() {

        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {

        this.timeEnd = timeEnd;
    }

    public String getNonceStr() {

        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {

        this.nonceStr = nonceStr;
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

    private static final String RETURN_CODE_KEY = "return_code";
    private static final String RETURN_MSG_KEY = "return_msg";
    private static final String RESULT_CODE_KEY = "result_code";
    private static final String ERR_CODE_KEY = "err_code";
    private static final String ERR_CODE_DES_KEY = "err_code_des";
    private static final String APP_ID_KEY = "appid";
    private static final String MCH_ID_KEY = "mch_id";
    private static final String DEVICE_INFO_KEY = "device_info";
    private static final String OPEN_ID_KEY = "openid";
    private static final String IS_SUBSCRIBE_KEY = "is_subscribe";
    private static final String TRADE_TYPE_KEY = "trade_type";
    private static final String BANK_TYPE_KEY = "bank_type";
    private static final String TOTAL_FEE_KEY = "total_fee";
    private static final String SETTLEMENT_TOTAL_FEE_KEY = "settlement_total_fee";
    private static final String FEE_TYPE_KEY = "fee_type";
    private static final String CASH_FEE_KEY = "cash_fee";
    private static final String CASH_FEE_TYPE_KEY = "cash_fee_type";
    private static final String COUPON_FEE_KEY = "coupon_fee";
    private static final String COUPON_COUNT_KEY = "coupon_count";
    private static final String TRANSACTION_ID_KEY = "transaction_id";
    private static final String OUT_TRADE_NO_KEY = "out_trade_no";
    private static final String ATTACH_KEY = "attach";
    private static final String TIME_END_KEY = "time_end";
    private static final String NONCE_STR_KEY = "nonce_str";
    private static final String SIGN_TYPE_KEY = "sign_type";
    private static final String SIGN_KEY = "sign";

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<String, Object>(THIRTY);
        if (StringUtils.isNotBlank(returnCode)) { result.put(RETURN_CODE_KEY, returnCode); }
        if (StringUtils.isNotBlank(returnMsg)) { result.put(RETURN_MSG_KEY, returnMsg); }
        if (StringUtils.isNotBlank(resultCode)) { result.put(RESULT_CODE_KEY, resultCode); }
        if (StringUtils.isNotBlank(errCode)) { result.put(ERR_CODE_KEY, errCode); }
        if (StringUtils.isNotBlank(errCodeDes)) { result.put(ERR_CODE_DES_KEY, errCodeDes); }
        if (StringUtils.isNotBlank(appId)) { result.put(APP_ID_KEY, appId); }
        if (StringUtils.isNotBlank(mchId)) { result.put(MCH_ID_KEY, mchId); }
        if (StringUtils.isNotBlank(deviceInfo)) { result.put(DEVICE_INFO_KEY, deviceInfo); }
        if (StringUtils.isNotBlank(openId)) { result.put(OPEN_ID_KEY, openId); }
        if (StringUtils.isNotBlank(isSubscribe)) { result.put(IS_SUBSCRIBE_KEY, isSubscribe); }
        if (StringUtils.isNotBlank(tradeType)) { result.put(TRADE_TYPE_KEY, tradeType); }
        if (StringUtils.isNotBlank(bankType)) { result.put(BANK_TYPE_KEY, bankType); }
        if (StringUtils.isNotBlank(totalFee)) { result.put(TOTAL_FEE_KEY, totalFee); }
        if (StringUtils.isNotBlank(settlementTotalFee)) { result.put(SETTLEMENT_TOTAL_FEE_KEY, settlementTotalFee); }
        if (StringUtils.isNotBlank(feeType)) { result.put(FEE_TYPE_KEY, feeType); }
        if (StringUtils.isNotBlank(cashFee)) { result.put(CASH_FEE_KEY, cashFee); }
        if (StringUtils.isNotBlank(cashFeeType)) { result.put(CASH_FEE_TYPE_KEY, cashFeeType); }
        if (StringUtils.isNotBlank(couponFee)) { result.put(COUPON_FEE_KEY, couponFee); }
        if (StringUtils.isNotBlank(couponCount)) { result.put(COUPON_COUNT_KEY, couponCount); }
//        代金券类型	coupon_type_$n	否
//        代金券ID	coupon_id_$n	否
//        单个代金券支付金额	coupon_fee_$n	否
        if (StringUtils.isNotBlank(transactionId)) { result.put(TRANSACTION_ID_KEY, transactionId); }
        if (StringUtils.isNotBlank(outTradeNo)) { result.put(OUT_TRADE_NO_KEY, outTradeNo); }
        if (StringUtils.isNotBlank(attach)) { result.put(ATTACH_KEY, attach); }
        if (StringUtils.isNotBlank(timeEnd)) { result.put(TIME_END_KEY, timeEnd); }
        if (StringUtils.isNotBlank(nonceStr)) { result.put(NONCE_STR_KEY, nonceStr); }
        if (StringUtils.isNotBlank(signType)) { result.put(SIGN_TYPE_KEY, signType); }
        if (StringUtils.isNotBlank(sign)) { result.put(SIGN_KEY, sign); }
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
        String openId = (String) map.get(OPEN_ID_KEY);
        if (StringUtils.isNotBlank(openId)) { this.openId = openId; }
        String isSubscribe = (String) map.get(IS_SUBSCRIBE_KEY);
        if (StringUtils.isNotBlank(isSubscribe)) { this.isSubscribe = isSubscribe; }
        String tradeType = (String) map.get(TRADE_TYPE_KEY);
        if (StringUtils.isNotBlank(tradeType)) { this.tradeType = tradeType; }
        String bankType = (String) map.get(BANK_TYPE_KEY);
        if (StringUtils.isNotBlank(bankType)) { this.bankType = bankType; }
        String totalFee = (String) map.get(TOTAL_FEE_KEY);
        if (StringUtils.isNotBlank(totalFee)) { this.totalFee = totalFee; }
        String settlementTotalFee = (String) map.get(SETTLEMENT_TOTAL_FEE_KEY);
        if (StringUtils.isNotBlank(settlementTotalFee)) { this.settlementTotalFee = settlementTotalFee; }
        String feeType = (String) map.get(FEE_TYPE_KEY);
        if (StringUtils.isNotBlank(feeType)) { this.feeType = feeType; }
        String cashFee = (String) map.get(CASH_FEE_KEY);
        if (StringUtils.isNotBlank(cashFee)) { this.cashFee = cashFee; }
        String cashFeeType = (String) map.get(CASH_FEE_TYPE_KEY);
        if (StringUtils.isNotBlank(cashFeeType)) { this.cashFeeType = cashFeeType; }
        String couponFee = (String) map.get(COUPON_FEE_KEY);
        if (StringUtils.isNotBlank(couponFee)) { this.couponFee = couponFee; }
        String couponCount = (String) map.get(COUPON_COUNT_KEY);
        if (StringUtils.isNotBlank(couponCount)) { this.couponCount = couponCount; }
//        代金券类型	coupon_type_$n	否
//        代金券ID	coupon_id_$n	否
//        单个代金券支付金额	coupon_fee_$n	否
        String transactionId = (String) map.get(TRANSACTION_ID_KEY);
        if (StringUtils.isNotBlank(transactionId)) { this.transactionId = transactionId; }
        String outTradeNo = (String) map.get(OUT_TRADE_NO_KEY);
        if (StringUtils.isNotBlank(outTradeNo)) { this.outTradeNo = outTradeNo; }
        String attach = (String) map.get(ATTACH_KEY);
        if (StringUtils.isNotBlank(attach)) { this.attach = attach; }
        String timeEnd = (String) map.get(TIME_END_KEY);
        if (StringUtils.isNotBlank(timeEnd)) { this.timeEnd = timeEnd; }
        String nonceStr = (String) map.get(NONCE_STR_KEY);
        if (StringUtils.isNotBlank(nonceStr)) { this.nonceStr = nonceStr; }
        String signType = (String) map.get(SIGN_TYPE_KEY);
        if (StringUtils.isNotBlank(signType)) { this.signType = signType; }
        String sign = (String) map.get(SIGN_KEY);
        if (StringUtils.isNotBlank(sign)) { this.sign = sign; }
    }

}

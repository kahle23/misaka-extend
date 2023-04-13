package misaka.tencent.wx.pay;

import artoria.common.GenericResult;
import artoria.data.AppType;
import artoria.data.bean.BeanUtils;
import artoria.data.json.JsonUtils;
import artoria.time.DateUtils;
import artoria.util.Assert;
import artoria.util.StringUtils;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayOrderCloseRequest;
import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderCloseResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import misaka.pay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import static artoria.common.Constants.*;
import static artoria.data.AppType.*;

public class WxPayProvider implements PayProvider {
    private static final String TIME_PATTERN = "yyyyMMddHHmmss";
    private static final String BILL_CREATE_IP = "10.0.0.1";
    private static Logger log = LoggerFactory.getLogger(WxPayProvider.class);
    private WxPayService wxPayService;

    public WxPayProvider(WxPayService wxPayService) {
        Assert.notNull(wxPayService, "Parameter \"wxPayService\" must not null. ");
        WxPayConfig config = wxPayService.getConfig();
        Assert.notNull(config, "Parameter \"wxPayService\" has not been initialized. ");
        this.wxPayService = wxPayService;
    }

    private void handleError(String logMsg, Exception ex, GenericResult result) {
        String code;
        String message;
        if (ex instanceof WxPayException) {
            WxPayException wxEx = (WxPayException) ex;
            code = wxEx.getErrCode();
            message = wxEx.getErrCodeDes();
        }
        else {
            code = FAILURE;
            message = ex.getMessage();
        }
        result.setMessage(StringUtils.isNotBlank(message) ? message : FAILURE);
        result.setCode(StringUtils.isNotBlank(code) ? code : FAILURE);
        log.info(logMsg, ex);
    }

    @Override
    public OrderPayResult payOrder(OrderPayModel orderPayModel) {
        OrderPayResult orderPayResult = new OrderPayResult();
        try {
            Assert.notNull(orderPayModel, "Parameter \"orderPayModel\" must not null. ");
            String appId = orderPayModel.getAppId();
            String appType = orderPayModel.getAppType();
            String payWay = orderPayModel.getPayWay();

            orderPayResult.setAppId(appId);
            orderPayResult.setAppType(appType);
            orderPayResult.setPayWay(payWay);

            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
            wxPayUnifiedOrderRequest.setBody(orderPayModel.getTitle());
            wxPayUnifiedOrderRequest.setDetail(orderPayModel.getDescription());
            String currencyType = orderPayModel.getCurrencyType();
            if (StringUtils.isNotBlank(currencyType)) {
                wxPayUnifiedOrderRequest.setFeeType(currencyType);
            }
            String totalAmountStr = orderPayModel.getTotalAmount();
            BigDecimal totalAmount = new BigDecimal(totalAmountStr);
            totalAmount = totalAmount.multiply(BigDecimal.valueOf(ONE_HUNDRED));
            wxPayUnifiedOrderRequest.setTotalFee(totalAmount.intValue());
            wxPayUnifiedOrderRequest.setOutTradeNo(orderPayModel.getOutTradeId());
            Date startTime = orderPayModel.getStartTime();
            if (startTime != null) {
                wxPayUnifiedOrderRequest.setTimeStart(DateUtils.format(startTime, TIME_PATTERN));
            }
            Date expirationTime = orderPayModel.getExpirationTime();
            if (expirationTime != null) {
                wxPayUnifiedOrderRequest.setTimeExpire(DateUtils.format(expirationTime, TIME_PATTERN));
            }

            wxPayUnifiedOrderRequest.setAttach(orderPayModel.getPassBack());
            String openId = (String) orderPayModel.get("openId");
            if (StringUtils.isNotBlank(openId)) {
                wxPayUnifiedOrderRequest.setOpenid(openId);
            }
            String spBillCreateIp = (String) orderPayModel.get("spbillCreateIp");
            if (StringUtils.isNotBlank(spBillCreateIp)) {
                wxPayUnifiedOrderRequest.setSpbillCreateIp(spBillCreateIp);
            }
            else {
                wxPayUnifiedOrderRequest.setSpbillCreateIp(BILL_CREATE_IP);
            }
            String notifyUrl = orderPayModel.getNotifyUrl();
            if (StringUtils.isNotBlank(notifyUrl)) {
                wxPayUnifiedOrderRequest.setNotifyUrl(notifyUrl);
            }
            String tradeType = (String) orderPayModel.get("tradeType");
            if (StringUtils.isNotBlank(tradeType)) {
                wxPayUnifiedOrderRequest.setTradeType(tradeType);
            }
            else {
                AppType appTypeEnum = AppType.valueOf(appType);
                // NATIVE：Native支付，用户打开"微信扫一扫“，扫描商户的二维码后完成支付
                if (APP_ANDROID.equals(appTypeEnum) || APP_IOS.equals(appTypeEnum)) {
                    // APP：App支付，商户APP中集成微信SDK，用户点击后跳转到微信内完成支付
                    wxPayUnifiedOrderRequest.setTradeType("APP");
                }
                else if (WEB_MOBILE_PHONE.equals(appTypeEnum)) {
                    // MWEB：H5支付，用户在微信以外的手机浏览器请求微信支付的场景唤起微信支付
                    wxPayUnifiedOrderRequest.setTradeType("MWEB");
                }
                else if (WEIXIN_MINI_APP.equals(appTypeEnum)) {
                    // JSAPI：JSAPI支付（或小程序支付），用户通过微信扫码、关注公众号等方式进入商家H5页面，并在微信内调用JSSDK完成支付
                    wxPayUnifiedOrderRequest.setTradeType("JSAPI");
                }
                else {
                    throw new IllegalArgumentException("Unsupported application types. ");
                }
            }
            log.info("Pay order internal input: {}", JsonUtils.toJsonString(wxPayUnifiedOrderRequest));
            Object payResult = wxPayService.createOrder(wxPayUnifiedOrderRequest);
            log.info("Pay order internal output: {}", JsonUtils.toJsonString(payResult));

            orderPayResult.setCode(SUCCESS);
            orderPayResult.setPayResult(payResult);
        }
        catch (Exception e) {
            handleError("Pay order failure. ", e, orderPayResult);
        }
        return orderPayResult;
    }

    @Override
    public OrderQueryResult queryOrder(OrderQueryModel orderQueryModel) {
        OrderQueryResult orderQueryResult = new OrderQueryResult();
        try {
            Assert.notNull(orderQueryModel, "Parameter \"orderQueryModel\" must not null. ");
            String appId = orderQueryModel.getAppId();
            String appType = orderQueryModel.getAppType();
            String payWay = orderQueryModel.getPayWay();
            String tradeId = orderQueryModel.getTradeId();
            String outTradeId = orderQueryModel.getOutTradeId();

            orderQueryResult.setAppId(appId);
            orderQueryResult.setAppType(appType);
            orderQueryResult.setPayWay(payWay);

            if (StringUtils.isBlank(tradeId)
                    && StringUtils.isBlank(outTradeId)) {
                throw new IllegalArgumentException(
                    "Parameter \"tradeId\" and \"outTradeId\" cannot be empty at the same time. "
                );
            }
            WxPayOrderQueryRequest wxPayOrderQueryRequest = new WxPayOrderQueryRequest();
            if (StringUtils.isNotBlank(tradeId)) {
                wxPayOrderQueryRequest.setTransactionId(tradeId);
            }
            if (StringUtils.isNotBlank(outTradeId)) {
                wxPayOrderQueryRequest.setOutTradeNo(outTradeId);
            }
            log.info("Query order internal input: {}", JsonUtils.toJsonString(wxPayOrderQueryRequest));
            WxPayOrderQueryResult wxPayOrderQueryResult = wxPayService.queryOrder(wxPayOrderQueryRequest);
            log.info("Query order internal output: {}", JsonUtils.toJsonString(wxPayOrderQueryResult));

            orderQueryResult.rawData(wxPayOrderQueryResult.getXmlString());
            orderQueryResult.putAll(BeanUtils.beanToMap(wxPayOrderQueryResult));
            orderQueryResult.remove("xmlString");
            orderQueryResult.remove("class");
            orderQueryResult.setCode(SUCCESS);
            if (!"SUCCESS".equals(orderQueryResult.get("resultCode"))) {
                String errCodeDes = (String) orderQueryResult.get("errCodeDes");
                String errCode = (String) orderQueryResult.get("errCode");
                orderQueryResult.setCode(errCode);
                orderQueryResult.setMessage(errCodeDes);
            }
            orderQueryResult.setTradeId(wxPayOrderQueryResult.getTransactionId());
            orderQueryResult.setOutTradeId(wxPayOrderQueryResult.getOutTradeNo());
            orderQueryResult.setCurrencyType(wxPayOrderQueryResult.getFeeType());
            Integer totalFee1 = wxPayOrderQueryResult.getTotalFee();
            if (totalFee1 != null && totalFee1 >= ZERO) {
                BigDecimal totalFee = BigDecimal.valueOf(totalFee1);
                BigDecimal oneHundred = BigDecimal.valueOf(ONE_HUNDRED);
                BigDecimal totalAmount = totalFee.divide(oneHundred, TWO, RoundingMode.HALF_UP);
                orderQueryResult.setTotalAmount(String.valueOf(totalAmount));
            }
            orderQueryResult.setTradeStatus(wxPayOrderQueryResult.getTradeState());
            orderQueryResult.setPassBack(wxPayOrderQueryResult.getAttach());
        }
        catch (Exception e) {
            handleError("Query order failure. ", e, orderQueryResult);
        }
        return orderQueryResult;
    }

    @Override
    public OrderCloseResult closeOrder(OrderCloseModel orderCloseModel) {
        OrderCloseResult orderCloseResult = new OrderCloseResult();
        try {
            Assert.notNull(orderCloseModel, "Parameter \"orderCloseModel\" must not null. ");
            String appId = orderCloseModel.getAppId();
            String appType = orderCloseModel.getAppType();
            String payWay = orderCloseModel.getPayWay();
            String outTradeId = orderCloseModel.getOutTradeId();
            Assert.notBlank(outTradeId, "Parameter \"outTradeId\" must not blank. ");

            orderCloseResult.setAppId(appId);
            orderCloseResult.setAppType(appType);
            orderCloseResult.setPayWay(payWay);

            WxPayOrderCloseRequest wxPayOrderCloseRequest = new WxPayOrderCloseRequest();
            wxPayOrderCloseRequest.setOutTradeNo(outTradeId);
            log.info("Close order internal input: {}", JsonUtils.toJsonString(wxPayOrderCloseRequest));
            WxPayOrderCloseResult wxPayOrderCloseResult = wxPayService.closeOrder(wxPayOrderCloseRequest);
            log.info("Close order internal output: {}", JsonUtils.toJsonString(wxPayOrderCloseResult));

            orderCloseResult.putAll(BeanUtils.beanToMap(wxPayOrderCloseResult));
            orderCloseResult.setCode(SUCCESS);
            orderCloseResult.setMessage(wxPayOrderCloseResult.getReturnMsg());
            orderCloseResult.setTradeId(orderCloseModel.getTradeId());
            orderCloseResult.setOutTradeId(orderCloseModel.getOutTradeId());
        }
        catch (Exception e) {
            handleError("Close order failure. ", e, orderCloseResult);
        }
        return orderCloseResult;
    }

    @Override
    public PayNotifyResult payNotify(PayNotifyModel payNotifyModel) {
        PayNotifyResult payNotifyResult = new PayNotifyResult();
        try {
            Assert.notNull(payNotifyModel, "Parameter \"payNotifyModel\" must not null. ");
            String appId = payNotifyModel.getAppId();
            String appType = payNotifyModel.getAppType();
            String payWay = payNotifyModel.getPayWay();
            Object notify = payNotifyModel.getNotify();
            Assert.notNull(notify, "Parameter \"notify\" must not null. ");

            payNotifyResult.setAppId(appId);
            payNotifyResult.setAppType(appType);
            payNotifyResult.setPayWay(payWay);

            String xmlData = String.valueOf(notify);
            log.info("Pay notify internal input: {}", xmlData);
            WxPayOrderNotifyResult wxPayOrderNotifyResult = wxPayService.parseOrderNotifyResult(xmlData);
            log.info("Pay notify internal output: {}", JsonUtils.toJsonString(wxPayOrderNotifyResult));
            //payNotifyResult.rawData(wxPayOrderNotifyResult);
            payNotifyResult.putAll(BeanUtils.beanToMap(wxPayOrderNotifyResult));
            payNotifyResult.setCode(SUCCESS);
            payNotifyResult.setMessage(wxPayOrderNotifyResult.getReturnMsg());
            payNotifyResult.setTradeId(wxPayOrderNotifyResult.getTransactionId());
            payNotifyResult.setOutTradeId(wxPayOrderNotifyResult.getOutTradeNo());
            payNotifyResult.setCurrencyType(wxPayOrderNotifyResult.getFeeType());
            BigDecimal totalFee = BigDecimal.valueOf(wxPayOrderNotifyResult.getTotalFee());
            BigDecimal oneHundred = BigDecimal.valueOf(ONE_HUNDRED);
            BigDecimal totalAmount = totalFee.divide(oneHundred, TWO, RoundingMode.HALF_UP);
            payNotifyResult.setTotalAmount(String.valueOf(totalAmount));
            payNotifyResult.setPassBack(wxPayOrderNotifyResult.getAttach());
        }
        catch (Exception e) {
            handleError("Pay notify failure. ", e, payNotifyResult);
        }
        return payNotifyResult;
    }

}

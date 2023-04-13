package misaka.tencent.wx.pay.old;

import artoria.codec.HexUtils;
import artoria.crypto.Hash;
import artoria.crypto.Hmac;
import artoria.crypto.KeyUtils;
import artoria.data.bean.BeanUtils;
import artoria.data.xml.XmlClassAlias;
import artoria.data.xml.XmlUtils;
import artoria.exception.ExceptionUtils;
import artoria.net.*;
import artoria.time.DateUtils;
import artoria.util.Assert;
import artoria.util.RandomUtils;
import artoria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.util.*;

import static artoria.common.Constants.*;

/**
 * WeiXin pay service implementation.
 * @author Kahle
 */
@Deprecated
public class WxPayServiceImpl implements WxPayService {
    private static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static Logger log = LoggerFactory.getLogger(WxPayServiceImpl.class);
    private String appId;
    private String mchId;
    private String mchKey;
    private Hmac hmacSha256;
    private Hash md5;

    public WxPayServiceImpl(String appId, String mchId, String mchKey) {
        Charset charset = Charset.forName(UTF_8);
        byte[] keyBytes = mchKey.getBytes(charset);
        SecretKey secretKey =
                KeyUtils.parseSecretKey(HMAC_SHA256, keyBytes);
        this.hmacSha256 = new Hmac(HMAC_SHA256, secretKey);
        this.md5 = new Hash(MD5);
        this.appId = appId;
        this.mchId = mchId;
        this.mchKey = mchKey;
    }

    @Override
    public String createSignature(Map<?, ?> params, String signAlgorithm, String... ignoredParams) {
        // Parameters validation.
        Assert.notEmpty(params, "Parameter \"params\" must not empty. ");
        // List of parameters to ignore.
        List<String> ignoredList = new ArrayList<String>(Arrays.asList(ignoredParams));
        ignoredList.add("sign");
        // Set default value for the "algorithm".
        if (StringUtils.isBlank(signAlgorithm)) { signAlgorithm = MD5; }
        // Delete "-" and "_" from the "algorithm".
        String inputAlgorithm = signAlgorithm;
        if (signAlgorithm.contains(MINUS)) {
            signAlgorithm = StringUtils.replace(signAlgorithm, MINUS, EMPTY_STRING);
        }
        if (signAlgorithm.contains(UNDERLINE)) {
            signAlgorithm = StringUtils.replace(signAlgorithm, UNDERLINE, EMPTY_STRING);
        }
        // Sort the data and assemble it into a string to be signed.
        SortedMap<Object, Object> sortedMap = new TreeMap<Object, Object>(params);
        Set<Object> keySet = sortedMap.keySet();
        StringBuilder willSign = new StringBuilder();
        for (Object keyObj : keySet) {
            String keyStr = String.valueOf(keyObj);
            Object valObj = params.get(keyStr);
            String valStr = valObj != null
                    ? String.valueOf(valObj) : EMPTY_STRING;
            if (StringUtils.isNotBlank(keyStr)
                    && !ignoredList.contains(keyStr)) {
                willSign.append(keyStr)
                        .append(EQUAL)
                        .append(valStr)
                        .append(AMPERSAND);
            }
        }
        willSign.append("key=").append(mchKey);
        // Sign according to the signature algorithm.
        String sign, willSignStr = willSign.toString();
        try {
            byte[] digest, bytes = willSignStr.getBytes(UTF_8);
            if (MD5.equalsIgnoreCase(signAlgorithm)) {
                digest = md5.digest(bytes);
            }
            else if (HMAC_SHA256.equalsIgnoreCase(signAlgorithm)) {
                digest = hmacSha256.digest(bytes);
            }
            else {
                throw new WxPayException("Unsupported signature algorithm! ");
            }
            sign = HexUtils.encodeToString(digest);
            sign = sign.toUpperCase();
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
        // Print log.
        log.info("Create the \"{}\" signature \"{}\" by \"{}\". ", inputAlgorithm, sign, willSignStr);
        return sign;
    }

    @Override
    public WxPaymentInfoResult createPaymentInfo(WxUnifiedOrderResult wxUnifiedOrderResult) {
        WxPaymentInfoResult result = BeanUtils.beanToBean(wxUnifiedOrderResult, WxPaymentInfoResult.class);
        result.setTimestamp(String.valueOf(DateUtils.getTimeInSeconds()));
        result.setNonceStr(RandomUtils.nextString(TWENTY));
        result.setAppId(appId);
        result.setSign(null);
        String tradeType = wxUnifiedOrderResult.getTradeType();
        String signType = wxUnifiedOrderResult.getSignType();
        // JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付
        if ("JSAPI".equals(tradeType)) {
            String prepayId = wxUnifiedOrderResult.getPrepayId();
            result.setPacket("prepay_id=" + prepayId);
            Map<String, Object> params = new HashMap<String, Object>(SIX);
            params.put("appId", result.getAppId());
            params.put("timeStamp", result.getTimestamp());
            params.put("nonceStr", result.getNonceStr());
            params.put("package", result.getPacket());
            params.put("signType", result.getSignType());
            String sign = createSignature(params, signType);
            result.setSign(sign);
        }
        else if ("APP".equals(tradeType)) {
            result.setPartnerId(mchId);
            result.setPacket("Sign=WXPay");
            Map<String, Object> params = new HashMap<String, Object>(TEN);
            params.put("appid", result.getAppId());
            params.put("partnerid", result.getTimestamp());
            params.put("prepayid", result.getNonceStr());
            params.put("package", result.getPacket());
            params.put("noncestr", result.getSignType());
            params.put("timestamp", result.getSignType());
            String sign = createSignature(params, signType);
            result.setSign(sign);
        }
        else if ("NATIVE".equals(tradeType)) {
            result.setCodeUrl(wxUnifiedOrderResult.getCodeUrl());
        }
        else if ("MWEB".equals(tradeType)) {
            result.setMwebUrl(wxUnifiedOrderResult.getMwebUrl());
        }
        else {
            throw new WxPayException("Unsupported trade type. ");
        }
        return result;
    }

    @Override
    public WxUnifiedOrderResult unifiedOrder(WxUnifiedOrderRequest wxUnifiedOrderRequest) {
        WxUnifiedOrderResult result = new WxUnifiedOrderResult();
        wxUnifiedOrderRequest.setAppId(appId);
        wxUnifiedOrderRequest.setMchId(mchId);
        String signType = wxUnifiedOrderRequest.getSignType();
        if (StringUtils.isBlank(signType)) {
            wxUnifiedOrderRequest.setSignType(signType = MD5);
        }

        Map<String, Object> requestMap = wxUnifiedOrderRequest.toMap();
        String sign = createSignature(requestMap, signType, mchKey);
        requestMap.put("sign", sign);

        String xmlString = XmlUtils.toXmlString(requestMap, new XmlClassAlias("xml", Map.class));
        log.info("Unified order send:{}{}", NEWLINE, xmlString);
        result.setUnifiedOrderRequest(xmlString);

        HttpClient httpClient = HttpUtils.getHttpClient();
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(UNIFIED_ORDER_URL);
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setCharset(DEFAULT_ENCODING_NAME);
        httpRequest.addHeader("Content-Type", "application/xml; charset=UTF-8");
        httpRequest.setBody(xmlString);

        try {
            HttpResponse response = httpClient.execute(httpRequest);
            String bodyAsString = response.getBodyAsString(UTF_8);
            log.info("Unified order receive:{}{}", NEWLINE, bodyAsString);
            result.setUnifiedOrderResponse(bodyAsString);
            Map<String, Object> map = XmlUtils.parseObject(bodyAsString, Map.class, new XmlClassAlias("xml", Map.class));
            result.fromMap(map);
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }

        result.setSignType(signType);
        return result;
    }

    @Override
    public WxPaymentInfoResult paymentInfo(WxUnifiedOrderRequest wxUnifiedOrderRequest) {
        WxUnifiedOrderResult wxUnifiedOrderResult = this.unifiedOrder(wxUnifiedOrderRequest);
        return createPaymentInfo(wxUnifiedOrderResult);
    }

    @Override
    public WxOrderPayNotifyResult parseOrderPayNotify(String notify, String signType) {
        log.info("The content of the order payment notice to be parsed is \"{}\". ", notify);
        WxOrderPayNotifyResult result = new WxOrderPayNotifyResult();
        Map<String, Object> map = XmlUtils.parseObject(notify, Map.class, new XmlClassAlias("xml", Map.class));
        result.fromMap(map);

        // The sign type is not returned in the payment callback of WeiXin.
        String returnedSignType = result.getSignType();
        if (StringUtils.isNotBlank(returnedSignType)) {
            signType = returnedSignType;
        }
        String weiXinSign = result.getSign();
        String calculatedSign = createSignature(map, signType, mchKey);
        boolean signVerification = calculatedSign.equals(weiXinSign);
        log.info("WeiXin sign \"{}\", Calculated sign \"{}\", Result {}. ", weiXinSign, calculatedSign, signVerification);

        result.setCalculatedSign(calculatedSign);
        result.setSignVerification(signVerification);
        return result;
    }

}

package misaka.tencent.wx.miniapp;

import java.io.Serializable;

/**
 * WeiXin mini app js code to session result.
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/union-id.html">WeiXin union id</a>
 * @author Kahle
 */
public class WxCode2SessionResult implements Serializable {
    private Integer errCode;
    private String errMsg;
    private String openId;
    private String unionId;
    private String sessionKey;

    public Integer getErrCode() {

        return errCode;
    }

    public void setErrCode(Integer errCode) {

        this.errCode = errCode;
    }

    public String getErrMsg() {

        return errMsg;
    }

    public void setErrMsg(String errMsg) {

        this.errMsg = errMsg;
    }

    public String getOpenId() {

        return openId;
    }

    public void setOpenId(String openId) {

        this.openId = openId;
    }

    public String getUnionId() {

        return unionId;
    }

    public void setUnionId(String unionId) {

        this.unionId = unionId;
    }

    public String getSessionKey() {

        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {

        this.sessionKey = sessionKey;
    }

}

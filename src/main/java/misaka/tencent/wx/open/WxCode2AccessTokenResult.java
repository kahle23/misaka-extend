package misaka.tencent.wx.open;

import java.io.Serializable;

/**
 * WeiXin open platform access token result.
 * @author Kahle
 */
public class WxCode2AccessTokenResult implements Serializable {
    /**
     * Error code.
     */
    private Integer errCode;
    /**
     * Error message.
     */
    private String errMsg;
    /**
     * 接口调用凭证
     */
    private String accessToken;
    /**
     * 用户刷新 access_token
     */
    private String refreshToken;
    /**
     * access_token 接口调用凭证超时时间，单位（秒）
     */
    private Long expiresIn;
    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;
    /**
     * 授权用户唯一标识
     */
    private String openId;
    /**
     * 当且仅当该移动应用已获得该用户的 userinfo 授权时，才会出现该字段
     */
    private String unionId;

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

}

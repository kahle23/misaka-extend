package misaka.tencent.wx.miniapp;

import java.io.Serializable;

/**
 * WeiXin user information object.
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/user-info/UserInfo.html">User Info</a>
 * @author Kahle
 */
public class WxMiniAppUserInfo implements Serializable {
    private String unionId;
    private String openId;
    private String nickName;
    private String avatarUrl;
    private Integer gender;
    private String country;
    private String province;
    private String city;
    private String language;
    private Watermark watermark;

    public String getUnionId() {

        return unionId;
    }

    public void setUnionId(String unionId) {

        this.unionId = unionId;
    }

    public String getOpenId() {

        return openId;
    }

    public void setOpenId(String openId) {

        this.openId = openId;
    }

    public String getNickName() {

        return nickName;
    }

    public void setNickName(String nickName) {

        this.nickName = nickName;
    }

    public String getAvatarUrl() {

        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {

        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {

        return gender;
    }

    public void setGender(Integer gender) {

        this.gender = gender;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public String getProvince() {

        return province;
    }

    public void setProvince(String province) {

        this.province = province;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public String getLanguage() {

        return language;
    }

    public void setLanguage(String language) {

        this.language = language;
    }

    public Watermark getWatermark() {

        return watermark;
    }

    public void setWatermark(Watermark watermark) {

        this.watermark = watermark;
    }

    /**
     * WeiXin data watermark.
     * @author Kahle
     */
    public static class Watermark implements Serializable {
        private String appid;
        private String timestamp;

        public String getAppid() {

            return appid;
        }

        public void setAppid(String appid) {

            this.appid = appid;
        }

        public String getTimestamp() {

            return timestamp;
        }

        public void setTimestamp(String timestamp) {

            this.timestamp = timestamp;
        }

    }

}

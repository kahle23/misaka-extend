package misaka.tencent.wx.miniapp;

import artoria.exception.UncheckedException;

/**
 * WeiXin mini app exception.
 * @author Kahle
 */
public class WxMiniAppException extends UncheckedException {

    public WxMiniAppException() {

        super();
    }

    public WxMiniAppException(String message) {

        super(message);
    }

    public WxMiniAppException(Throwable cause) {

        super(cause);
    }

    public WxMiniAppException(String message, Throwable cause) {

        super(message, cause);
    }

}

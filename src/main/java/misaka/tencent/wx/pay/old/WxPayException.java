package misaka.tencent.wx.pay.old;

import artoria.exception.UncheckedException;

/**
 * WeiXin pay exception.
 * @author Kahle
 */
@Deprecated
public class WxPayException extends UncheckedException {

    public WxPayException() {

        super();
    }

    public WxPayException(String message) {

        super(message);
    }

    public WxPayException(Throwable cause) {

        super(cause);
    }

    public WxPayException(String message, Throwable cause) {

        super(message, cause);
    }

}

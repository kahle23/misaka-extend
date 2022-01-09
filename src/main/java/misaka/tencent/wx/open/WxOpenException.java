package misaka.tencent.wx.open;

import artoria.exception.UncheckedException;

/**
 * WeiXin open platform exception.
 * @author Kahle
 */
public class WxOpenException extends UncheckedException {

    public WxOpenException() {

        super();
    }

    public WxOpenException(String message) {

        super(message);
    }

    public WxOpenException(Throwable cause) {

        super(cause);
    }

    public WxOpenException(String message, Throwable cause) {

        super(message, cause);
    }

}

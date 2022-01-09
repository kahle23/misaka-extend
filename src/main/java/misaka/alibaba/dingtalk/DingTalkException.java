package misaka.alibaba.dingtalk;

import artoria.exception.UncheckedException;

public class DingTalkException extends UncheckedException {

    public DingTalkException() {

    }

    public DingTalkException(String message) {

        super(message);
    }

    public DingTalkException(Throwable cause) {

        super(cause);
    }

    public DingTalkException(String message, Throwable cause) {

        super(message, cause);
    }

}

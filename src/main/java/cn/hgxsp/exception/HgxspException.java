package cn.hgxsp.exception;

/**
 * DESC：自定义异常类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/24
 * Time : 11:39
 */
public class HgxspException extends RuntimeException {
    public HgxspException() {
        super();
    }

    public HgxspException(String message) {
        super(message);
    }

    public HgxspException(String message, Throwable cause) {
        super(message, cause);
    }

    public HgxspException(Throwable cause) {
        super(cause);
    }

    protected HgxspException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package io.github.tdminhnhat.exception;

public class ClassNameNotFoundException extends RuntimeException {
    public ClassNameNotFoundException(String message) {
        super(message);
    }

    public ClassNameNotFoundException() {
        super();
    }

    public ClassNameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassNameNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ClassNameNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

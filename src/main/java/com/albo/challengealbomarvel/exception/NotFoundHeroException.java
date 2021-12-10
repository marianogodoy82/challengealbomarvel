package com.albo.challengealbomarvel.exception;

public class NotFoundHeroException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 364077127441485500L;

    public NotFoundHeroException() {
        super("Oops!! No conocemos a ese heroe!!");
    }

    public NotFoundHeroException(String message) {
        super(message);
    }

    public NotFoundHeroException(String message, Throwable cause) {
        super(message, cause);
    }
}

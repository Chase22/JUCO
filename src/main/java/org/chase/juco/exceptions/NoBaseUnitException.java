package org.chase.juco.exceptions;

public class NoBaseUnitException extends RuntimeException{
    public NoBaseUnitException() {
        super("No Base unit was defined in this group");
    }

    public NoBaseUnitException(final String message) {
        super(message);
    }

    public NoBaseUnitException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoBaseUnitException(final Throwable cause) {
        super(cause);
    }

    public NoBaseUnitException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

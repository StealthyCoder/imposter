package com.gatehill.imposter.exception;

/**
 * @author Pete Cornish {@literal <outofcoffee@gmail.com>}
 */
public class ResponseException extends RuntimeException {
    public ResponseException(Exception cause) {
        super(cause);
    }

    public ResponseException(String message, Exception cause) {
        super(message, cause);
    }
}

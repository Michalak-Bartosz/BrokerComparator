package org.message.producer.exception;

public class HttpStreamRedirectException extends RuntimeException {
    public HttpStreamRedirectException(String redirectURL) {
        super("Redirect to url: `" + redirectURL + "` exception! Response body is null!");
    }
}

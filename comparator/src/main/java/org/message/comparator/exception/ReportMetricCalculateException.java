package org.message.comparator.exception;

public class ReportMetricCalculateException extends RuntimeException {

    public ReportMetricCalculateException(String className, String message) {
        super("ClassName: [" + className + "] Message: " + message);
    }
}

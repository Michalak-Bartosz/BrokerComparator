package org.message.comparator.exception;

public class NoDataToGenerateReportException extends RuntimeException{
    public NoDataToGenerateReportException() {
        super("No data in database to generate test report!");
    }
}

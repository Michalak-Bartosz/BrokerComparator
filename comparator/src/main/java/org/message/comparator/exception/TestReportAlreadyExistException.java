package org.message.comparator.exception;

import java.util.UUID;

public class TestReportAlreadyExistException extends RuntimeException {

    public TestReportAlreadyExistException(UUID testUUID) {
        super("Test report with UUID: `" + testUUID + "` already exist!");
    }
}

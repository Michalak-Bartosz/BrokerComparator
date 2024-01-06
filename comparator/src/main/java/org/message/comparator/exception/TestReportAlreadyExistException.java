package org.message.comparator.exception;

import java.util.UUID;

public class TestReportAlreadyExistException extends RuntimeException {

    public TestReportAlreadyExistException(UUID testUUID) {
        super(String.format("Test report with test UUID: [%s] already exist!", testUUID));
    }
}

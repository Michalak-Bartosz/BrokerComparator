package org.message.model.util;

import lombok.Getter;

@Getter
public enum ReportStatus {
    NEW("New"), IN_PROGRESS("In progress"), BLOCKED("Blocked"), RESOLVED("Resolved");

    private final String value;

    ReportStatus(String value) {
        this.value = value;
    }
}

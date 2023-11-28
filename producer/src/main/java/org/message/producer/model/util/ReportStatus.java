package org.message.producer.model.util;

import lombok.Getter;
import org.apache.commons.lang3.RandomUtils;

@Getter
public enum ReportStatus {
    NEW("New"), IN_PROGRESS("In progress"), BLOCKED("Blocked"), RESOLVED("Resolved");

    private final String value;

    ReportStatus(String value) {
        this.value = value;
    }

    public static ReportStatus randomReportStatus() {
        ReportStatus[] reportStatuses = values();
        return reportStatuses[RandomUtils.nextInt(0, reportStatuses.length)];
    }
}

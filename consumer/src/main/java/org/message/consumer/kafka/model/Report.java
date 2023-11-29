package org.message.consumer.kafka.model;

import lombok.Data;
import org.message.consumer.kafka.model.util.ReportStatus;

import java.util.List;
import java.util.UUID;

@Data
public class Report {
    private UUID uuid;
    private UUID userUuid;
    private String summary;
    private String description;
    private ReportStatus status;
    private List<Comment> comments;
}

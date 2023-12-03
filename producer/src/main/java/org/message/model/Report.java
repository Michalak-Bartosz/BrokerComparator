package org.message.model;

import lombok.Builder;
import lombok.Data;
import org.message.model.util.ReportStatus;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Report {
    private UUID uuid;
    private UUID userUuid;
    private String summary;
    private String description;
    private ReportStatus status;
    private List<Comment> comments;
}

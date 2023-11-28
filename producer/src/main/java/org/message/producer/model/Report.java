package org.message.producer.model;

import lombok.Data;
import org.message.producer.model.util.ReportStatus;

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

    public Report(String summary, String description, ReportStatus status, List<Comment> comments) {
        this.uuid = UUID.randomUUID();
        this.summary = summary;
        this.description = description;
        this.status = status;
        comments.forEach(comment -> comment.setReportUuid(uuid));
        this.comments = comments;
    }
}

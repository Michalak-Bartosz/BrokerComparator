package org.message.producer.model;

import lombok.Data;
import org.message.producer.model.util.ReportStatus;

import java.util.List;

@Data
public class Report {
    private Long id;
    private String summary;
    private String description;
    private ReportStatus status;
    private List<Comment> comments;

    public Report(String summary, String description, ReportStatus status, List<Comment> comments) {
        this.summary = summary;
        this.description = description;
        this.status = status;
        this.comments = comments;
    }
}

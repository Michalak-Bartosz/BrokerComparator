package org.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.model.util.BrokerType;
import org.message.model.util.ReportStatus;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private UUID uuid;
    private UUID testUUID;
    private UUID userUuid;
    private BrokerType brokerType;
    private String summary;
    private String description;
    private ReportStatus status;
    private List<Comment> comments;
}

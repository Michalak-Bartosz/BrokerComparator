package org.message.consumer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.message.consumer.entity.id.ReportIdKey;
import org.message.model.util.BrokerType;
import org.message.model.util.ReportStatus;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@IdClass(ReportIdKey.class)
public class Report {
    @Id
    private UUID uuid;
    @Id
    private UUID testUUID;
    @Id
    private UUID userUuid;
    @Id
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;

    private String summary;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private ReportStatus status;
    @OneToMany
    private List<Comment> comments;
}

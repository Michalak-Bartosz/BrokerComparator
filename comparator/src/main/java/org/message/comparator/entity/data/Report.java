package org.message.comparator.entity.data;

import jakarta.persistence.*;
import lombok.*;
import org.message.comparator.entity.data.id.ReportIdKey;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.entity.data.util.ReportStatus;

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
    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments;
}

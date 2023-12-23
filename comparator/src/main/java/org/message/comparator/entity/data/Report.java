package org.message.comparator.entity.data;

import jakarta.persistence.*;
import lombok.*;
import org.message.comparator.entity.data.util.ReportStatus;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Report {
    @Id
    private UUID uuid;
    private UUID testUUID;
    private UUID userUuid;
    private String summary;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private ReportStatus status;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments;
}

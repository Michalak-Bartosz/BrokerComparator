package org.message.comparator.entity.data;

import jakarta.persistence.*;
import lombok.*;
import org.message.comparator.entity.data.id.CommentIdKey;
import org.message.comparator.entity.data.util.BrokerType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@IdClass(CommentIdKey.class)
public class Comment {
    @Id
    private UUID uuid;
    @Id
    private UUID reportUuid;
    @Id
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
}

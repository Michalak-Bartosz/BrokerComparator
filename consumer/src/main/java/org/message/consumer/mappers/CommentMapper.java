package org.message.consumer.mappers;

import lombok.experimental.UtilityClass;
import org.message.consumer.entity.Comment;

@UtilityClass
public class CommentMapper {

    public static Comment mapCommentModelToEntity(org.message.model.Comment commentModel) {
        return Comment.builder()
                .uuid(commentModel.getUuid())
                .reportUuid(commentModel.getReportUuid())
                .brokerType(commentModel.getBrokerType())
                .description(commentModel.getDescription())
                .build();
    }
}

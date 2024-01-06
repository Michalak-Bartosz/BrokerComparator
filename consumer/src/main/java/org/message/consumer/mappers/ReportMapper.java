package org.message.consumer.mappers;

import lombok.experimental.UtilityClass;
import org.message.consumer.entity.Comment;
import org.message.consumer.entity.Report;

import java.util.List;

@UtilityClass
public class ReportMapper {

    public static Report mapReportModelToEntity(org.message.model.Report reportModel) {
        List<org.message.model.Comment> commentModelList = reportModel.getComments();
        List<Comment> commentList = commentModelList.stream().map(CommentMapper::mapCommentModelToEntity).toList();
        return Report.builder()
                .uuid(reportModel.getUuid())
                .testUUID(reportModel.getTestUUID())
                .brokerType(reportModel.getBrokerType())
                .userUuid(reportModel.getUserUuid())
                .summary(reportModel.getSummary())
                .description(reportModel.getDescription())
                .status(reportModel.getStatus())
                .comments(commentList)
                .build();
    }
}

package org.message.consumer.service;

import lombok.RequiredArgsConstructor;
import org.message.consumer.entity.Report;
import org.message.consumer.mappers.ReportMapper;
import org.message.consumer.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final CommentService commentService;

    public Report saveReportModel(org.message.model.Report reportModel) {
        Report report = ReportMapper.mapReportModelToEntity(reportModel);
        commentService.saveAllComments(report.getComments());
        reportRepository.save(report);
        reportRepository.flush();
        return report;
    }
}

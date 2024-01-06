package org.message.comparator.util.metric;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.message.comparator.entity.data.DebugInfo;
import org.message.comparator.exception.ReportMetricCalculateException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@UtilityClass
public class ReportTimeMetricUtil {

    public static Duration getMinDeltaTime(List<DebugInfo> debugInfoList) {
        return debugInfoList.stream().map(DebugInfo::getDeltaTimestamp).min(Duration::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportTimeMetricUtil.class.getSimpleName(), "Get min delta time exception!"));
    }

    public static Duration getMaxDeltaTime(List<DebugInfo> debugInfoList) {
        return debugInfoList.stream().map(DebugInfo::getDeltaTimestamp).max(Duration::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportTimeMetricUtil.class.getSimpleName(), "Get max delta time exception!"));
    }

    public static Duration getAverageDeltaTime(List<DebugInfo> debugInfoList) {
        long sumDurationMilliseconds = debugInfoList.stream().map(DebugInfo::getDeltaTimestamp).map(Duration::toMillis).mapToLong(Long::longValue).sum();
        return Duration.ofMillis(sumDurationMilliseconds / debugInfoList.size());
    }

    public static Duration getProducedTime(List<DebugInfo> debugInfoList) {
        Instant startProducedTimestamp = debugInfoList.stream().map(DebugInfo::getProducedTimestamp).min(Instant::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportTimeMetricUtil.class.getSimpleName(), "Get start produced time exception!"));
        Instant endProducedTimestamp = debugInfoList.stream().map(DebugInfo::getProducedTimestamp).max(Instant::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportTimeMetricUtil.class.getSimpleName(), "Get end produced time exception!"));
        return Duration.between(startProducedTimestamp, endProducedTimestamp);
    }

    public static Duration getConsumedTime(List<DebugInfo> debugInfoList) {
        Instant startConsumedTimestamp = debugInfoList.stream().map(DebugInfo::getConsumedTimestamp).min(Instant::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportTimeMetricUtil.class.getSimpleName(), "Get start consumed time exception!"));
        Instant endConsumedTimestamp = debugInfoList.stream().map(DebugInfo::getConsumedTimestamp).max(Instant::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportTimeMetricUtil.class.getSimpleName(), "Get end consumed time exception!"));
        return Duration.between(startConsumedTimestamp, endConsumedTimestamp);
    }

    public static String humanReadableDuration(Duration duration) {
        return DurationFormatUtils.formatDuration(duration.toMillis(), "HH'h' mm'min' ss's' SSS'ms'", false);
    }
}

package org.message.comparator.util.metric;

import lombok.experimental.UtilityClass;
import org.message.comparator.entity.data.metric.DataSizeMetric;
import org.message.comparator.exception.ReportMetricCalculateException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.time.Duration;
import java.util.List;

@UtilityClass
public class ReportDataSizeMetricUtil {

    public static Integer getMaxPayloadSizeInBytes(List<DataSizeMetric> dataSizeMetrics) {
        return dataSizeMetrics.stream()
                .map(DataSizeMetric::getPayloadSizeInBytes)
                .max(Integer::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportDataSizeMetricUtil.class.getSimpleName(), "Get max payload size in bytes exception!"));
    }

    public static Integer getMinPayloadSizeInBytes(List<DataSizeMetric> dataSizeMetrics) {
        return dataSizeMetrics.stream()
                .map(DataSizeMetric::getPayloadSizeInBytes)
                .min(Integer::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportDataSizeMetricUtil.class.getSimpleName(), "Get min payload size in bytes exception!"));
    }

    public static BigDecimal getAveragePayloadSizeInBytes(List<DataSizeMetric> dataSizeMetrics) {
        int sum = dataSizeMetrics.stream().map(DataSizeMetric::getPayloadSizeInBytes).reduce(0, Integer::sum);
        return BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(dataSizeMetrics.size()), 3, RoundingMode.UP);
    }

    public static String getFormattedMaxPayloadSize(Integer maxPayloadSizeInBytes) {
        return humanReadableByteCountBin(maxPayloadSizeInBytes);
    }

    public static String getFormattedMinPayloadSize(Integer minPayloadSizeInBytes) {
        return humanReadableByteCountBin(minPayloadSizeInBytes);
    }

    public static String getFormattedAveragePayloadSize(BigDecimal averagePayloadSizeInBytes) {
        return humanReadableByteCountBin(averagePayloadSizeInBytes.setScale(3, RoundingMode.UP).longValue());
    }

    public static Integer getTotalProducedDataSizeInBytes(List<DataSizeMetric> dataSizeMetrics) {
        return dataSizeMetrics.stream()
                .map(DataSizeMetric::getProducedDataSizeInBytes)
                .max(Integer::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportDataSizeMetricUtil.class.getSimpleName(), "Get total produced data size in bytes exception!"));
    }

    public static Integer getTotalConsumedDataSizeInBytes(List<DataSizeMetric> dataSizeMetrics) {
        return dataSizeMetrics.stream()
                .map(DataSizeMetric::getConsumedDataSizeInBytes)
                .max(Integer::compareTo)
                .orElseThrow(() -> new ReportMetricCalculateException(ReportDataSizeMetricUtil.class.getSimpleName(), "Get total consumed data size in bytes exception!"));
    }

    public static String getFormattedTotalProducedDataSize(Integer totalProducedDataSizeInBytes) {
        return humanReadableByteCountBin(totalProducedDataSizeInBytes);
    }

    public static String getFormattedTotalConsumedDataSize(Integer totalConsumedDataSizeInBytes) {
        return humanReadableByteCountBin(totalConsumedDataSizeInBytes);
    }

    public static BigDecimal getProducedDataSizeInBytesPerSecond(Integer totalProducedDataSizeInBytes, Duration producedTime) {
        BigDecimal time = BigDecimal.valueOf(producedTime.getSeconds()).add(BigDecimal.valueOf(producedTime.getNano(), 9));
        return BigDecimal.valueOf(totalProducedDataSizeInBytes).divide(time, 3, RoundingMode.UP);
    }

    public static BigDecimal getConsumedDataSizeInBytesPerSecond(Integer totalConsumedDataSizeInBytes, Duration consumedTime) {
        BigDecimal time = BigDecimal.valueOf(consumedTime.getSeconds()).add(BigDecimal.valueOf(consumedTime.getNano(), 9));
        return BigDecimal.valueOf(totalConsumedDataSizeInBytes).divide(time, 3, RoundingMode.UP);
    }

    public static String getFormattedProducedDataSizePerSecond(BigDecimal producedDataSizeInBytesPerSecond) {
        return humanReadableByteCountBin(producedDataSizeInBytesPerSecond.setScale(2, RoundingMode.UP).longValue());
    }

    public static String getFormattedConsumedDataSizePerSecond(BigDecimal consumedDataSizeInBytesPerSecond) {
        return humanReadableByteCountBin(consumedDataSizeInBytesPerSecond.setScale(2, RoundingMode.UP).longValue());
    }

    private static String humanReadableByteCountBin(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }
}

package org.message.consumer.util;

import org.message.model.DebugInfo;

import java.util.Comparator;

public class DebugInfoComparator implements Comparator<DebugInfo> {
    @Override
    public int compare(DebugInfo d1, DebugInfo d2) {
        if (d1.getCountOfProducedMessages() > d2.getCountOfProducedMessages() && d1.getTestStatusPercentage() > d2.getTestStatusPercentage()) {
            return 1;
        } else if (d1.getCountOfProducedMessages() < d2.getCountOfProducedMessages() && d1.getTestStatusPercentage() < d2.getTestStatusPercentage()) {
            return -1;
        }
        return 0;
    }
}

package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.entity.data.DebugInfo;
import org.message.comparator.entity.data.util.BrokerType;
import org.message.comparator.repository.data.DebugInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DebugInfoService {

    private final DebugInfoRepository debugInfoRepository;

    public List<DebugInfo> getAllDebugInfoByTestUUID(UUID testUUID) {
        return debugInfoRepository.findAllByTestUUID(testUUID);
    }

    public List<DebugInfo> getAllDebugInfoByTestUUIDAndBrokerType(UUID testUUID, BrokerType brokerType) {
        return debugInfoRepository.findAllByTestUUIDAndBrokerType(testUUID, brokerType);
    }
}

package org.message.comparator.service.data;

import lombok.RequiredArgsConstructor;
import org.message.comparator.entity.data.BrokerInfoData;
import org.message.comparator.repository.data.BrokerInfoDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrokerInfoDataService {

    private final BrokerInfoDataRepository brokerInfoDataRepository;

    public List<BrokerInfoData> saveBrokerInfoDataList(List<BrokerInfoData> brokerInfoDataList) {
        if (CollectionUtils.isEmpty(brokerInfoDataList)) {
            return Collections.emptyList();
        }
        brokerInfoDataRepository.saveAll(brokerInfoDataList);
        brokerInfoDataRepository.flush();
        return brokerInfoDataList;
    }
}

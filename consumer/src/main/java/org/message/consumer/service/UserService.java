package org.message.consumer.service;

import lombok.RequiredArgsConstructor;
import org.message.consumer.entity.Address;
import org.message.consumer.entity.Report;
import org.message.consumer.entity.User;
import org.message.consumer.mappers.UserMapper;
import org.message.consumer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final ReportService reportService;

    public void saveUserModel(org.message.model.User userModel) {
        org.message.model.Address addressModel = userModel.getAddress();
        List<org.message.model.Report> reportModelList = userModel.getReports();
        Address address = addressService.saveAddressModel(addressModel);
        List<Report> reportList = reportModelList.stream().map(reportService::saveReportModel).toList();
        User user = UserMapper.mapUserModelToEntity(userModel, address, reportList);
        userRepository.save(user);
        userRepository.flush();
    }
}

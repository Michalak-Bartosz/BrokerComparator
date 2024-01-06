package org.message.consumer.mappers;

import lombok.experimental.UtilityClass;
import org.message.consumer.entity.Address;
import org.message.consumer.entity.Report;
import org.message.consumer.entity.User;

import java.util.List;

@UtilityClass
public class UserMapper {

    public static User mapUserModelToEntity(org.message.model.User userModel, Address address, List<Report> reportList) {
        return User.builder()
                .uuid(userModel.getUuid())
                .testUUID(userModel.getTestUUID())
                .brokerType(userModel.getBrokerType())
                .idNumber(userModel.getIdNumber())
                .name(userModel.getName())
                .lastName(userModel.getLastName())
                .email(userModel.getEmail())
                .cellPhone(userModel.getCellPhone())
                .address(address)
                .reports(reportList)
                .build();
    }
}

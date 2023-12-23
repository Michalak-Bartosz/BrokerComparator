package org.message.comparator.controller.dashboard;

import lombok.RequiredArgsConstructor;
import org.message.comparator.dto.dashboarduser.ChangePasswordRequestDto;
import org.message.comparator.service.dashboard.DashboardUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.message.comparator.config.ApiConstants.REQUEST_MAPPING_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping(REQUEST_MAPPING_NAME + "/user")
public class DashboardUserController {

    private final DashboardUserService dashboardUserService;

    @PatchMapping
    public ResponseEntity<HttpStatus> changePassword(
            @RequestBody ChangePasswordRequestDto request,
            Principal connectedUser) {
        dashboardUserService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

}

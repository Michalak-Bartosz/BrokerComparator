package org.message.comparator.service.dashboarduser;

import lombok.RequiredArgsConstructor;
import org.message.comparator.dto.dashboarduser.ChangePasswordRequestDto;
import org.message.comparator.entity.DashboardUser;
import org.message.comparator.repository.DashboardUserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class DashboardUserService {

    private final PasswordEncoder passwordEncoder;
    private final DashboardUserRepository dashboardUserRepository;

    public void changePassword(ChangePasswordRequestDto request, Principal connectedUser) {

        var dashboardUser = (DashboardUser) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), dashboardUser.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        dashboardUser.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        dashboardUserRepository.save(dashboardUser);
    }
}

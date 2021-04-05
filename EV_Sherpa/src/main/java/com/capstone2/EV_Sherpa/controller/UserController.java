package com.capstone2.EV_Sherpa.controller;

import com.capstone2.EV_Sherpa.domain.User;
import com.capstone2.EV_Sherpa.service.UserService;
import com.capstone2.EV_Sherpa.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/account/signup")
    public CreateUserResponse signup(@RequestBody @Valid CreateUserRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        Long id = userService.join(user);
        return new CreateUserResponse(id);
    }

    @Data
    @AllArgsConstructor
    static class CreateUserResponse {
        private Long id;
    }

    @Data
    @AllArgsConstructor
    static class CreateUserRequest{

        @NotEmpty
        private String email;

        @NotEmpty
        private String password;
    }

    @Data
    @AllArgsConstructor
    static class EditProfileResponse{
        private Long id;
    }

    @Data
    @AllArgsConstructor
    static class EditProfileRequest{
        @NotEmpty
        private String nickname;
        private
    }

}

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

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/account/signup")             //회원가입
    public CreateUserResponse signup( @Valid CreateUserRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setNickname(request.getNickname());
        Long id = userService.join(user);
        if(id == -1){
            return new CreateUserResponse(false);
        }
        return new CreateUserResponse(true);
    }

    @PostMapping("/account/signin")              //로그인
    public SearchUserResponse signin( @Valid SearchUserRequest request){
        User findUser = userService.authenticate(request.getEmail(), request.getPassword());
        if(findUser == null){
            return new SearchUserResponse(false);
        }
        String accessToken = jwtUtil.createToken(findUser.getId(), findUser.getEmail());
        return new SearchUserResponse(true);
    }

    @PutMapping("/account/edit/password")              //비밀번호 변경
    public ChangePasswordResponse editPassword(@Valid ChangePasswordRequest request){   //@RequestBpdy
        User findUser = userService.authenticate(request.getEmail(), request.getPassword());
        if(findUser == null) {
            return new ChangePasswordResponse(false);
        }
        userService.updatePassword(request.getNewPassword(), findUser.getId());
        return new ChangePasswordResponse(true);
    }

    @PostMapping("/user/profile")                //프로필 가져오는 함수
    public UserProfileResponse searchProfile(@Valid UserProfileRequest request) {
        User findUser = userService.authenticate(request.getEmail(), request.getPassword());
        if(findUser == null){
            return new UserProfileResponse(false, null, null,null,null,null,null);
        }
        return new UserProfileResponse(true, findUser.getEmail(), findUser.getNickname(), findUser.getCarName(),
                findUser.getAge(), findUser.getHomeAddr(), findUser.getWorkplaceAddr());
    }

    @PutMapping("/user/profile/edit")           //프로필 수정하고
    public EditProfileResponse editProfile(@Valid EditProfileRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateUserProfile(findUser.getEmail(), request.getNickname(),
                request.getCarName(), request.getAge(), request.getHomeAddr(),
                request.getWorkplaceAddr());
        return new EditProfileResponse(true, findUser.getNickname(), findUser.getCarName(),
                findUser.getAge(), findUser.getHomeAddr(), findUser.getWorkplaceAddr());
    }


    @Data
    @AllArgsConstructor
    static class CreateUserResponse {
        private Boolean success;
    }

    @Data
    @AllArgsConstructor
    static class CreateUserRequest{
        @NotEmpty
        private String email;
        @NotEmpty
        private String password;
        @NotEmpty
        private String nickname;
    }

    @Data
    @AllArgsConstructor
    static class ChangePasswordResponse{
        private Boolean success;
    }

    @Data
    @AllArgsConstructor
    static class ChangePasswordRequest{
        @NotEmpty
        private String email;
        @NotEmpty
        private String password;
        @NotEmpty
        private String newPassword;
    }

    @Data
    @AllArgsConstructor
    static class UserProfileResponse{
        private Boolean success;
        private String email;
        private String nickname;
        private String carName;
        private Long age;
        private String homeAddr;
        private String workplaceAddr;
    }

    @Data
    @AllArgsConstructor
    static class UserProfileRequest{
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    static class EditProfileResponse{
        private Boolean success;
        private String nickname;
        private String carName;
        private Long age;
        private String homeAddr;
        private String workplaceAddr;
    }

    @Data
    @AllArgsConstructor
    static class EditProfileRequest{
        private String email;
        private String nickname;
        private String carName;
        private Long age;
        private String homeAddr;
        private String workplaceAddr;
    }


    @Data
    static class SearchUserRequest {
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    static class SearchUserResponse{
        private Boolean success;
    }

}

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
    public CreateUserResponse signup(@RequestBody @Valid CreateUserRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        Long id = userService.join(user);
        return new CreateUserResponse(id);
    }

    @GetMapping("/account/signin")              //로그인
    public SearchUserResponse signin(@RequestBody @Valid SearchUserRequest request){
        User findUser = userService.authenticate(request.getEmail(), request.getPassword());
        String accessToken = jwtUtil.createToken(findUser.getId(), findUser.getEmail());
        return new SearchUserResponse(accessToken);
    }

    @PutMapping("/account/edit/password")              //비밀번호 변경
    public CreateUserResponse editPassword(@RequestBody @Valid CreateUserRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updatePassword(findUser.getPassword(), findUser.getId());
        return new CreateUserResponse(findUser.getId());
    }

    @GetMapping("/user/profile")                //프로필 가져오는 함수
    public UserProfileResponse searchProfile(@RequestBody @Valid UserProfileRequest request) {
        User findUser = userService.findOne(request.getId());
        return new UserProfileResponse(findUser.getNickname(), findUser.getCarName(),
                findUser.getAge(), findUser.getHomeAddr(), findUser.getWorkplaceAddr());
    }

    @PutMapping("/user/edit/profile")           //프로필 수정하고
    public UserProfileResponse editProfile(@RequestBody @Valid UserProfileRequest request){
        User findUser = userService.findOne(request.getId());
        userService.updateUserProfile(findUser.getEmail(), findUser.getNickname(),
                findUser.getCarName(), findUser.getAge(), findUser.getHomeAddr(),
                findUser.getWorkplaceAddr());
        return new UserProfileResponse(findUser.getNickname(), findUser.getCarName(),
                findUser.getAge(), findUser.getHomeAddr(), findUser.getWorkplaceAddr());
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
    static class UserProfileResponse{
        private String nickname;
        private String carName;
        private Long age;
        private String homeAddr;
        private String workplaceAddr;
    }

    @Data
    @AllArgsConstructor
    static class UserProfileRequest{
        @NotEmpty
        private Long id;
    }

    @Data
    static class SearchUserRequest {
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    static class SearchUserResponse{
        private String accessToken;
    }

}

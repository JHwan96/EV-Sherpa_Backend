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
            return new SearchUserResponse(false, null,null, null,null,null,null);
        }
        String accessToken = jwtUtil.createToken(findUser.getId(), findUser.getEmail());
        return new SearchUserResponse(true, findUser.getEmail(), findUser.getNickname(), findUser.getHomeAddr(), findUser.getWorkplaceAddr(), findUser.getCarName(), findUser.getAge());
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

    @PutMapping("/account/update")           //전체 프로필
    public EditProfileResponse editProfile(@Valid EditProfileRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateUserProfile(findUser.getEmail(), request.getNickname(),
                request.getCarName(), request.getAge(), request.getHomeAddr(),
                request.getWorkplaceAddr());
        return new EditProfileResponse(true, findUser.getNickname(), findUser.getCarName(),
                findUser.getAge(), findUser.getHomeAddr(), findUser.getWorkplaceAddr());
    }

    @PostMapping("/account/update/home_address")    //집주소 변경1
    public EditHomeAddrResponse editHomeAddr(@Valid EditHomeAddrRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateHomeAddr(findUser.getEmail(), request.getHomeAddr());
        return new EditHomeAddrResponse(true);
    }

    @PutMapping("/account/update/home_address")     ////집주소 변경2
    public EditHomeAddrResponse editHomeAddr2(@Valid EditHomeAddrRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateHomeAddr(findUser.getEmail(), request.getHomeAddr());
        return new EditHomeAddrResponse(true);
    }

    @PostMapping("/account/update/work_address")    //직장주소 변경1
    public EditWorkplaceAddrResponse editWorkplaceAddr(@Valid EditWorkplaceAddrRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateWorkplaceAddr(findUser.getEmail(), request.getWorkplaceAddr());
        return new EditWorkplaceAddrResponse(true);
    }

    @PutMapping("/account/update/work_address")     //직장주소 변경2
    public EditWorkplaceAddrResponse editWorkplaceAddr2(@Valid EditWorkplaceAddrRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateHomeAddr(findUser.getEmail(), request.getWorkplaceAddr());
        return new EditWorkplaceAddrResponse(true);
    }

    @PostMapping("/account/update/nickname")    //닉네임 변경1
    public EditNicknameResponse editNickname(@Valid EditNicknameRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateWorkplaceAddr(findUser.getEmail(), request.getNickname());
        return new EditNicknameResponse(true);
    }

    @PutMapping("/account/update/nickname")     //닉네임 변경2
    public EditNicknameResponse editNickname2(@Valid EditNicknameRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateHomeAddr(findUser.getEmail(), request.getNickname());
        return new EditNicknameResponse(true);
    }

    @PostMapping("/account/update/car_info")    //자기차 정보 변경1
    public EditCarNameResponse editCarName(@Valid EditCarNameRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateWorkplaceAddr(findUser.getEmail(), request.getCarName());
        return new EditCarNameResponse(true);
    }

    @PutMapping("/account/update/car_info")     //자기차 정보 변경2
    public EditCarNameResponse editCarName2(@Valid EditCarNameRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateHomeAddr(findUser.getEmail(), request.getCarName());
        return new EditCarNameResponse(true);
    }

    @PostMapping("/account/update/age")    //자기차 정보 변경1
    public EditAgeResponse editAge(@Valid EditAgeRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateWorkplaceAddr(findUser.getEmail(), request.getAge());
        return new EditAgeResponse(true);
    }

    @PutMapping("/account/update/age")     //자기차 정보 변경2
    public EditAgeResponse editAge2(@Valid EditAgeRequest request){
        User findUser = userService.findOneByEmail(request.getEmail());
        userService.updateHomeAddr(findUser.getEmail(), request.getAge());
        return new EditAgeResponse(true);
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
        private String age;
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
    static class EditProfileResponse{   //전체프로필 수정
        private Boolean success;
        private String nickname;
        private String carName;
        private String age;
        private String homeAddr;
        private String workplaceAddr;
    }

    @Data
    @AllArgsConstructor
    static class EditProfileRequest{    //전체프로필 수정
        private String email;
        private String nickname;
        private String carName;
        private String age;
        private String homeAddr;
        private String workplaceAddr;
    }

    @Data
    @AllArgsConstructor
    static class EditHomeAddrResponse{  //집주소 수정
        private Boolean success;
    }

    @Data
    @AllArgsConstructor
    static class EditHomeAddrRequest{   //집주소 수정
        private String email;
        private String homeAddr;
    }

    @Data
    @AllArgsConstructor
    static class EditWorkplaceAddrResponse{     //직장주소 수정
        private Boolean success;
    }

    @Data
    @AllArgsConstructor
    static class EditWorkplaceAddrRequest{      //직장주소 수정
        private String email;
        private String workplaceAddr;
    }

    @Data
    @AllArgsConstructor
    static class EditNicknameResponse{     //닉네임 수정
        private Boolean success;
    }

    @Data
    @AllArgsConstructor
    static class EditNicknameRequest{      //닉네임 수정
        private String email;
        private String nickname;
    }

    @Data
    @AllArgsConstructor
    static class EditCarNameResponse{     //자기차 정보 수정
        private Boolean success;
    }

    @Data
    @AllArgsConstructor
    static class EditCarNameRequest{      //자기차 정보 수정
        private String email;
        private String carName;
    }

    @Data
    @AllArgsConstructor
    static class EditAgeResponse{     //나이 수정
        private Boolean success;
    }

    @Data
    @AllArgsConstructor
    static class EditAgeRequest{      //나이 수정
        private String email;
        private String age;
    }

    @Data
    static class SearchUserRequest {        //로그인
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    static class SearchUserResponse{        //로그인
        private Boolean success;
        private String email;
        private String nickname;
        private String homeAddr;
        private String workplaceAddr;
        private String carName;
        private String age;
    }

}

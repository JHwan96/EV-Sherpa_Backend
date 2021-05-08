package com.capstone2.EV_Sherpa.service;

import com.capstone2.EV_Sherpa.domain.User;
import com.capstone2.EV_Sherpa.exception.PasswordWrongException;
import com.capstone2.EV_Sherpa.exception.UserEmptyException;
import com.capstone2.EV_Sherpa.exception.UserExistException;
import com.capstone2.EV_Sherpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(User user){
        int check = 0;
        check = validateDuplicateUser(user);
        if(check == -1){
            return (long)-1;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public User authenticate(String email, String password){
        List<User> findUsers = userRepository.findByEmail(email);
        if(findUsers.isEmpty()){
            return null;
        }
        if(!passwordEncoder.matches(password, findUsers.get(0).getPassword())){
            return null;
        }
        return findUsers.get(0);
    }

    private int validateDuplicateUser(User user){
        List<User> findUsers = userRepository.findByEmail(user.getEmail());
        if(!findUsers.isEmpty()){
            return -1;
        }
        return 0;
    }

    public User findOneByEmail (String email){
        List<User> findUsers = userRepository.findByEmail(email);
        return findUsers.get(0);
    }

    public User findOne(Long userId){return userRepository.findOne(userId);}

    @Transactional
    public void updatePassword(String password, Long id){
        User user = userRepository.findOne(id);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Transactional
    public void updateUserProfile(String email, String nickname, String carName, Long age, String homeAddr, String workplaceAddr){
        User user = userRepository.findOneByEmail(email);       //프로필 수정용
        user.setNickname(nickname);
        user.setCarName(carName);
        user.setAge(age);
        user.setHomeAddr(homeAddr);
        user.setWorkplaceAddr(workplaceAddr);
        userRepository.save(user);
    }

}

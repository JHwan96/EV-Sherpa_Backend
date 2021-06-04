package com.capstone2.EV_Sherpa.service;

import com.capstone2.EV_Sherpa.domain.Preference;
import com.capstone2.EV_Sherpa.domain.User;
import com.capstone2.EV_Sherpa.domain.UserPreference;
import com.capstone2.EV_Sherpa.exception.PasswordWrongException;
import com.capstone2.EV_Sherpa.exception.UserEmptyException;
import com.capstone2.EV_Sherpa.exception.UserExistException;
import com.capstone2.EV_Sherpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.prefs.Preferences;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long checkDuplicate(User user){
        int check = 0;
        check = validateDuplicateUser(user);
        if(check == -1){
            return (long)-1;
        }
        else{
            return (long)1;
        }
    }

    @Transactional
    public Long join(User user){

        int check = 0;
        check = validateDuplicateUser(user);
        if(check == -1){
            return (long)-1;
        }
        Preference preference = new Preference();
        UserPreference userPreference = new UserPreference();
        userPreference.setPreference_id(preference);
        userPreference.setUser_id(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        userRepository.save(preference);
        userRepository.save(userPreference);
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
        log.info("email");
        log.info(email);
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
    public void updateUserProfile(String email, String nickname, String carName, String age, String homeAddr, String workplaceAddr){
        User user = userRepository.findOneByEmail(email);       //프로필 수정용
        user.setNickname(nickname);
        user.setCarName(carName);
        user.setAge(age);
        user.setHomeAddr(homeAddr);
        user.setWorkplaceAddr(workplaceAddr);
        userRepository.save(user);
    }

    @Transactional
    public void updateHomeAddr(String email, String homeAddr){
        User user = userRepository.findOneByEmail(email);       //집주소 수정용
        user.setHomeAddr(homeAddr);
        userRepository.save(user);
    }

    @Transactional
    public void updateWorkplaceAddr(String email, String workplaceAddr){
        User user = userRepository.findOneByEmail(email);       //직장주소 수정용
        user.setWorkplaceAddr(workplaceAddr);
        userRepository.save(user);
    }

    @Transactional
    public void updateNickname(String email, String nickname){
        User user = userRepository.findOneByEmail(email);       //닉네임 수정용
        user.setNickname(nickname);
        userRepository.save(user);
    }

    @Transactional
    public void updateCarName(String email, String carName){
        User user = userRepository.findOneByEmail(email);       //자기차 수정용
        user.setCarName(carName);
        userRepository.save(user);
    }

    @Transactional
    public void updateAge(String email, String age){
        User user = userRepository.findOneByEmail(email);       //나이 수정용
        user.setAge(age);
        userRepository.save(user);
    }

    @Transactional
    public void updatePreference(String email, Long distance, Long chargerType, String batterytype,
                                 Boolean FastCharge, Long remainingCharger, String businessName){
        User user = userRepository.findOneByEmail(email);
        UserPreference userPreference= userRepository.findOneByUserId(user.getId());
        Preference preference = userRepository.findOneByPreferenceId(userPreference.getPreference_id().getId());
        preference.setDistance(distance);
        preference.setChargerType(chargerType);
        preference.setBatteryType(batterytype);
        preference.setFastCharge(FastCharge);
        preference.setRemainingCharger(remainingCharger);
        preference.setBusinessName(businessName);
        userRepository.save(preference);
    }

}

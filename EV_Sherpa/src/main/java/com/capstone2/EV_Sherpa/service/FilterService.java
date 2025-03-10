package com.capstone2.EV_Sherpa.service;

import com.capstone2.EV_Sherpa.domain.entity.ApiInformation;
import com.capstone2.EV_Sherpa.domain.entity.Preference;
import com.capstone2.EV_Sherpa.domain.entity.User;
import com.capstone2.EV_Sherpa.domain.entity.UserPreference;
import com.capstone2.EV_Sherpa.domain.repository.FilterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FilterService {
    private final FilterRepository filterRepository;

    @Transactional
    public String prefFromBusinessName(String email, Float latitude, Float longitude){
        String result = "";
        int length;
        User user = filterRepository.findOneByEmail(email);
        UserPreference userPreference = filterRepository.findOneByUserId(user.getId());
        Preference preference = filterRepository.findOneByPreferenceId(userPreference.getPreference_id().getId());
        List<ApiInformation> info = filterRepository.findByBusinessName(latitude,longitude, preference.getBusinessName());
        length = info.size();
        for(int i = 0; i < length; i++) {
            if(i == length -1) {
                result += info.get(i).getStatId();
            }
            else{
                result += info.get(i).getStatId() + ",";
            }
        }
        return result;
    }

    @Transactional
    public String prefFromDistance(String email, Float latitude, Float longitude){
        String result = "";
        int length;
        User user = filterRepository.findOneByEmail(email);
        UserPreference userPreference = filterRepository.findOneByUserId(user.getId());
        Preference preference = filterRepository.findOneByPreferenceId(userPreference.getPreference_id().getId());
        List<ApiInformation> info = filterRepository.findByDistance(latitude,longitude,(long)3);
        length = info.size();
        for(int i = 0; i < length; i++) {
            if(i == info.size() -1) {
                result += info.get(i).getStatId();
            }
            else{
                result += info.get(i).getStatId() + ",";
            }
        }
        return result;
    }

    @Transactional
    public String prefFromFastCharge(String email, Float latitude, Float longitude){
        String result = "";
        int length;
        User user = filterRepository.findOneByEmail(email);
        UserPreference userPreference = filterRepository.findOneByUserId(user.getId());
        Preference preference = filterRepository.findOneByPreferenceId(userPreference.getPreference_id().getId());
        List<ApiInformation> info = filterRepository.findByFastCharge(latitude,longitude,preference.getFastCharge());
        length = info.size();
        for(int i = 0; i < length; i++) {
            if(i == length -1) {
                result += info.get(i).getStatId();
            }
            else{
                result += info.get(i).getStatId() + ",";
            }

        }
        return result;
    }

    @Transactional
    public String prefFromChargerType(String email, Float latitude, Float longitude){
        String result = "";
        int length;
        User user = filterRepository.findOneByEmail(email);
        UserPreference userPreference = filterRepository.findOneByUserId(user.getId());
        Preference preference = filterRepository.findOneByPreferenceId(userPreference.getPreference_id().getId());
        List<ApiInformation> info = filterRepository.findByChargerType(latitude,longitude,preference.getChargerType());
        length = info.size();
        for(int i = 0; i < length; i++) {
            if(i == length -1) {
                result += info.get(i).getStatId();
            }
            else{
                result += info.get(i).getStatId() + ",";
            }

        }
        return result;
    }

}

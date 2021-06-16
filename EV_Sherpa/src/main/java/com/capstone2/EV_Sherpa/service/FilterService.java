package com.capstone2.EV_Sherpa.service;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import com.capstone2.EV_Sherpa.domain.Preference;
import com.capstone2.EV_Sherpa.domain.User;
import com.capstone2.EV_Sherpa.domain.UserPreference;
import com.capstone2.EV_Sherpa.repository.FilterRepository;
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
        User user = filterRepository.findOneByEmail(email);
        UserPreference userPreference = filterRepository.findOneByUserId(user.getId());
        Preference preference = filterRepository.findOneByPreferenceId(userPreference.getPreference_id().getId());
        List<ApiInformation> info = filterRepository.findByBusinessName(latitude,longitude, preference.getBusinessName());
        for(int i = 0; i < info.size(); i++) {
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
    public String prefFromDistance(String email, Float latitude, Float longitude){
        String result = "";
        User user = filterRepository.findOneByEmail(email);
        UserPreference userPreference = filterRepository.findOneByUserId(user.getId());
        Preference preference = filterRepository.findOneByPreferenceId(userPreference.getPreference_id().getId());
        List<ApiInformation> info = filterRepository.findByDistance(latitude,longitude,(long)3);
        for(int i = 0; i < info.size(); i++) {
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
        User user = filterRepository.findOneByEmail(email);
        UserPreference userPreference = filterRepository.findOneByUserId(user.getId());
        Preference preference = filterRepository.findOneByPreferenceId(userPreference.getPreference_id().getId());
        List<ApiInformation> info = filterRepository.findByFastCharge(latitude,longitude,preference.getFastCharge());
        for(int i = 0; i < info.size(); i++) {
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
    public String prefFromChargerType(String email, Float latitude, Float longitude){
        String result = "";
        User user = filterRepository.findOneByEmail(email);
        UserPreference userPreference = filterRepository.findOneByUserId(user.getId());
        Preference preference = filterRepository.findOneByPreferenceId(userPreference.getPreference_id().getId());
        List<ApiInformation> info = filterRepository.findByChargerType(latitude,longitude,preference.getChargerType());
        for(int i = 0; i < info.size(); i++) {
            if(i == info.size() -1) {
                result += info.get(i).getStatId();
            }
            else{
                result += info.get(i).getStatId() + ",";
            }

        }
        return result;
    }

}

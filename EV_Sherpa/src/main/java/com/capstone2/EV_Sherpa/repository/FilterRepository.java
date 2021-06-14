package com.capstone2.EV_Sherpa.repository;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import com.capstone2.EV_Sherpa.domain.Preference;
import com.capstone2.EV_Sherpa.domain.User;
import com.capstone2.EV_Sherpa.domain.UserPreference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FilterRepository {
    private final EntityManager em;

    public ApiInformation findOneByStatId(String statId){       //StatId로 가져오기
        return em.createQuery("select u from ApiInformation as u where u.statId= : statId", ApiInformation.class)
                .setParameter("statId", statId)
                .getSingleResult();
    }

    public List<ApiInformation> findByStatId(String statId){    //StatId로 가져오기
        return em.createQuery("select u from ApiInformation as u where u.statId= : statId", ApiInformation.class)
                .setParameter("statId", statId)
                .getResultList();
    }

    public User findOneByEmail(String email){       //Email로 명단
        return em.createQuery("select u from User as u where u.email= :email", User.class)
                .setParameter("email",email)
                .getSingleResult();
    }

    public UserPreference findOneByUserId(Long user_id){    //userId로 user의 preference
        return em.createQuery("select u from UserPreference as u join fetch u.user_id a where a.id = :user_id",UserPreference.class)
                .setParameter("user_id", user_id)
                .getSingleResult();
    }

    public Preference findOneByPreferenceId(Long preference_id){    //preferenceId로 preference의 명단
        return em.createQuery("select u from Preference as u where u.id= :preference_id",Preference.class)
                .setParameter("preference_id", preference_id)
                .getSingleResult();
    }

    public List<ApiInformation> findByChargerType(Float latitude, Float longitude, String chargerType){
        List<ApiInformation> result = new ArrayList<>();
        int count = 0;
        int length;
        List<ApiInformation> tempResult =  em.createQuery("SELECT u FROM ApiInformation as u where " +
                "(6371*acos(cos(radians(:latitude))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(:longitude))+sin(radians(:latitude))*sin(radians(u.lat)))) <= 10 order by " +
                "(6371*acos(cos(radians(37.4685225))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(:longitude))+sin(radians(:latitude))*sin(radians(u.lat)))) asc",ApiInformation.class)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .getResultList();
        length = tempResult.size();

        for(int i = 0; i < length; i++) {
            if(count == 10){
                break;
            }
            if(result.isEmpty()){
                if(tempResult.get(i).getChgerType().equals(chargerType)) {
                    result.add(tempResult.get(i));
                    count++;
                }
                else continue;
            }
            else {
                if(result.get(count-1).getStatId().equals(tempResult.get(i).getStatId())) continue;
                else{
                    if(tempResult.get(i).getChgerType().equals(chargerType)) {
                        result.add(tempResult.get(i));
                        count++;
                    }
                    else continue;
                }
            }
        }
        return result;
    }

    public List<ApiInformation> findByBusinessName(Float latitude, Float longitude, String businessName){
        List<ApiInformation> result = new ArrayList<>();
        int count = 0;
        int length;
        List<ApiInformation> tempResult =  em.createQuery("SELECT u FROM ApiInformation as u where " +
                "(6371*acos(cos(radians(:latitude))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(:longitude))+sin(radians(:latitude))*sin(radians(u.lat)))) <= 10 order by " +
                "(6371*acos(cos(radians(37.4685225))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(:longitude))+sin(radians(:latitude))*sin(radians(u.lat)))) asc",ApiInformation.class)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .getResultList();
        length = tempResult.size();

        for(int i = 0; i < length; i++) {
            if(count == 10){
                break;
            }
            if(result.isEmpty()){
                if(tempResult.get(i).getBusiNm().equals(businessName)) {
                    result.add(tempResult.get(i));
                    count++;
                }
                else continue;
            }
            else {
                if(result.get(count-1).getStatId().equals(tempResult.get(i).getStatId())) continue;
                else{
                    if(tempResult.get(i).getBusiNm().equals(businessName)) {
                        result.add(tempResult.get(i));
                        count++;
                    }
                    else continue;
                }
            }
        }
        return result;
    }

    public List<ApiInformation> findByDistance(Float latitude, Float longitude, Long distance){
        double Ddistance = (double)distance;
        List<ApiInformation> result = new ArrayList<>();
        int count = 0;
        int length = 0;

        List<ApiInformation> tempResult =  em.createQuery("SELECT u FROM ApiInformation as u where " +
                "(6371*acos(cos(radians(:latitude))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(:longitude))+sin(radians(:latitude))*sin(radians(u.lat)))) <= :distance order by " +
                "(6371*acos(cos(radians(37.4685225))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(:longitude))+sin(radians(:latitude))*sin(radians(u.lat)))) asc",ApiInformation.class)
                .setParameter("distance", Ddistance)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .getResultList();
        length = tempResult.size();

        for(int i = 0; i < length; i++) {
            if(count == 10){
                break;
            }
            if(i == 0){
                result.add(tempResult.get(0));
                count++;
            }
            else {
                if(result.get(count-1).getStatId().equals(tempResult.get(i).getStatId())) continue;
                else{
                    result.add(tempResult.get(i));
                    count++;
                }
            }
        }

        return result;


    }


    public List<ApiInformation> findByFastCharge(Float latitude, Float longitude, Boolean fastCharge){
        List<ApiInformation> result = new ArrayList<>();
        int count = 0;
        int length;
        String a = "02";
        String b = "07";

        List<ApiInformation> tempResult =  em.createQuery("SELECT u FROM ApiInformation as u where " +
                "(6371*acos(cos(radians(:latitude))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(:longitude))+sin(radians(:latitude))*sin(radians(u.lat)))) <= 10 order by " +
                "(6371*acos(cos(radians(37.4685225))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(:longitude))+sin(radians(:latitude))*sin(radians(u.lat)))) asc",ApiInformation.class)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .getResultList();
        length = tempResult.size();

        if(fastCharge == false) {
            for(int i = 0; i < length; i++) {
                if(count == 10){
                    break;
                }
                if(result.isEmpty()){
                    if(tempResult.get(i).getChgerType().equals(a) || tempResult.get(i).getChgerType().equals(b)) {
                        result.add(tempResult.get(i));
                        count++;
                    }
                    else continue;
                }
                else {
                    if(result.get(count-1).getStatId().equals(tempResult.get(i).getStatId())) continue;
                    else{
                        if(tempResult.get(i).getChgerType().equals(a) || tempResult.get(i).getChgerType().equals(b)) {
                            result.add(tempResult.get(i));
                            count++;
                        }
                        else continue;
                    }
                }
            }

            return result;
        }
        else {
            for(int i = 0; i < length; i++) {
                if(count == 10){
                    break;
                }
                if(result.isEmpty()){
                    if(!tempResult.get(i).getChgerType().equals(a) && !tempResult.get(i).getChgerType().equals(b)) {
                        result.add(tempResult.get(i));
                        count++;
                    }
                    else continue;
                }
                else {
                    if(result.get(count-1).getStatId().equals(tempResult.get(i).getStatId())) continue;
                    else{
                        if(!tempResult.get(i).getChgerType().equals(a) && !tempResult.get(i).getChgerType().equals(b)) {
                            result.add(tempResult.get(i));
                            count++;
                        }
                        else continue;
                    }
                }
            }
            return result;
        }
    }


    public List<ApiInformation> findByRemainingCharger(Float latitude, Float longitude, Long remainingCharger){
        List<ApiInformation> result = new ArrayList<>();
        int count = 0;
        int chargerCount = 0;
        int length;
        List<ApiInformation> tempResult =  em.createQuery("SELECT u FROM ApiInformation as u where " +
                "(6371*acos(cos(radians(:latitude))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(:longitude))+sin(radians(:latitude))*sin(radians(u.lat)))) <= 10 order by " +
                "(6371*acos(cos(radians(37.4685225))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(:longitude))+sin(radians(:latitude))*sin(radians(u.lat)))) asc",ApiInformation.class)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .getResultList();
        length = tempResult.size();

        for(int i = 0; i < length; i++) {
            if(count == 10){
                break;
            }

            if(count != 0 && result.get(count-1).equals(tempResult.get(i).getStatId())){
                continue;           //이미 있는 경우
            }

            if(remainingCharger == 0 && i == 0){    // 선호 0대 이상
                result.add(tempResult.get(i));
                count++;
                continue;
            }
            else if(i == 0){
                if(tempResult.get(i).getStat() == 2)
                    chargerCount++;
            }


            if(i != 0 && tempResult.get(i-1).getStatId().equals(tempResult.get(i))){
                if(chargerCount <= remainingCharger){
                    result.add(tempResult.get(i));
                    count++;
                    continue;
                }
                else if(tempResult.get(i).getStat() == 2){
                    chargerCount++;
                }

            }
            else if(i != 0 && !tempResult.get(i-1).getStatId().equals(tempResult.get(i))){
                chargerCount = 0;
                continue;
            }
        }
        return result;
    }
}

package com.capstone2.EV_Sherpa.repository;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import com.capstone2.EV_Sherpa.domain.Preference;
import com.capstone2.EV_Sherpa.domain.User;
import com.capstone2.EV_Sherpa.domain.UserPreference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<ApiInformation> findByChargerType(String chargerType){
        return em.createQuery("select u from ApiInformation as u where u.chgerType= :chargerType", ApiInformation.class)
                .setParameter("chargerType",chargerType)
                .getResultList();
    }

    public List<ApiInformation> findByBusinessName(String businessName){
        log.info(businessName);
        return em.createQuery("select u from ApiInformation as u where u.busiNm= :businessName",ApiInformation.class)
                .setParameter("businessName", businessName)
                .getResultList();
    }
    public List<ApiInformation> findByDistance(Float latitute, Float longitute, Long distance){
        return em.createQuery("SELECT u.stat_id FROM ApiInformation as u where " +
                "(6371*acos(cos(radians(37.4685225))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(126.8943311))+sin(radians(37.4685225))*sin(radians(u.lat)))) <= :distance " +
                "order by (6371*acos(cos(radians(37.4685225))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(126.8943311))+sin(radians(37.4685225))*sin(radians(u.lat)))) limit 0,5",ApiInformation.class)
                .setParameter("distance", distance)
                .setParameter("latitute", latitute)
                .setParameter("longitute", longitute)
                .getResultList();
    }

    public List<ApiInformation> findByFastCharge(Boolean fastCharge){
        String a = "02";
        String b = "07";
        if(fastCharge == false) {
            return em.createQuery("select u from ApiInformation as u where " +
                    "u.chgerType= :chargeType1 or u.chgerType= :chargeType2", ApiInformation.class)
                    .setParameter("chargeType1", a)
                    .setParameter("chargeType2", b)
                    .getResultList();
        }
        else {
            return em.createQuery("select u from ApiInformation as u where " +
                    "u.chgerType!= :chargeType1 and u.chgerType!= :chargeType2", ApiInformation.class)
                    .setParameter("chargeType1", a)
                    .setParameter("chargeType2", b)
                    .getResultList();
        }
    }


}

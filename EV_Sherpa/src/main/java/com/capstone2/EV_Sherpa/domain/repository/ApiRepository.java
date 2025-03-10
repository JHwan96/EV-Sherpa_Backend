package com.capstone2.EV_Sherpa.domain.repository;

import com.capstone2.EV_Sherpa.domain.entity.ApiInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ApiRepository {
    private final EntityManager em;

    public void save(ApiInformation apiInformation){em.persist(apiInformation);}

    public ApiInformation findOne(String statId){
        return em.createQuery("select u from ApiInformation as u where u.statId= : statId", ApiInformation.class)
                .setParameter("statId", statId)
                .getSingleResult();
    }

    public List<ApiInformation> findByStatId(String statId){
        return em.createQuery("select u from ApiInformation as u where u.statId= : statId", ApiInformation.class)
                .setParameter("statId", statId)
                .getResultList();
    }

    public List<ApiInformation> findNearCharger(Float latitude, Float longitude){
        List<ApiInformation> result = new ArrayList<>();
        int count = 0;
        int length;
        List<ApiInformation> tempResult =  em.createQuery("SELECT u FROM ApiInformation as u where " +
                "(6371*acos(cos(radians(:latitude))*cos(radians(u.lat))*cos(radians(u.lng)" +
                "-radians(:longitude))+sin(radians(:latitude))*sin(radians(u.lat)))) <= 1 order by " +
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
            if(i == 0){
                result.add(tempResult.get(0));
                count++;
                continue;
            }
            else {
                if(result.get(count-1).getStatId().equals(tempResult.get(i).getStatId())){
                    result.add(tempResult.get(i));
                    continue;
                }
                else if(!result.get(count-1).getStatId().equals(tempResult.get(i).getStatId())){
                    result.add(tempResult.get(i));
                    count++;
                }
            }
        }
        return result;
    }

}

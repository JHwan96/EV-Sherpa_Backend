package com.capstone2.EV_Sherpa.repository;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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
}

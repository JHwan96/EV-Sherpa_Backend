package com.capstone2.EV_Sherpa.repository;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ApiRepository {
    private final EntityManager em;

    public void save(ApiInformation apiInformation){em.persist(apiInformation);}

}

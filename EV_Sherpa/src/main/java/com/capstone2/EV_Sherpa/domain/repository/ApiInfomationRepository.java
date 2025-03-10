package com.capstone2.EV_Sherpa.domain.repository;

import com.capstone2.EV_Sherpa.domain.entity.ApiInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiInfomationRepository extends JpaRepository<ApiInformation, Long> {
}

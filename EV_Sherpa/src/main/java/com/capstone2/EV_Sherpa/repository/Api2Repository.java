package com.capstone2.EV_Sherpa.repository;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Api2Repository extends JpaRepository<ApiInformation, Long> {
}

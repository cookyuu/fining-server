package com.hklim.finingserver.domain.indicators.repository;

import com.hklim.finingserver.domain.indicators.entity.IndicatorIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface IndicatorIndexRepository extends JpaRepository<IndicatorIndex, Long> {
    boolean existsByAsOfDate(LocalDate now);
}

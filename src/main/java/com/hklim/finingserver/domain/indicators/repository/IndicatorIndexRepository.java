package com.hklim.finingserver.domain.indicators.repository;

import com.hklim.finingserver.domain.indicators.entity.IndicatorIndex;
import com.hklim.finingserver.domain.indicators.entity.IndicatorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IndicatorIndexRepository extends JpaRepository<IndicatorIndex, Long> {
    boolean existsByAsOfDateAndIndicatorType(LocalDate now, IndicatorType indicatorType);

    List<IndicatorIndex> findAllByAsOfDate(LocalDate now);
}

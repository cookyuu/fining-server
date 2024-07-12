package com.hklim.finingserver.domain.indicators.repository;

import com.hklim.finingserver.domain.indicators.entity.Indicators;
import com.hklim.finingserver.domain.indicators.entity.IndicatorsIndex;
import com.hklim.finingserver.domain.indicators.entity.IndicatorsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IndicatorIndexRepository extends JpaRepository<IndicatorsIndex, Long> {
    boolean existsByAsOfDateAndIndicatorType(LocalDate now, IndicatorsType indicatorsType);

    List<IndicatorsIndex> findAllByAsOfDate(LocalDate now);

    List<IndicatorsIndex> findAllByIndicators(Indicators indicators);
}

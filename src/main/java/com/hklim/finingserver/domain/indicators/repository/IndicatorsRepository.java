package com.hklim.finingserver.domain.indicators.repository;

import com.hklim.finingserver.domain.indicators.entity.Indicators;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndicatorsRepository extends JpaRepository<Indicators, Long> {
    Indicators findBySymbol(String symbol);
}

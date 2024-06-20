package com.hklim.finingserver.domain.indicators.repository;

import com.hklim.finingserver.domain.indicators.entity.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndicatorRepository extends JpaRepository<Indicator, Long> {
    Indicator findBySymbol(String symbol);
}

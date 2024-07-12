package com.hklim.finingserver.domain.indicators.repository;

import com.hklim.finingserver.domain.indicators.entity.Indicators;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndicatorRepository extends JpaRepository<Indicators, Long> {
    Indicators findBySymbol(String symbol);
}

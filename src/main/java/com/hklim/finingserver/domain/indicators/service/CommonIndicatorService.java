package com.hklim.finingserver.domain.indicators.service;

import com.hklim.finingserver.domain.indicators.repository.IndicatorIndexRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public abstract class CommonIndicatorService{
    private final IndicatorIndexRepository indicatorIndexRepository;
    public boolean isExistIndicatorIndexToday() {
        log.info("[INDICATOR-CRAWLING] Check Today Indicator Data insert Already.  Date : {}", LocalDate.now());
        return indicatorIndexRepository.existsByAsOfDate(LocalDate.now());
    }
}

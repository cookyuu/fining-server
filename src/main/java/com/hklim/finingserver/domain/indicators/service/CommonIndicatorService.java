package com.hklim.finingserver.domain.indicators.service;

import com.hklim.finingserver.domain.indicators.entity.Indicator;
import com.hklim.finingserver.domain.indicators.entity.IndicatorType;
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

    /*
    * 날짜, 지수 종류 둘다 확인 필요
    * */
    public boolean isExistIndicatorIndexToday(IndicatorType indicatorType) {
        log.info("[INDICATOR-CRAWLING] Check if today's Indicator Data has already been inserted.  Date : {}, IndicatorType : {}", LocalDate.now(), indicatorType);
        return indicatorIndexRepository.existsByAsOfDateAndIndicatorType(LocalDate.now(), indicatorType);
    }
}

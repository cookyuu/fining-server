package com.hklim.finingserver.domain.indicators.service;

import com.hklim.finingserver.domain.indicators.entity.Indicator;
import com.hklim.finingserver.domain.indicators.entity.IndicatorIndex;
import com.hklim.finingserver.domain.indicators.entity.IndicatorType;
import com.hklim.finingserver.domain.indicators.repository.IndicatorIndexRepository;
import com.hklim.finingserver.domain.ui.dto.MainUiDataResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<MainUiDataResponseDto.IndicatorData> getIndicatorOfToday() {
        List<IndicatorIndex> indicatorIndices = indicatorIndexRepository.findAllByAsOfDate(LocalDate.now());
        return convertIndicatorToMainIndicatorList(indicatorIndices);
    }

    private List<MainUiDataResponseDto.IndicatorData> convertIndicatorToMainIndicatorList(List<IndicatorIndex> indicatorIndices) {
        List<MainUiDataResponseDto.IndicatorData> indicatorDataList = new ArrayList<>();
        indicatorIndices.forEach(indicatorIndex -> {
            indicatorDataList.add(new MainUiDataResponseDto.IndicatorData(indicatorIndex.getIndicator().getName(), indicatorIndex.getIndicator().getSymbol()
            ,indicatorIndex.getIndicatorType().getValue(), indicatorIndex.getNetChange(), indicatorIndex.getPercentChange(), indicatorIndex.getPrice()));
        });
        return indicatorDataList;
    }
 }

package com.hklim.finingserver.domain.indicators.service;

import com.hklim.finingserver.domain.indicators.entity.Indicators;
import com.hklim.finingserver.domain.indicators.entity.IndicatorsIndex;
import com.hklim.finingserver.domain.indicators.entity.IndicatorsType;
import com.hklim.finingserver.domain.indicators.repository.IndicatorsIndexRepository;
import com.hklim.finingserver.domain.indicators.repository.IndicatorsRepository;
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
public abstract class CommonIndicatorsService {
    private final IndicatorsIndexRepository indicatorsIndexRepository;
    private final IndicatorsRepository indicatorsRepository;

    /*
    * 날짜, 지수 종류 둘다 확인 필요
    * */
    public boolean isExistIndicatorIndexToday(IndicatorsType indicatorsType) {
        log.info("[INDICATOR-CRAWLING] Check if today's Indicator Data has already been inserted.  Date : {}, IndicatorType : {}", LocalDate.now(), indicatorsType);
        return indicatorsIndexRepository.existsByAsOfDateAndIndicatorsType(LocalDate.now(), indicatorsType);
    }

    public List<MainUiDataResponseDto.IndicatorData> getIndicatorOfToday() {
        List<IndicatorsIndex> indicatorIndices = indicatorsIndexRepository.findAllByAsOfDate(LocalDate.now());
        return convertIndicatorToMainIndicatorList(indicatorIndices);
    }

    private List<MainUiDataResponseDto.IndicatorData> convertIndicatorToMainIndicatorList(List<IndicatorsIndex> indicatorIndices) {
        List<MainUiDataResponseDto.IndicatorData> indicatorDataList = new ArrayList<>();
        indicatorIndices.forEach(indicatorIndex -> {
            indicatorDataList.add(new MainUiDataResponseDto.IndicatorData(indicatorIndex.getIndicators().getName(), indicatorIndex.getIndicators().getSymbol()
            ,indicatorIndex.getIndicatorsType().getValue(), indicatorIndex.getNetChange(), indicatorIndex.getPercentChange(), indicatorIndex.getPrice()));
        });
        return indicatorDataList;
    }

    public Indicators findBySymbol(String symbol){
        log.info("[FIND-INDICATORS-DATA] Find Indicators data By Symbol, Symbol : {}", symbol);
        return indicatorsRepository.findBySymbol(symbol);
    }

    public List<IndicatorsIndex> getAllIndexOfIndicators(Indicators indicators) {
        List<IndicatorsIndex> indicatorsIndexList = indicatorsIndexRepository.findAllByIndicatorsOrderByAsOfDateDesc(indicators);
        log.info("[FIND-STOCK-INDEX-ALL] Find Stock Index Cnt : {}, Symbol : {}",indicatorsIndexList.size(), indicators.getSymbol());
        return indicatorsIndexList;
    }
}

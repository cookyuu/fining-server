package com.hklim.finingserver.domain.indicators.service;

import com.hklim.finingserver.domain.indicators.entity.Indicators;
import com.hklim.finingserver.domain.indicators.entity.IndicatorsIndex;
import com.hklim.finingserver.domain.indicators.entity.IndicatorsType;
import com.hklim.finingserver.domain.indicators.repository.IndicatorIndexRepository;
import com.hklim.finingserver.domain.indicators.repository.IndicatorRepository;
import com.hklim.finingserver.domain.stock.entity.StockIndex;
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
    private final IndicatorRepository indicatorRepository;

    /*
    * 날짜, 지수 종류 둘다 확인 필요
    * */
    public boolean isExistIndicatorIndexToday(IndicatorsType indicatorsType) {
        log.info("[INDICATOR-CRAWLING] Check if today's Indicator Data has already been inserted.  Date : {}, IndicatorType : {}", LocalDate.now(), indicatorsType);
        return indicatorIndexRepository.existsByAsOfDateAndIndicatorType(LocalDate.now(), indicatorsType);
    }

    public List<MainUiDataResponseDto.IndicatorData> getIndicatorOfToday() {
        List<IndicatorsIndex> indicatorIndices = indicatorIndexRepository.findAllByAsOfDate(LocalDate.now());
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
        return indicatorRepository.findBySymbol(symbol);
    }

    public List<IndicatorsIndex> getAllIndexOfIndicators(Indicators indicators) {
        List<IndicatorsIndex> indicatorsIndexList = indicatorIndexRepository.findAllByIndicators(indicators);
        log.info("[FIND-STOCK-INDEX-ALL] Find Stock Index Cnt : {}, Symbol : {}",indicatorsIndexList.size(), indicators.getSymbol());
        return indicatorsIndexList;
    }
}

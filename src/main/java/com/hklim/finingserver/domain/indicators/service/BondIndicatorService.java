package com.hklim.finingserver.domain.indicators.service;

import com.hklim.finingserver.domain.indicators.dto.BondDataResponseDto;
import com.hklim.finingserver.domain.indicators.entity.Indicator;
import com.hklim.finingserver.domain.indicators.entity.IndicatorIndex;
import com.hklim.finingserver.domain.indicators.entity.IndicatorType;
import com.hklim.finingserver.domain.indicators.repository.IndicatorIndexRepository;
import com.hklim.finingserver.domain.indicators.repository.IndicatorRepository;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import com.hklim.finingserver.global.utils.CrawlerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BondIndicatorSerivce implements IndicatorService{

    private final IndicatorRepository indicatorRepository;
    private final IndicatorIndexRepository indicatorIndexRepository;
    private final CrawlerUtils crawlerUtils;
    // 크롤링
    // 데이터 확인 후 Indicator에 등록되어있으면 그대로 아니면 등록
    // 데이터 insert
    @Override
    public void insertData() {
        List<IndicatorIndex> indicatorIndexList = new ArrayList<>();
        toEntityList(indicatorIndexList, crawlerUtils.getBondData().getDataList());
        try {
            indicatorIndexRepository.saveAll(indicatorIndexList);
        } catch (Exception e) {
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_CRAWLING_SAVE, e);
        }
    }

    private void toEntityList(List<IndicatorIndex> indicatorIndexList, List<BondDataResponseDto.Data> dataList) {
        try {
            dataList.forEach(data -> {
                Indicator indicator = indicatorRepository.findBySymbol(data.getSymbol());
                if (indicator == null) {
                    Indicator newIndicator = indicatorRepository.save(new Indicator(data.getIndicatorName(), data.getSymbol(), IndicatorType.BOND));
                    indicatorIndexList.add(new IndicatorIndex(data.getNetChange(), data.getPercentChange(), data.getPrice(),
                            LocalDate.now(), newIndicator));
                } else {
                    indicatorIndexList.add(new IndicatorIndex(data.getNetChange(), data.getPercentChange(), data.getPrice(),
                            LocalDate.now(), indicator));
                }
            });
        } catch (Exception e) {
            throw new ApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, e, "[INDICATOR-CRAWLING] Bond Indicator Data convert to Entity is Failed. ");
        }
    }

}

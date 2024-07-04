package com.hklim.finingserver.domain.indicators.service;

import com.hklim.finingserver.domain.indicators.dto.StockIndicatorDataResponseDto;
import com.hklim.finingserver.domain.indicators.entity.Indicator;
import com.hklim.finingserver.domain.indicators.entity.IndicatorIndex;
import com.hklim.finingserver.domain.indicators.entity.IndicatorType;
import com.hklim.finingserver.domain.indicators.repository.IndicatorIndexRepository;
import com.hklim.finingserver.domain.indicators.repository.IndicatorRepository;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import com.hklim.finingserver.global.utils.CrawlerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Primary
public class StockIndicatorService extends CommonIndicatorService implements IndicatorService{
    private final IndicatorRepository indicatorRepository;
    private final IndicatorIndexRepository indicatorIndexRepository;
    private final CrawlerUtils crawlerUtils;

    public StockIndicatorService(IndicatorIndexRepository indicatorIndexRepository, IndicatorRepository indicatorRepository, CrawlerUtils crawlerUtils) {
        super(indicatorIndexRepository);
        this.indicatorRepository = indicatorRepository;
        this.indicatorIndexRepository = indicatorIndexRepository;
        this.crawlerUtils = crawlerUtils;
    }

    @Override
    public void insertData() {
        List<IndicatorIndex> indicatorIndexList = new ArrayList<>();
        toEntityList(indicatorIndexList, crawlerUtils.getStockIndicatorData().getDataList());
        if (isExistIndicatorIndexToday(IndicatorType.STOCK)) {
            log.info("[INDICATOR-CRAWLING] Today's Stock Indicator data has already been inserted");
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_CRAWLING_SAVE, "[INDICATOR-CRAWLING] Today's Stock Indicator data has already been inserted. Please try again tomorrow. ");
        }
        try {
            log.info("[INDICATOR-CRAWLING] Stock Indicator Data Insert. ");

            indicatorIndexRepository.saveAll(indicatorIndexList);
        } catch (Exception e) {
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_CRAWLING_SAVE, e);
        }
    }
    private void toEntityList(List<IndicatorIndex> indicatorIndexList, List<StockIndicatorDataResponseDto.Data> dataList) {
        try {
            dataList.forEach(data -> {
                Indicator indicator = indicatorRepository.findBySymbol(data.getSymbol());
                if (indicator == null) {
                    log.info("[INDICATOR-CRAWLING] New Indicator Data Insert. Symbol : {}", data.getSymbol());
                    Indicator newIndicator = indicatorRepository.save(new Indicator(data.getIndicatorName(), data.getSymbol(), IndicatorType.STOCK));
                    indicatorIndexList.add(new IndicatorIndex(data.getNetChange(), data.getPercentChange(), data.getPrice(),
                            LocalDate.now(), newIndicator.getIndicatorType(), newIndicator));
                } else {
                    indicatorIndexList.add(new IndicatorIndex(data.getNetChange(), data.getPercentChange(), data.getPrice(),
                            LocalDate.now(), indicator.getIndicatorType(), indicator));
                }
            });
        } catch (Exception e) {
            throw new ApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, e, "[INDICATOR-CRAWLING] Stock Indicator Data convert to Entity is Failed. ");
        }
    }
}

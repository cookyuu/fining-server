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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BondIndicatorService extends CommonIndicatorService implements IndicatorService{

    private final IndicatorRepository indicatorRepository;
    private final IndicatorIndexRepository indicatorIndexRepository;
    private final CrawlerUtils crawlerUtils;

    public BondIndicatorService(IndicatorIndexRepository indicatorIndexRepository, IndicatorRepository indicatorRepository, CrawlerUtils crawlerUtils) {
        super(indicatorIndexRepository);
        this.indicatorRepository = indicatorRepository;
        this.indicatorIndexRepository = indicatorIndexRepository;
        this.crawlerUtils = crawlerUtils;
    }

    @Override
    public void insertData() {
        List<IndicatorIndex> indicatorIndexList = new ArrayList<>();
        toEntityList(indicatorIndexList, crawlerUtils.getBondData().getDataList());
        if (isExistIndicatorIndexToday()) {
            log.info("[INDICATOR-CRAWLING] Today Indicator data");
            return;
        }
        try {
            log.info("[INDICATOR-CRAWLING] Bond Indicator Data Insert. ");
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
                    log.info("[INDICATOR-CRAWLING] New Indicator Data Insert.  Symbol : {}", data.getSymbol());
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

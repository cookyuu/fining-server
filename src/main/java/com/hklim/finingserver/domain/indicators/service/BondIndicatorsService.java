package com.hklim.finingserver.domain.indicators.service;

import com.hklim.finingserver.domain.indicators.dto.BondDataResponseDto;
import com.hklim.finingserver.domain.indicators.entity.Indicators;
import com.hklim.finingserver.domain.indicators.entity.IndicatorsIndex;
import com.hklim.finingserver.domain.indicators.entity.IndicatorsType;
import com.hklim.finingserver.domain.indicators.repository.IndicatorsIndexRepository;
import com.hklim.finingserver.domain.indicators.repository.IndicatorsRepository;
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
public class BondIndicatorsService extends CommonIndicatorsService implements IndicatorsService {

    private final IndicatorsRepository indicatorsRepository;
    private final IndicatorsIndexRepository indicatorsIndexRepository;
    private final CrawlerUtils crawlerUtils;

    public BondIndicatorsService(IndicatorsIndexRepository indicatorsIndexRepository, IndicatorsRepository indicatorsRepository, CrawlerUtils crawlerUtils) {
        super(indicatorsIndexRepository, indicatorsRepository);
        this.indicatorsRepository = indicatorsRepository;
        this.indicatorsIndexRepository = indicatorsIndexRepository;
        this.crawlerUtils = crawlerUtils;
    }

    @Override
    public void insertData() {
        List<IndicatorsIndex> indicatorsIndexList = new ArrayList<>();
        toEntityList(indicatorsIndexList, crawlerUtils.getBondData().getDataList());
        if (isExistIndicatorIndexToday(IndicatorsType.BOND)) {
            log.info("[INDICATOR-CRAWLING] Today's Bond Indicator data has already been inserted. ");
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_CRAWLING_SAVE, "[INDICATOR-CRAWLING] Today's Bond Indicator data has already been inserted. Please try again tomorrow. ");
        }
        try {
            log.info("[INDICATOR-CRAWLING] Bond Indicator Data Insert. ");
            indicatorsIndexRepository.saveAll(indicatorsIndexList);
        } catch (Exception e) {
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_CRAWLING_SAVE, e);
        }
    }

    private void toEntityList(List<IndicatorsIndex> indicatorsIndexList, List<BondDataResponseDto.Data> dataList) {
        try {
            dataList.forEach(data -> {
                Indicators indicators = indicatorsRepository.findBySymbol(data.getSymbol());
                if (indicators == null) {
                    log.info("[INDICATOR-CRAWLING] New Indicator Data Insert.  Symbol : {}", data.getSymbol());
                    Indicators newIndicators = indicatorsRepository.save(new Indicators(data.getIndicatorsName(), data.getSymbol(), IndicatorsType.BOND));
                    indicatorsIndexList.add(new IndicatorsIndex(data.getNetChange(), data.getPercentChange(), data.getPrice(),
                            LocalDate.now(), newIndicators.getIndicatorsType(), newIndicators));
                } else {
                    indicatorsIndexList.add(new IndicatorsIndex(data.getNetChange(), data.getPercentChange(), data.getPrice(),
                            LocalDate.now(), indicators.getIndicatorsType(), indicators));
                }
            });
        } catch (Exception e) {
            throw new ApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, e, "[INDICATOR-CRAWLING] Bond Indicator Data convert to Entity is Failed. ");
        }
    }

}

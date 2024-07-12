package com.hklim.finingserver.domain.indicators.service;

import com.hklim.finingserver.domain.ui.dto.MainUiDataResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommonIndicatorsServiceTest {
    Logger log = LogManager.getLogger(CommonIndicatorsServiceTest.class);
    @Autowired
    CommonIndicatorsService commonIndicatorsService;
    @Test
    void getIndicatorOfToday() {
        List<MainUiDataResponseDto.IndicatorData> indicatorDataList = commonIndicatorsService.getIndicatorOfToday();
        log.info("Indicator Cnt : {}", indicatorDataList.size());
        indicatorDataList.forEach(indicatorData -> {
            log.info("Name : {}, Symbol : {}, IndicatorType : {}, NetChange : {}, PercentChange : {}, Price : {}",
                    indicatorData.getName(), indicatorData.getSymbol(), indicatorData.getIndicatorType(), indicatorData.getNetChange(), indicatorData.getPercentChange(), indicatorData.getPrice());
        });
    }
}
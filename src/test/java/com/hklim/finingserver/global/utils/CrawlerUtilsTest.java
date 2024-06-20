package com.hklim.finingserver.global.utils;

import com.hklim.finingserver.domain.indicators.dto.BondDataResponseDto;
import com.hklim.finingserver.domain.indicators.dto.StockIndicatorDataResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrawlerUtilsTest {
    Logger log = LogManager.getLogger(CrawlerUtilsTest.class);

    @Autowired
    CrawlerUtils crawlerUtils;
    @Test
    void getBondIndicatorData() {
        BondDataResponseDto resData = crawlerUtils.getBondData();
        resData.getDataList().forEach(i -> log.info("SYMBOL : " + i.getSymbol() + ", IndexName : " + i.getIndicatorName() + ", Price : " + i.getPrice()));
    }
    @Test
    void getStockIndicatorData() {
        StockIndicatorDataResponseDto resData = crawlerUtils.getStockIndicatorData();
        resData.getDataList().forEach(i -> log.info("SYMBOL : " + i.getSymbol() + ", IndexName : " + i.getIndicatorName() + ", Price : " + i.getPrice()));
    }
}
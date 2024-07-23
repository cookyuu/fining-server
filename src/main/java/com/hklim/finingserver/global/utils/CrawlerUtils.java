package com.hklim.finingserver.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hklim.finingserver.domain.indicators.dto.BondDataResponseDto;
import com.hklim.finingserver.domain.indicators.dto.StockIndicatorsDataResponseDto;
import com.hklim.finingserver.domain.stock.dto.SingleStockDataResponseDto;
import com.hklim.finingserver.domain.stock.dto.StockDataResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlerUtils {
    @Value("${service.stock.single-url-head}")
    private String singleStockUrlHead;

    @Value("${service.stock.single-url-tail}")
    private String singleStockUrlTail;

    @Value("${service.stock.total-url}")
    private String totalStockUrl;

    @Value("${service.indicator.stock-url}")
    private String stockIndicatorUrl;

    @Value("${service.indicator.bond-url}")
    private String bondIndicatorUrl;

    public SingleStockDataResponseDto getSingleStockInfo(String symbol) {
        String url = singleStockUrlHead + symbol + singleStockUrlTail;
        Connection.Response docs = null;

        try {
            docs = Jsoup.connect(url)
                    .timeout(60*1000)
                    .ignoreContentType(true)
                    .execute();

        } catch (IOException e) {
            log.info("[STOCK-CRAWLING] Fail to Crawling Stock Data. Symbol : {}, error msg : {}", symbol, e.getStackTrace());
            return null;
        }
        return toSingleStockDataDto(docs.body());
    }

    public StockDataResponseDto getTotalStockInfo(int offset) {
        String url = totalStockUrl + offset*1000;
        Connection.Response docs = null;
        try {
            docs = Jsoup.connect(url)
                    .timeout(60*1000)
                    .ignoreContentType(true)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toTotalStockDataDto(docs.body());
    }

    private SingleStockDataResponseDto toSingleStockDataDto(String stockData) {
        ObjectMapper mapper = new ObjectMapper();
        SingleStockDataResponseDto data = new SingleStockDataResponseDto();
        try {
            log.debug("[STOCK-CRAWLING] Convert Data to Dto. ");
            data = mapper.readValue(stockData, SingleStockDataResponseDto.class);
        } catch (JsonProcessingException e) {
            log.info("[STOCK-CRAWLING] Fail to Crawling from URL. error msg : {} ", e.getMessage());
            return null;
        }
        return data;
    }
    private StockDataResponseDto toTotalStockDataDto(String stockData) {
//        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ObjectMapper mapper = new ObjectMapper();
        StockDataResponseDto data = new StockDataResponseDto();
        try {
            log.info("[STOCK-CRAWLING] Convert Data to Dto. ");
            data = mapper.readValue(stockData, StockDataResponseDto.class);
        } catch (JsonProcessingException e) {
            log.info("[STOCK-CRAWLING] Fail to Crawling from URL. error msg : {} ", e.getMessage());
            return null;
        }
        return data;
    }

    public BondDataResponseDto getBondData() {
        Connection.Response docs = null;
        try {
            docs = Jsoup.connect(bondIndicatorUrl)
                    .timeout(60*1000)
                    .ignoreContentType(true)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toDto(new BondDataResponseDto(), docs.body());
    }

    public StockIndicatorsDataResponseDto getStockIndicatorData() {
        Connection.Response docs = null;
        try {
            docs = Jsoup.connect(stockIndicatorUrl)
                    .timeout(60*1000)
                    .ignoreContentType(true)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return toDto(new StockIndicatorsDataResponseDto(), docs.body());
    }

    private <T> T toDto(T dto, String bondData) {
        ObjectMapper mapper = new ObjectMapper();
        T data;
        try {
            log.info("[INDICATOR-CRAWLING] Convert Data to Dto. ");
            data = (T) mapper.readValue(bondData, dto.getClass());
        } catch (JsonProcessingException e) {
            log.info("[INDICATOR-CRAWLING] Fail to Crawling from URL. error msg : {} ", e.getMessage());
            return null;
        }
        return data;
    }
}

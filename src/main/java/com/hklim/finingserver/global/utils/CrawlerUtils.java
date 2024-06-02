package com.hklim.finingserver.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hklim.finingserver.domain.stock.dto.SingleStockDataResponseDto;
import com.hklim.finingserver.domain.stock.dto.StockDataResponseDto;
import com.hklim.finingserver.domain.stock.dto.StockDataResponseDto_bak;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CrowlerUtils {
    @Value("${service.stock.single-url-head}")
    private String singleStockUrlHead;

    @Value("${service.stock.single-url-tail}")
    private String singleStockUrlTail;

    @Value("${service.stock.total-url")
    private String totalStockUrl;

    public SingleStockDataResponseDto getSingleStockInfo(String symbol) {
        String url = singleStockUrlHead + symbol + singleStockUrlTail;
        Connection.Response docs = null;

        try {
            docs = Jsoup.connect(url)
                    .timeout(60*1000)
                    .ignoreContentType(true)
                    .execute();

        } catch (IOException e) {
            log.info("[CROWLING STOCK DATA]");
            return null;
        }
        return toSingleStockDataDto(docs.body());
    }

    public StockDataResponseDto_bak getTotalStockInfo() {
        String url = totalStockUrl;
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
            data = mapper.readValue(stockData, SingleStockDataResponseDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;
    }
    private StockDataResponseDto_bak toTotalStockDataDto(String stockData) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        StockDataResponseDto_bak data = new StockDataResponseDto_bak();
        try {
            data = mapper.readValue(stockData, StockDataResponseDto_bak.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;
    }

}

package com.hklim.finingserver.domain.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hklim.finingserver.domain.stock.dto.StockDataResponseDto;
import com.hklim.finingserver.global.utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@SpringBootTest
class StockServiceTest {
    Logger log = LogManager.getLogger(StockServiceTest.class);

    @Autowired
    FileUtils fileUtils;
    @Autowired
    StockService stockService;

    @BeforeEach
    void beforeEach() {

    }
    @Test
    public void collectStockDataTest() throws IOException {
        String filePath = "/Users/HK/Downloads";
        String fileName = "nasdaq_screener_1716015574955.csv";
        String fileFullPath = Paths.get(filePath, fileName).toString();

        fileUtils.toDtoFromCSVFile(fileFullPath).forEach(i -> System.out.println(i.getIpoYear()));
    }

    @Test
    public void getTotalStockinfoTest() throws IOException {
        stockService.insertTotalData();
    }

    @Test
    public void convertAsofToLocalDateTest() {
//        String asOf = "Last price as of May 31, 2024";
        String asOf = "Last price as of Jun 3, 2024";
        String asOfDate = asOf.replace("Last price as of ","").trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

        try {
            // Parse the date string to LocalDate
            LocalDate date = LocalDate.parse(asOfDate, formatter);
            System.out.println("Parsed date: " + date);
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }
    @Test
    public void testDataParseByJsoup() {
        String url = "https://api.nasdaq.com/api/screener/stocks?tableonly=true&limit=25&offset=0";
        Connection.Response docs = null;
        try {
            log.info("[STOCK-CRAWLING] Data parsing from Jsoup. Start");
            docs = Jsoup.connect(url)
                    .timeout(60*1000)
                    .ignoreContentType(true)
                    .execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StockDataResponseDto data = toTotalStockDataDto(docs.body());
        log.info("Data : {}", data.getData().getTable().getRows().get(0).getName());

    }

    private StockDataResponseDto toTotalStockDataDto(String stockData) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        StockDataResponseDto data = new StockDataResponseDto();
        try {
            data = mapper.readValue(stockData, StockDataResponseDto.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;
    }



}

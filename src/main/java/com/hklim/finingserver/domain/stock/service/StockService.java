package com.hklim.finingserver.domain.stock.service;

import com.hklim.finingserver.domain.stock.dto.InsertStockDataRequestDto;
import com.hklim.finingserver.domain.stock.dto.StockDataFromCSVDto;
import com.hklim.finingserver.domain.stock.dto.StockDataResponseDto;
import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.domain.stock.entity.StockIndex;
import com.hklim.finingserver.domain.stock.repository.StockIndexRepository;
import com.hklim.finingserver.domain.stock.repository.StockRepository;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import com.hklim.finingserver.global.utils.CrawlerUtils;
import com.hklim.finingserver.global.utils.FileUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {
    private final FileUtils fileUtils;
    private final CrawlerUtils crawlerUtils;
    private final StockRepository stockRepository;
    private final StockIndexRepository stockIndexRepository;

    @Value("${service.stock.max-count}")
    int maxCnt;

    @Transactional
    public void insertStockDataFromCSV(InsertStockDataRequestDto insertStockDataInfo) {
        String fileFullPath = Paths.get(insertStockDataInfo.getFilePath(), insertStockDataInfo.getFileName()).toString();
        List<StockDataFromCSVDto> stockDataList = fileUtils.toDtoFromCSVFile(fileFullPath);
        if (stockDataList.isEmpty()) {
            log.info("Extract Stock Data Count is Zero. ");
        } else {
            log.info("[INSERT STOCK DATA PROCESS] START. ");
            for (StockDataFromCSVDto stockData : stockDataList) {
                Stock stock = stockRepository.save(stockData.toStockEntity());
                stockIndexRepository.save(stockData.toStockIndexEntity(stock));
            }
            log.info("[INSERT STOCK DATA PROCESS] END. Insert Data Count : {}", stockDataList.size());
        }
    }

    @Transactional
    public void insertTotalData() {
        List<StockIndex> stockIndexList = new ArrayList<>();

        for (int i=0; i<(maxCnt/1000); i++) {
            StockDataResponseDto resData = crawlerUtils.getTotalStockInfo(i);
            int statusCode = resData.getStatus().getRCode();
            int totalRecords = resData.getData().getTotalRecords();
            if (totalRecords/1000 < i) {
                break;
            }
            LocalDate lastPriceDate = convertAsofToLocalDate(resData.getData().getAsof());
            log.info("[STOCK_CRAWLING] Last Price Date : {}", lastPriceDate);

            if (i==0 && stockIndexRepository.existsByLastSaleDate(lastPriceDate)) {
                log.info("[STOCK_CRAWLING] Already registration stock index. last price date : {}",lastPriceDate);
                throw new ApplicationErrorException(ApplicationErrorType.FAIL_CRAWLING_SAVE, "해당 날짜의 주식 종가 데이터는 이미 등록되어있습니다. 날짜 : " + lastPriceDate);
            }

            List<StockDataResponseDto.Data.Table.Row> rows = resData.getData().getTable().getRows();
            log.info("[STOCK-CRAWLING] Response Status Code : {}", statusCode);
            log.info("[STOCK-CRAWLING] Crawling Data Total cnt : {}", totalRecords);
            log.info("[STOCK-CRAWLING] Crawling Data cnt : {}", rows.size());

            convertStockIndexData(stockIndexList, rows, lastPriceDate);
            log.info("[STOCK-CRAWLING] Insert Stock Index. Insert data cnt : {}", stockIndexList.size());
        }
        try {
            stockIndexRepository.saveAll(stockIndexList);
            log.info("[STOCK-CRAWLING] Finally insert stock index data cnt : {}" , stockIndexList.size());
        } catch (Exception e) {
            log.info("[STOCK-CRAWLING] Fail to insert Stock Index. errMsg : {}", (Object) e.getStackTrace());
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_TO_SAVE_DATA);
        }
    }

    private void convertStockIndexData(List<StockIndex> stockIndexList, List<StockDataResponseDto.Data.Table.Row> stockDataList, LocalDate lastPriceDate) {
        stockDataList.forEach(
                stockData -> {
                    Stock stock = stockRepository.findBySymbol(stockData.getSymbol());
                    if (stock != null) {
                        stockIndexList.add(new StockIndex(stockData.getLastsale(), stockData.getMarketCap(), stockData.getNetchange(), stockData.getPctchange(), lastPriceDate, stock));
                    }
                });
    }

    private LocalDate convertAsofToLocalDate(String asof) {
        log.info("[STOCK_CRAWLING] Convert LastPrice Type, String to LocalDate.");
        String asOfDate = asof.replace("Last price as of ","").trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

        try {
            return LocalDate.parse(asOfDate, formatter);
        } catch (DateTimeParseException e) {
            log.info("[STOCK_CRAWLING] Fail to convert LastPrice Date. Error Message : {}", e.getMessage());
        }
        return null;
    }
}

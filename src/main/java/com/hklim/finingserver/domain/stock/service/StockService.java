package com.hklim.finingserver.domain.stock.service;

import com.hklim.finingserver.domain.portfolio.entity.Portfolio;
import com.hklim.finingserver.domain.stock.dto.InsertStockDataRequestDto;
import com.hklim.finingserver.domain.stock.dto.SearchStockDataResponseDto;
import com.hklim.finingserver.domain.stock.dto.StockDataFromCSVDto;
import com.hklim.finingserver.domain.stock.dto.StockDataResponseDto;
import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.domain.stock.entity.StockIndex;
import com.hklim.finingserver.domain.stock.repository.StockIndexRepository;
import com.hklim.finingserver.domain.stock.repository.StockRepository;
import com.hklim.finingserver.domain.ui.dto.MainUiDataResponseDto;
import com.hklim.finingserver.domain.ui.dto.UiStockDataResponseDto;
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
import java.util.ArrayList;
import java.util.List;

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
            log.info("[INSERT-STOCK-DATA] Extract Stock Data Count is Zero. ");
        } else {
            log.info("[INSERT-STOCK-DATA] START. ");
            for (StockDataFromCSVDto stockData : stockDataList) {
                Stock stock = stockRepository.save(stockData.toStockEntity());
                stockIndexRepository.save(stockData.toStockIndexEntity(stock));
            }
            log.info("[INSERT-STOCK-DATA] END. Insert Data Count : {}", stockDataList.size());
        }
    }

    @Transactional
    public void insertTotalData() {
        List<StockIndex> stockIndexList = new ArrayList<>();

        LocalDate asOfDate = LocalDate.now();
        log.info("[STOCK-CRAWLING] As Of Date : {}", asOfDate);

        log.info("[INDICATOR-CRAWLING] Check if today's Stock Index Data has already been inserted.  Date : {}", LocalDate.now());
        if (stockIndexRepository.existsByAsOfDate(asOfDate)) {
            log.info("[STOCK-CRAWLING] Already registration stock index. last price date : {}",asOfDate);
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_CRAWLING_SAVE, "[STOCK-CRAWLING] Today's Stock Indicator data has already been inserted. Please try again tomorrow. ");
        }

        for (int i=0; i<(maxCnt/1000); i++) {
            StockDataResponseDto resData = crawlerUtils.getTotalStockInfo(i);
            int statusCode = resData.getStatus().getRCode();
            int totalRecords = resData.getData().getTotalRecords();
            if (totalRecords/1000 < i) {
                break;
            }

            List<StockDataResponseDto.Data.Table.Row> rows = resData.getData().getTable().getRows();
            log.info("[STOCK-CRAWLING] Response Status Code : {}", statusCode);
            log.info("[STOCK-CRAWLING] Crawling Data Total cnt : {}", totalRecords);
            log.info("[STOCK-CRAWLING] Crawling Data cnt : {}", rows.size());

            convertCrawlingDataToStockIndexList(stockIndexList, rows);
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

    public Stock findBySymbol(String symbol){
        log.debug("[FIND-STOCK-DATA] Find Stock data By Symbol, Symbol : {}", symbol);
        return stockRepository.findBySymbol(symbol);
    }

    public UiStockDataResponseDto getPortfolioStockData(Portfolio portfolio) {
        Stock stock = portfolio.getStock();
        StockIndex stockIndex = stockIndexRepository.findByStockAndAsOfDate(stock, LocalDate.now());
        return UiStockDataResponseDto.builder()
                .stockId(stock.getId())
                .symbol(stock.getSymbol())
                .name(stock.getName())
                .lastSale(stockIndex.getLastSale())
                .marketCap(stockIndex.getMarketCap())
                .netChange(stockIndex.getNetChange())
                .percentChange(stockIndex.getPercentChange())
                .build();
    }

    public List<UiStockDataResponseDto> getTopTenStocksOfToday() {
        List<StockIndex> stockIndexList = stockIndexRepository.findTop10ByAsOfDateOrderByMarketCapDesc(LocalDate.now());
        return convertStockIndexToMainStockData(stockIndexList);
    }

    private void convertCrawlingDataToStockIndexList(List<StockIndex> stockIndexList, List<StockDataResponseDto.Data.Table.Row> stockDataList) {
        stockDataList.forEach(
                stockData -> {
                    Stock stock = findBySymbol(stockData.getSymbol());
                    if (stock != null) {
                        stockIndexList.add(new StockIndex(stockData.getLastsale(), convertMarketCapStrToLong(stockData.getMarketCap()), stockData.getNetchange(), stockData.getPctchange(), LocalDate.now(), stock));
                    }
                });
    }

    private List<UiStockDataResponseDto> convertStockIndexToMainStockData(List<StockIndex> stockIndexList) {
        List<UiStockDataResponseDto> mainStockDataList = new ArrayList<>();
        stockIndexList.forEach(stockIndex -> {
            mainStockDataList.add(new UiStockDataResponseDto(stockIndex.getStock().getId(), stockIndex.getStock().getSymbol(),stockIndex.getStock().getName()
            ,stockIndex.getLastSale(), stockIndex.getMarketCap(), stockIndex.getNetChange(), stockIndex.getPercentChange()));
        });
        return mainStockDataList;
    }

    private Long convertMarketCapStrToLong(String strMarketCap) {
        return strMarketCap==null || strMarketCap.equals("NA") ? 0L : Long.parseLong(strMarketCap.replace(",", ""));
    }

    public List<StockIndex> getAllIndexOfStock(Stock stock) {
        List<StockIndex> stockIndexList = stockIndexRepository.findAllByStockOrderByAsOfDateDesc(stock);
        log.info("[FIND-STOCK-INDEX-ALL] Find Stock Index Cnt : {}, Symbol : {}",stockIndexList.size(), stock.getSymbol());
        return stockIndexList;
    }

    public SearchStockDataResponseDto searchStockData(String keyword) {
        log.info("[SEARCH-STOCK] Searching stock data by keyword");
        List<Stock> stockList = stockRepository.findByNameContainingOrSymbolContaining(keyword, keyword);
        return convertToSearchStockDataResponse(stockList);
    }

    private SearchStockDataResponseDto convertToSearchStockDataResponse(List<Stock> stockList) {
        log.info("[SEARCH-STOCK] Converting to response stock data");
        int searchDataCnt = stockList.size();
        log.info("[SEARCH-STOCK] Search data cnt : {}", searchDataCnt);
        List<SearchStockDataResponseDto.StockData> resStockData = new ArrayList<>();
            stockList.forEach(stock -> {
                resStockData.add(
                        SearchStockDataResponseDto.StockData.builder()
                                .name(stock.getName())
                                .symbol(stock.getSymbol())
                                .build());
            });

        return SearchStockDataResponseDto.builder()
                .searchCnt(searchDataCnt)
                .stockDataList(resStockData)
                .build();
    }
}

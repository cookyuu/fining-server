package com.hklim.finingserver.domain.ui.service;

import com.hklim.finingserver.domain.indicators.service.CommonIndicatorService;
import com.hklim.finingserver.domain.portfolio.service.PortfolioService;
import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.domain.stock.entity.StockIndex;
import com.hklim.finingserver.domain.stock.service.StockService;
import com.hklim.finingserver.domain.ui.dto.MainUiDataResponseDto;
import com.hklim.finingserver.domain.ui.dto.StockDetailUiDataResponseDto;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UiService {
    private final PortfolioService portfolioService;
    private final StockService stockService;
    private final CommonIndicatorService indicatorService;


    public MainUiDataResponseDto getMainUiData(UserDetails user) {
        List<MainUiDataResponseDto.StockData> resPortfolioDataList = new ArrayList<>();
        if (user != null) {
            // 포트폴리오 데이터 가져오기
            resPortfolioDataList = portfolioService.getPortfolioStocks(user.getUsername());
        }
        // 오늘 주식 Top 10 정보
        List<MainUiDataResponseDto.StockData> resStockDataList = stockService.getTopTenStocksOfToday();
        // 오늘 지수정보
        List<MainUiDataResponseDto.IndicatorData> resIndicatorDateList = indicatorService.getIndicatorOfToday();
        return MainUiDataResponseDto.builder()
                .portfolioList(resPortfolioDataList)
                .stockList(resStockDataList)
                .indicatorDataList(resIndicatorDateList)
                .asOfDate(LocalDate.now())
                .build();
    }

    public StockDetailUiDataResponseDto getStockDetailData(String symbol) {
        log.info("[STOCK-DETAIL-UI-DATA] Find Stock Detail UI Data, Symbol : {}", symbol);
        Stock stock = stockService.findBySymbol(symbol);
        if (stock == null) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_STOCK,"[STOCK-DETAIL-UI-DATA] Not Found Stock Data, Symbol : {"+symbol+"}");
        }

        List<StockIndex> stockIdxList = stockService.getAllIndexOfStock(stock);
        List<StockDetailUiDataResponseDto.StockDetailIndexData> resStockDetailList = convertToUiStockIdxList(stockIdxList);

        return StockDetailUiDataResponseDto.builder()
                .id(stock.getId())
                .symbol(stock.getSymbol())
                .name(stock.getName())
                .sector(stock.getSector())
                .country(stock.getCountry())
                .industry(stock.getIndustry())
                .ipoYear(stock.getIpoYear())
                .indexList(resStockDetailList)
                .build();
    }

    private List<StockDetailUiDataResponseDto.StockDetailIndexData> convertToUiStockIdxList(List<StockIndex> stockIdxList) {
        log.info("[CONVERT-UI-DATA] Convert to UI Stock Index List");
        List<StockDetailUiDataResponseDto.StockDetailIndexData> detailIndexDataList = new ArrayList<>();
        stockIdxList.forEach(stockData -> {
            detailIndexDataList.add(StockDetailUiDataResponseDto.StockDetailIndexData.builder()
                    .lastSale(stockData.getLastSale())
                    .marketCap(stockData.getMarketCap())
                    .netChange(stockData.getNetChange())
                    .percentChange(stockData.getPercentChange())
                    .asOfDate(stockData.getAsOfDate())
                    .build());
        });
        return detailIndexDataList;
    }
}

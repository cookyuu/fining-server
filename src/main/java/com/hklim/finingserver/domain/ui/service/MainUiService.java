package com.hklim.finingserver.domain.ui.service;

import com.hklim.finingserver.domain.indicators.service.CommonIndicatorService;
import com.hklim.finingserver.domain.portfolio.service.PortfolioService;
import com.hklim.finingserver.domain.stock.service.StockService;
import com.hklim.finingserver.domain.ui.dto.MainUiDataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainUiService {
    private final PortfolioService portfolioService;
    private final StockService stockService;
    private final CommonIndicatorService indicatorService;


    public MainUiDataResponseDto getMainUiData(UserDetails user) {

        if (user != null) {
            // 포트폴리오 데이터 가져오기
            List<MainUiDataResponseDto.StockData> resPortfolioDataList = portfolioService.getPortfolioStocks(user.getUsername());
        }
        // 오늘 주식 Top 6 정보
        List<MainUiDataResponseDto.StockData> resStockDataList = stockService.getTopSixStocksOfToday();
        // 오늘 지수정보
        List<MainUiDataResponseDto.IndicatorData> resIndicatorDateList = indicatorService.getIndicatorOfToday();
        MainUiDataResponseDto res = MainUiDataResponseDto.builder()
                .portfolioList(resStockDataList)
                .stockList(resStockDataList)
                .indicatorDataList(resIndicatorDateList)
                .asOfDate(LocalDate.now())
                .build();

        return null;
    }
}

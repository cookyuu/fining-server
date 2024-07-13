package com.hklim.finingserver.domain.ui.service;

import com.hklim.finingserver.domain.indicators.entity.Indicators;
import com.hklim.finingserver.domain.indicators.entity.IndicatorsIndex;
import com.hklim.finingserver.domain.indicators.service.CommonIndicatorsService;
import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.member.service.MemberService;
import com.hklim.finingserver.domain.portfolio.service.PortfolioService;
import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.domain.stock.entity.StockIndex;
import com.hklim.finingserver.domain.stock.service.StockService;
import com.hklim.finingserver.domain.ui.dto.IndicatorsDetailUiDataResponseDto;
import com.hklim.finingserver.domain.ui.dto.MainUiDataResponseDto;
import com.hklim.finingserver.domain.ui.dto.MyProfileUiDataResponseDto;
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
    private final CommonIndicatorsService indicatorService;
    private final MemberService memberService;


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
    public IndicatorsDetailUiDataResponseDto getIndicatorDetailData(String symbol) {
        log.info("[INDICATORS-DETAIL-UI-DATA] Find Indicators Detail UI Data, Symbol : {}", symbol);
        Indicators indicators = indicatorService.findBySymbol(symbol);
        if (indicators == null) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_INDICATORS, "[INDICATORS-DETAIL-UI-DATA] Not Found Indicators Data, Symbol : {"+symbol+"}");
        }
        List<IndicatorsIndex> indicatorsIndexList = indicatorService.getAllIndexOfIndicators(indicators);
        List<IndicatorsDetailUiDataResponseDto.IndicatorsIndexData> resIndicatorsIndexList = convertToUiIndicatorsIdxList(indicatorsIndexList);

        return IndicatorsDetailUiDataResponseDto.builder()
                .name(indicators.getName())
                .symbol(indicators.getSymbol())
                .indicatorsType(indicators.getIndicatorsType())
                .indicatorsIndexDataList(resIndicatorsIndexList)
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

    private List<IndicatorsDetailUiDataResponseDto.IndicatorsIndexData> convertToUiIndicatorsIdxList(List<IndicatorsIndex> indicatorsIdxList) {
        List<IndicatorsDetailUiDataResponseDto.IndicatorsIndexData> resIndicatorsIndexDataList = new ArrayList<>();
        indicatorsIdxList.forEach(indicatorsIndex -> {
            resIndicatorsIndexDataList.add(IndicatorsDetailUiDataResponseDto.IndicatorsIndexData.builder()
                    .netChange(indicatorsIndex.getNetChange())
                    .percentChange(indicatorsIndex.getPercentChange())
                    .price(indicatorsIndex.getPrice())
                    .asOfDate(indicatorsIndex.getAsOfDate())
                    .build());
        });
        return resIndicatorsIndexDataList;
    }

    public MyProfileUiDataResponseDto getMyProfileUiData(UserDetails user) {
        Member member = memberService.findMemberById(Long.valueOf(user.getUsername()));
        if (member == null) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_MEMBER, "[MY-PROFILE-UI-DATA] Not found member profile data in that token.");
        }
        return MyProfileUiDataResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .role(member.getRole())
                .build();
    }
}

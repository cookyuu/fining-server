package com.hklim.finingserver.domain.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainUiDataResponseDto {
    private List<StockData> portfolioList;
    private List<StockData> stockList;
    private List<IndicatorData> indicatorDataList;
    private LocalDate asOfDate;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StockData {
        private Long stockId;
        private String symbol;
        private String name;
        private String lastSale;
        private String marketCap;
        private String netChange;
        private String percentChange;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IndicatorData {
        private String name;
        private String symbol;
        private String indicatorType;
        private String netChange;
        private String percentChange;
        private String price;
    }
}

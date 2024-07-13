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
    private List<UiStockDataResponseDto> portfolioList;
    private List<UiStockDataResponseDto> stockList;
    private List<IndicatorData> indicatorDataList;
    private LocalDate asOfDate;

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

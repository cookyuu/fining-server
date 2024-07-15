package com.hklim.finingserver.domain.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SearchStockDataResponseDto {
    private int searchCnt;
    private List<StockData> stockDataList;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class StockData {
        private String name;
        private String symbol;
    }
}

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
public class StockDetailUiDataResponseDto {
    private Long id;
    private String symbol;
    private String name;
    private String sector;
    private String country;
    private String industry;
    private String ipoYear;
    private List<StockDetailIndexData> indexList;

    @Getter
    @Builder
    public static class StockDetailIndexData {
        private String lastSale;
        private Long marketCap;
        private String netChange;
        private String percentChange;
        private LocalDate asOfDate;
    }

}

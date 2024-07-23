package com.hklim.finingserver.domain.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UiStockDataResponseDto {
    private Long stockId;
    private String symbol;
    private String name;
    private String lastSale;
    private Long marketCap;
    private String netChange;
    private String percentChange;
}

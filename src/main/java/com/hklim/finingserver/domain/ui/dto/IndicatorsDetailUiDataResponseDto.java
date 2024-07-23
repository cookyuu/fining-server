package com.hklim.finingserver.domain.ui.dto;

import com.hklim.finingserver.domain.indicators.entity.IndicatorsType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndicatorsDetailUiDataResponseDto {
    private String name;
    private String symbol;
    @Enumerated(EnumType.STRING)
    private IndicatorsType indicatorsType;
    private List<IndicatorsIndexData> indicatorsIndexDataList;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class IndicatorsIndexData {
        private String netChange;
        private String percentChange;
        private String price;
        private LocalDate asOfDate;
    }
}

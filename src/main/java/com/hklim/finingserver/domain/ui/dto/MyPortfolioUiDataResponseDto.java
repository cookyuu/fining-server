package com.hklim.finingserver.domain.ui.dto;

import com.hklim.finingserver.global.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPortfolioUiDataResponseDto {
    private PageInfo pageInfo;
    private List<UiStockDataResponseDto> portfolioData;

}

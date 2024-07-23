package com.hklim.finingserver.domain.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AddPortfolioDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private String stockSymbol;
    }
}

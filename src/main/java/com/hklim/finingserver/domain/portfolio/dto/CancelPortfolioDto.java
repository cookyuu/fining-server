package com.hklim.finingserver.domain.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CancelPortfolioDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String stockSymbol;
    }
}

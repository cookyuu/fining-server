package com.hklim.finingserver.domain.indicators.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IndicatorType {
    BOND("BOND"), STOCK("STOCK"), RAW_MATERIAL("RAW_MATERIAL")
    ,EXCHANGE_RATE("EXCHANGE_RATE");
    String value;
}

package com.hklim.finingserver.global.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IndicatorType {
    SNP("S&P500"), NASDAK("NASDAK100"), RUSELL("RUSELL2000");
    String value;
}

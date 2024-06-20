package com.hklim.finingserver.domain.indicators.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BondDataResponseDto {

    @JsonProperty("data")
     private Data data;
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        @JsonProperty("Chg")
        private String chg;
        @JsonProperty("ChgPct")
        private String chgPct;
        @JsonProperty("Last")
        private String last;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Symbol")
        private String symbol;
    }
}

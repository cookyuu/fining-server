package com.hklim.finingserver.domain.indicators.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BondDataResponseDto {

    @JsonProperty("result")
     private List<Data> dataList;
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        @JsonProperty("name")
        private String IndicatorName;
        @JsonProperty("closePrice")
        private String price;
        @JsonProperty("fluctuations")
        private String netChange;
        @JsonProperty("fluctuationsRatio")
        private String percentChange;
        @JsonProperty("reutersCode")
        private String symbol;
    }
}

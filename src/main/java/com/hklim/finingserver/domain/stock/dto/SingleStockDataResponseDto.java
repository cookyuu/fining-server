package com.hklim.finingserver.domain.stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleStockDataResponseDto {
    private Data data;
    private String message;
    private Status status;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private String symbol;
        private String companyName;
        private String stockType;
        private String exchange;
        private Boolean isNasdaqListed;
        private Boolean isNasdaq100;
        private Boolean isHeld;
        private PrimaryData primaryData;
        private String secondaryData;
        private String marketStatus;
        private String assetClass;
        private KeyStats keyStats;
        private List<Notification> notifications;

        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PrimaryData {
            private String lastSalePrice;
            private String netChange;
            private String percentageChange;
            private String deltaIndicator;
            private String lastTradeTimestamp;
            private Boolean isRealTime;
            private String bidPrice;
            private String askPrice;
            private String bidSize;
            private String askSize;
            private String volume;
        }
        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class KeyStats {
            private Range fiftyTwoWeekHighLow;
            private Range dayrange;

            @Getter
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Range {
                private String label;
                private String value;
            }
        }
        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Notification {
            private String headline;
            private List<EventType> eventTypes;

            @Getter
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class EventType {
                private String message;
                private String eventName;
                private Url url;
                private String id;
                @Getter
                @AllArgsConstructor
                @NoArgsConstructor
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Url {
                    private String label;
                    private String value;
                }
            }
        }
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Status {
        private Integer rCode;
        private String bCodeMessage;
        private String developerMessage;
    }
}

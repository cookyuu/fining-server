package com.hklim.finingserver.domain.stock.dto;

import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.domain.stock.entity.StockIndex;
import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockDataFromCSVDto {
    @CsvBindByName(column = "Symbol")
    private String symbol;
    @CsvBindByName(column = "Name")
    private String name;
    @CsvBindByName(column = "Last Sale")
    private String lastSale;
    @CsvBindByName(column = "Net Change")
    private String netChange;
    @CsvBindByName(column = "% Change")
    private String change;
    @CsvBindByName(column = "Market Cap")
    private String marketCap;
    @CsvBindByName(column = "Country")
    private String country;
    @CsvBindByName(column = "IPO Year")
    private String ipoYear;
    @CsvBindByName(column = "Volume")
    private String volume;
    @CsvBindByName(column = "Sector")
    private String sector;
    @CsvBindByName(column = "Industry")
    private String industry;

    public Stock toStockEntity() {
        return Stock.builder()
                .symbol(this.symbol)
                .name(this.name)
                .sector(this.sector)
                .country(this.country)
                .industry(this.industry)
                .ipoYear(this.ipoYear)
                .build();
    }

    public StockIndex toStockIndexEntity(Stock stock) {
        return StockIndex.builder()
                .lastSale(this.lastSale)
                .marketCap(Long.parseLong(this.marketCap))
                .netChange(this.netChange)
                .percentChange(this.change)
                .stock(stock)
                .build();
    }
}

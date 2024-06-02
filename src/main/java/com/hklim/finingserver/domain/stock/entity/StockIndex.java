package com.hklim.finingserver.domain.stock.entity;

import com.hklim.finingserver.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockIndex extends BaseEntity {

    private String lastSale;
    private String marketCap;
    private String netChange;
    private String percentChange;
    private LocalDate lastSaleDate;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}

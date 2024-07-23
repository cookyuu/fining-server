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
    private Long marketCap;
    private String netChange;
    private String percentChange;
    private LocalDate asOfDate;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}

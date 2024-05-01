package com.hklim.finingserver.domain.stock.entity;

import com.hklim.finingserver.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockIndex extends BaseEntity {
    @NotNull
    private String lastSale;
    @NotNull
    private String changePercent;
    @NotNull
    private String marketCap;

    @ManyToOne()
    @JoinColumn(name = "stock_id")
    private Stock stock;
}

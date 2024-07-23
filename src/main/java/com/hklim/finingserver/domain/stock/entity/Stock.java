package com.hklim.finingserver.domain.stock.entity;

import com.hklim.finingserver.domain.news.entity.StockNews;
import com.hklim.finingserver.domain.portfolio.entity.Portfolio;
import com.hklim.finingserver.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock extends BaseEntity {
    private String symbol;
    private String name;
    private String sector;
    private String country;
    private String industry;
    private String ipoYear;

    @OneToMany(mappedBy = "stock")
    private final List<StockIndex> stockIndices = new ArrayList<>();

    @OneToMany(mappedBy = "stock")
    private final List<Portfolio> portfolios = new ArrayList<>();

    @OneToMany(mappedBy = "stock")
    private final List<StockNews> stockNewsList = new ArrayList<>();
}

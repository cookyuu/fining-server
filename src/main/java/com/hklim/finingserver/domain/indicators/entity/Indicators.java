package com.hklim.finingserver.domain.indicators.entity;

import com.hklim.finingserver.domain.news.entity.IndicatorsNews;
import com.hklim.finingserver.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Indicators extends BaseEntity {
    @NotNull
    private String name;
    @NotNull
    private String symbol;
    @NotNull
    @Enumerated(EnumType.STRING)
    private IndicatorsType indicatorsType;

    @OneToMany(mappedBy = "indicators")
    private final List<IndicatorsNews> indicatorsNewsList = new ArrayList<>();
    @OneToMany(mappedBy = "indicators")
    private final List<IndicatorsIndex> indicatorsIndexList = new ArrayList<>();
}

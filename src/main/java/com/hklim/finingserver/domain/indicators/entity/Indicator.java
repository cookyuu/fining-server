package com.hklim.finingserver.domain.indicators.entity;

import com.hklim.finingserver.domain.news.entity.IndicatorNews;
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
public class Indicator extends BaseEntity {
    @NotNull
    private String name;
    @NotNull
    private String symbol;
    @NotNull
    @Enumerated(EnumType.STRING)
    private IndicatorType indicatorType;

    @OneToMany(mappedBy = "indicator")
    private final List<IndicatorNews> indicatorNewsList = new ArrayList<>();
    @OneToMany(mappedBy = "indicator")
    private final List<IndicatorIndex> indicatorIndexList = new ArrayList<>();
}

package com.hklim.finingserver.domain.indicators.entity;

import com.hklim.finingserver.global.entity.BaseEntity;
import jakarta.persistence.*;
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
public class IndicatorsIndex extends BaseEntity {
    private String netChange;
    private String percentChange;
    private String price;
    private LocalDate asOfDate;
    @Enumerated(EnumType.STRING)
    private IndicatorsType indicatorsType;

    @ManyToOne
    @JoinColumn(name = "indicators_id")
    private Indicators indicators;
}

package com.hklim.finingserver.domain.indicators.entity;

import com.hklim.finingserver.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class IndicatorIndex extends BaseEntity {
    private String netChange;
    private String percentChange;
    private String price;
    private LocalDate asOfDate;

    @ManyToOne
    @JoinColumn(name = "indicator_id")
    private Indicator indicator;
}

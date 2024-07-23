package com.hklim.finingserver.domain.news.entity;

import com.hklim.finingserver.domain.indicators.entity.Indicators;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class IndicatorsNews extends News{
    @ManyToOne
    @JoinColumn(name = "indicators_id")
    private Indicators indicators;

    public IndicatorsNews(String title, String contents, String writtenTime, String keyword, Indicators indicators) {
        super(title, contents, writtenTime, keyword);
        this.indicators = indicators;
    }
}

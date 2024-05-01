package com.hklim.finingserver.domain.news.entity;

import com.hklim.finingserver.domain.indicators.entity.Indicator;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class IndicatorNews extends News{
    @ManyToOne
    @JoinColumn(name = "indicator_id")
    private Indicator indicator;

    public IndicatorNews(String title, String contents, String writtenTime, String keyword, Indicator indicator) {
        super(title, contents, writtenTime, keyword);
        this.indicator = indicator;
    }
}

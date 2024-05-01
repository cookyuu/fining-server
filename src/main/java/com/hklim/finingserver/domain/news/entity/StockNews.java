package com.hklim.finingserver.domain.news.entity;

import com.hklim.finingserver.domain.stock.entity.Stock;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class StockNews extends News {
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    public StockNews(String title, String contents, String writtenTime, String keyword, Stock stock) {
        super(title, contents, writtenTime, keyword);
        this.stock = stock;
    }
}

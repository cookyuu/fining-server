package com.hklim.finingserver.domain.stock.repository;

import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.domain.stock.entity.StockIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StockIndexRepository extends JpaRepository<StockIndex, Long> {

    boolean existsByAsOfDate(LocalDate asOfDate);

    StockIndex findByStockAndAsOfDate(Stock stock, LocalDate today);

    List<StockIndex> findTop10ByAsOfDateOrderByMarketCapDesc(LocalDate now);

    List<StockIndex> findAllByStockOrderByAsOfDateDesc(Stock stock);
}

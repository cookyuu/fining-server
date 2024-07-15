package com.hklim.finingserver.domain.stock.repository;

import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.domain.stock.entity.StockIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findBySymbol(String symbol);

    List<Stock> findByNameContainingOrSymbolContaining(String keyword, String keyword1);
}

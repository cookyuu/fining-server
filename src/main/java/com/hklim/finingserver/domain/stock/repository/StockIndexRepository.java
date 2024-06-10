package com.hklim.finingserver.domain.stock.repository;

import com.hklim.finingserver.domain.stock.entity.StockIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface StockIndexRepository extends JpaRepository<StockIndex, Long> {
    boolean existsByLastSaleDate(LocalDate lastPriceDate);
}

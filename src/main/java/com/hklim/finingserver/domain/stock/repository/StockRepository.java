package com.hklim.finingserver.domain.stock.repository;

import com.hklim.finingserver.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}

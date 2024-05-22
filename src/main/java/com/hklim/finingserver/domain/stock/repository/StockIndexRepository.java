package com.hklim.finingserver.domain.stock.repository;

import com.hklim.finingserver.domain.stock.entity.StockIndex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockIndexRepository extends JpaRepository<StockIndex, Long> {
}

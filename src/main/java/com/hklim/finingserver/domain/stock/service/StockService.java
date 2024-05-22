package com.hklim.finingserver.domain.stock.service;

import com.hklim.finingserver.domain.stock.dto.InsertStockDataRequestDto;
import com.hklim.finingserver.domain.stock.dto.StockDataFromCSVDto;
import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.domain.stock.entity.StockIndex;
import com.hklim.finingserver.domain.stock.repository.StockIndexRepository;
import com.hklim.finingserver.domain.stock.repository.StockRepository;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.utils.FileUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {
    private final FileUtils fileUtils;
    private final StockRepository stockRepository;
    private final StockIndexRepository stockIndexRepository;

    @Transactional
    public void insertStockDataFromCSV(InsertStockDataRequestDto insertStockDataInfo) {
        String fileFullPath = Paths.get(insertStockDataInfo.getFilePath(), insertStockDataInfo.getFileName()).toString();
        List<StockDataFromCSVDto> stockDataList = fileUtils.toDtoFromCSVFile(fileFullPath);
        if (stockDataList.isEmpty()) {
            log.info("Extract Stock Data Count is Zero. ");
        } else {
            log.info("[INSERT STOCK DATA PROCESS] START. ");
            for (StockDataFromCSVDto stockData : stockDataList) {
                Stock stock = stockRepository.save(stockData.toStockEntity());
                stockIndexRepository.save(stockData.toStockIndexEntity(stock));
            }
            log.info("[INSERT STOCK DATA PROCESS] END. Insert Data Count : {}", stockDataList.size());
        }
    }
}

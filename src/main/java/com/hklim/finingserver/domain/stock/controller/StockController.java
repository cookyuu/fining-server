package com.hklim.finingserver.domain.stock.controller;

import com.hklim.finingserver.domain.stock.dto.InsertStockDataRequestDto;
import com.hklim.finingserver.domain.stock.service.StockService;
import com.hklim.finingserver.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    // 파일로 주식정보를 입력하는 api
    @PostMapping("/renew")
    public ResponseEntity<ResponseDto<String>> insertStockDataFromCSV(@RequestBody InsertStockDataRequestDto insertStockDataInfo) {
        stockService.insertStockDataFromCSV(insertStockDataInfo);
        return ResponseDto.ok("성공");
    }
}

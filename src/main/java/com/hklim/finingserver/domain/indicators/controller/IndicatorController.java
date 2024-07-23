package com.hklim.finingserver.domain.indicators.controller;

import com.hklim.finingserver.domain.indicators.service.BondIndicatorsService;
import com.hklim.finingserver.domain.indicators.service.StockIndicatorsService;
import com.hklim.finingserver.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/indicator")
public class IndicatorController {
    private final BondIndicatorsService bondIndicatorsService;
    private final StockIndicatorsService stockIndicatorService;
    @PostMapping("/scrap/bond")
    public ResponseEntity<ResponseDto<String>> scrapBondIndicatorsData() {
        bondIndicatorsService.insertData();
        return ResponseDto.ok("채권 지수 데이터 크롤링 성공!");
    }

    @PostMapping("/scrap/stock")
    public ResponseEntity<ResponseDto<String>> scrapStockIndicatorsData() {
        stockIndicatorService.insertData();
        return ResponseDto.ok("주가 지수 데이터 크롤링 성공!");
    }
}

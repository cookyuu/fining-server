package com.hklim.finingserver.domain.ui.controller;

import com.hklim.finingserver.domain.ui.dto.IndicatorsDetailUiDataResponseDto;
import com.hklim.finingserver.domain.ui.dto.MainUiDataResponseDto;
import com.hklim.finingserver.domain.ui.dto.MyProfileUiDataResponseDto;
import com.hklim.finingserver.domain.ui.dto.StockDetailUiDataResponseDto;
import com.hklim.finingserver.domain.ui.service.UiService;
import com.hklim.finingserver.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui")
public class UiController {
    private final UiService uiService;
    @GetMapping("/main")
    public ResponseEntity<ResponseDto<MainUiDataResponseDto>> getMainUiData(@AuthenticationPrincipal UserDetails user) {
        return ResponseDto.ok(uiService.getMainUiData(user));
    }

    @GetMapping("/stock/{symbol}")
    public ResponseEntity<ResponseDto<StockDetailUiDataResponseDto>> getStockDetailUiData(@PathVariable(value = "symbol") String symbol) {
        return ResponseDto.ok(uiService.getStockDetailData(symbol));
    }

    @GetMapping("/indicators/{symbol}")
    public ResponseEntity<ResponseDto<IndicatorsDetailUiDataResponseDto>> getIndicatorsDetailUiData(@PathVariable(value = "symbol") String symbol) {
        return ResponseDto.ok(uiService.getIndicatorDetailData(symbol));
    }
    @GetMapping("/my/profile")
    public ResponseEntity<ResponseDto<MyProfileUiDataResponseDto>> getMyProfileUiData(@AuthenticationPrincipal UserDetails user) {
        return ResponseDto.ok(uiService.getMyProfileUiData(user));
    }


}

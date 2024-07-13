package com.hklim.finingserver.domain.ui.controller;

import com.hklim.finingserver.domain.ui.dto.*;
import com.hklim.finingserver.domain.ui.service.UiService;
import com.hklim.finingserver.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/my/portfolio")
    public ResponseEntity<ResponseDto<MyPortfolioUiDataResponseDto>> getMyPortfolioUiData(@AuthenticationPrincipal UserDetails user, @RequestParam("pageNum") int pageNum) {
        return ResponseDto.ok(uiService.getMyPortfolioUiData(user, pageNum));
    }


}

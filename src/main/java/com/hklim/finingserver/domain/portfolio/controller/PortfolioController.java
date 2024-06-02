package com.hklim.finingserver.domain.portfolio.controller;

import com.hklim.finingserver.domain.auth.dto.JwtUserInfo;
import com.hklim.finingserver.domain.portfolio.dto.AddPortfolioDto;
import com.hklim.finingserver.domain.portfolio.entity.Portfolio;
import com.hklim.finingserver.domain.portfolio.service.PortfolioService;
import com.hklim.finingserver.domain.stock.service.StockService;
import com.hklim.finingserver.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {
    private final StockService stockService;
    private final PortfolioService portfolioService;

    @PostMapping("")
    public ResponseEntity<ResponseDto<String>> addPortfolio(@AuthenticationPrincipal UserDetails user, @RequestBody AddPortfolioDto.Request addPortfolioData ) {
        portfolioService.addPortfolio(user.getUsername(), addPortfolioData);
        return ResponseDto.ok("포트폴리오 등록 성공!");
    }
}

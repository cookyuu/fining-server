package com.hklim.finingserver.domain.portfolio.service;

import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.member.service.MemberService;
import com.hklim.finingserver.domain.portfolio.entity.Portfolio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
class PortfolioServiceTest {
    Logger log = LogManager.getLogger(PortfolioServiceTest.class);
    @Autowired
    PortfolioService portfolioService;
    @Autowired
    MemberService memberService;

    @Test
    void getPortfolioStocksPagination() {
        Member member = memberService.findMemberById(3L);
        Page<Portfolio> portfolios = portfolioService.getPortfolioStocksPagination(member, 2);

        log.info("[PORTFOLIO-DATA-CNT] cnt : {}",portfolios.getSize());
        portfolios.forEach(portfolio -> {
            log.info("[PORTFOLIO-STOCK-DATA] Symbol : {}, Name : {}", portfolio.getStock().getSymbol(), portfolio.getStock().getName(), portfolio.getStock());
        });
    }
}
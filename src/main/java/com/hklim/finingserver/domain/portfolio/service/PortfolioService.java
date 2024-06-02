package com.hklim.finingserver.domain.portfolio.service;

import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.member.repository.MemberRepository;
import com.hklim.finingserver.domain.portfolio.dto.AddPortfolioDto;
import com.hklim.finingserver.domain.portfolio.entity.Portfolio;
import com.hklim.finingserver.domain.portfolio.repository.PortfolioRepository;
import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.domain.stock.repository.StockRepository;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final MemberRepository memberRepository;

    public void addPortfolio(String username, AddPortfolioDto.Request addPortfolioData) {
        Stock stock = stockRepository.findBySymbol(addPortfolioData.getStockSymbol()).orElseThrow(() ->
                new ApplicationErrorException(ApplicationErrorType.NO_EXIST_STOCK)
            );
        Member member = memberRepository.findById(Long.parseLong(username)).orElseThrow(() ->
                new ApplicationErrorException(ApplicationErrorType.NO_EXIST_MEMBER)
            );

        if (portfolioRepository.existsByMemberAndStock(member, stock)){
            log.info("[ADD-PORTFOLIO] Already registered in the portfolio. ");
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_TO_SAVE_DATA, "이미 포트폴리오에 등록된 주식입니다.");
        }
        log.info("[ADD-PORTFOLIO] Insert Portfolio. memeber : {}, stock : {}", member.getEmail(), stock.getSymbol());
        portfolioRepository.save(new Portfolio(member, stock));
    }
}

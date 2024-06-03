package com.hklim.finingserver.domain.portfolio.service;

import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.member.repository.MemberRepository;
import com.hklim.finingserver.domain.portfolio.dto.AddPortfolioDto;
import com.hklim.finingserver.domain.portfolio.dto.CancelPortfolioDto;
import com.hklim.finingserver.domain.portfolio.entity.Portfolio;
import com.hklim.finingserver.domain.portfolio.repository.PortfolioRepository;
import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.domain.stock.repository.StockRepository;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final MemberRepository memberRepository;

    public void addPortfolio(String username, AddPortfolioDto.Request addPortfolioData) {
        Stock stock = stockRepository.findBySymbol(addPortfolioData.getStockSymbol());
        if (stock == null) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_STOCK);
        }

        Member member = memberRepository.findById(Long.parseLong(username)).orElseThrow(() ->
                new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_MEMBER));

        if (portfolioRepository.existsByMemberAndStock(member, stock)){
            log.info("[ADD-PORTFOLIO] Already registered in the portfolio. ");
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_TO_SAVE_DATA, "This Stock is already registered in Portfolio.");
        }
        log.info("[ADD-PORTFOLIO] Insert Portfolio. member : {}, stock : {}", member.getEmail(), stock.getSymbol());
        portfolioRepository.save(new Portfolio(member, stock));
    }

    public void cancelPortfolio(String username, CancelPortfolioDto.Request cancelPortfolioData) {
        Stock stock = stockRepository.findBySymbol(cancelPortfolioData.getStockSymbol());
        if (stock == null) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_STOCK);
        }
        Member member = memberRepository.findById(Long.parseLong(username)).orElseThrow(() ->
                new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_MEMBER));
        Portfolio portfolio = portfolioRepository.findByMemberAndStock(member,stock).orElseThrow(() ->
                new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_PORTFOLIO,  "Fail to cancel Portfolio registration."));


        try {
            log.info("[CANCEL-PORTFOLIO] Cancel Portfolio registration. member : {}, stock : {}", member.getEmail(), stock.getSymbol());
            portfolioRepository.delete(portfolio);
        } catch (Exception e) {
            log.info("[CANCEL-PORTFOLIO] Fail to cancel Portfolio registration. Error message : {}", e.getMessage());
        }
    }
}

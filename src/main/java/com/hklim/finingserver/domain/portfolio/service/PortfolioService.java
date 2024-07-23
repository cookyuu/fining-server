package com.hklim.finingserver.domain.portfolio.service;

import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.member.repository.MemberRepository;
import com.hklim.finingserver.domain.member.service.MemberService;
import com.hklim.finingserver.domain.portfolio.dto.AddPortfolioDto;
import com.hklim.finingserver.domain.portfolio.dto.CancelPortfolioDto;
import com.hklim.finingserver.domain.portfolio.entity.Portfolio;
import com.hklim.finingserver.domain.portfolio.repository.PortfolioRepository;
import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.domain.stock.entity.StockIndex;
import com.hklim.finingserver.domain.stock.repository.StockIndexRepository;
import com.hklim.finingserver.domain.stock.repository.StockRepository;
import com.hklim.finingserver.domain.stock.service.StockService;
import com.hklim.finingserver.domain.ui.dto.MainUiDataResponseDto;
import com.hklim.finingserver.domain.ui.dto.UiStockDataResponseDto;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final MemberService memberService;
    private final StockService stockService;

    public void addPortfolio(String username, AddPortfolioDto.Request addPortfolioData) {
        Stock stock = stockService.findBySymbol(addPortfolioData.getStockSymbol());
        if (stock == null) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_STOCK);
        }
        Member member = memberService.findMemberById(Long.parseLong(username));

        if (portfolioRepository.existsByMemberAndStock(member, stock)){
            log.info("[ADD-PORTFOLIO] Already registered in the portfolio. ");
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_TO_SAVE_DATA, "[ADD-PORTFOLIO] This Stock is already registered in Portfolio. Symbol : " + stock.getSymbol());
        }
        log.info("[ADD-PORTFOLIO] Insert Portfolio. member : {}, stock : {}", member.getEmail(), stock.getSymbol());
        portfolioRepository.save(new Portfolio(member, stock));
    }

    public void cancelPortfolio(String username, CancelPortfolioDto.Request cancelPortfolioData) {
        Stock stock = stockService.findBySymbol(cancelPortfolioData.getStockSymbol());
        if (stock == null) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_STOCK);
        }
        Member member = memberService.findMemberById(Long.parseLong(username));
        Portfolio portfolio = portfolioRepository.findByMemberAndStock(member,stock).orElseThrow(() ->
                new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_PORTFOLIO,  "[ADD-PORTFOLIO] Fail to cancel Portfolio registration. stock is not found in Portfolio. Symbol : " + stock.getSymbol()));

        try {
            log.info("[CANCEL-PORTFOLIO] Cancel Portfolio registration. member : {}, stock : {}", member.getEmail(), stock.getSymbol());
            portfolioRepository.delete(portfolio);
        } catch (Exception e) {
            log.info("[CANCEL-PORTFOLIO] Fail to cancel Portfolio registration. Error message : {}", e.getMessage());
        }
    }

    public List<UiStockDataResponseDto> getPortfolioStocks(String username) {
        List<UiStockDataResponseDto> portfolioDataList = new ArrayList<>();
        Member member = memberService.findMemberById(Long.parseLong(username));

        log.info("[FIND-PORTFOLIO-INFO] Find personal portfolio data.");
        List<Portfolio> portfolios = findAllByMember(member);
        portfolios.forEach(portfolio ->
                portfolioDataList.add(stockService.getPortfolioStockData(portfolio))
                );
        return portfolioDataList;
    }

    public List<Portfolio> findAllByMember(Member member) {
        return portfolioRepository.findAllByMember(member);
    }

    public Page<Portfolio> getPortfolioStocksPagination(Member member, int pageNum) {
        log.info("[FIND-PORTFOLIO-INFO] Find personal pagination portfolio data.");
        PageRequest pageRequest = PageRequest.of(pageNum-1, 10);
        Page<Portfolio> portfolios = portfolioRepository.findAllByMember(member, pageRequest);
        return portfolios;
    }

    public void withdrawalMember(Member member) {
        portfolioRepository.deleteAllByMember(member);
    }
}

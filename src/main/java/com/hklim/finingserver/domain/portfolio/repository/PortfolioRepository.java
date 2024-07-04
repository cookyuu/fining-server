package com.hklim.finingserver.domain.portfolio.repository;

import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.portfolio.entity.Portfolio;
import com.hklim.finingserver.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    boolean existsByMemberAndStock(Member member, Stock stock);

    Optional<Portfolio> findByMemberAndStock(Member member, Stock stock);

    List<Portfolio> findAllByMember(Member member);
}

package com.hklim.finingserver.domain.portfolio.entity;

import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.stock.entity.Stock;
import com.hklim.finingserver.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio extends BaseEntity {

    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne()
    @JoinColumn(name = "stock_id")
    private Stock stock;

}

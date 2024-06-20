package com.hklim.finingserver.domain.member.entity;

import com.hklim.finingserver.domain.portfolio.entity.Portfolio;
import com.hklim.finingserver.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private boolean isDeleted;
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "member")
    private final List<Portfolio> portfolios = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = RoleType.USER;
    }

    public void updateTempPw(String tempPw) {
        this.password = tempPw;
    }
}

package com.hklim.finingserver.domain.member.entity;

import com.hklim.finingserver.domain.portfolio.entity.Portfolio;
import com.hklim.finingserver.global.entity.BaseEntity;
import com.hklim.finingserver.global.entity.RoleType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private boolean isDeleted;
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "member")
    private final List<Portfolio> portfolios = new ArrayList<>();
}

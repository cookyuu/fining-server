package com.hklim.finingserver.global.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKeyType {
    AUTH_EMAIL("auth:email:")
    ,LOGOUT_TOKEN("logout:token:");
    String separator;
}

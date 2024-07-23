package com.hklim.finingserver.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PageInfo {
    private int totalPages;
    private long totalElements;
    private int pageNum;
    private long offset;
    private long pageElementCnt;
}

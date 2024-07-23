package com.hklim.finingserver.global.utils;

import com.hklim.finingserver.global.dto.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommonUtils {
    public <T> PageInfo convertToPageInfo(Page<T> pagingData) {
        return PageInfo.builder()
                .totalPages(pagingData.getTotalPages())
                .totalElements(pagingData.getTotalElements())
                .offset(pagingData.getPageable().getOffset())
                .pageNum(pagingData.getPageable().getPageNumber()+1)
                .pageElementCnt(pagingData.getPageable().getPageSize())
                .build();
    }
}

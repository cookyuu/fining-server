package com.hklim.finingserver.domain.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsertStockDataRequestDto {
    private String filePath;
    private String fileName;
}

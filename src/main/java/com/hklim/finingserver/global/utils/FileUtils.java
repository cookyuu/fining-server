package com.hklim.finingserver.global.utils;

import com.hklim.finingserver.domain.stock.dto.StockDataFromCSVDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Slf4j
@Component
public class FileUtils {
    public List<StockDataFromCSVDto> toDtoFromCSVFile(String fileFullPath) {
        try {
            log.info("[EXTRACT STOCK DATA PROCESS] START. ");
            Reader reader = new BufferedReader(new FileReader(fileFullPath));

            CsvToBean<StockDataFromCSVDto> csvReader = new CsvToBeanBuilder(reader)
                    .withType(StockDataFromCSVDto.class)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();

            List<StockDataFromCSVDto> stockDataList = csvReader.parse();
            log.info("[EXTRACT STOCK DATA PROCESS] END. Extract Data Count : {}", stockDataList.size());
            return stockDataList;
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }
}

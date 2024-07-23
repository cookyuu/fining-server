package com.hklim.finingserver.global.batch;

import com.hklim.finingserver.domain.indicators.service.StockIndicatorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StockIndicatorsCrawlingTasklet implements Tasklet {
    private final StockIndicatorsService stockIndicatorsService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("[BATCH-STOCK-INDICATORS-CRAWLING] Process Start. ");
        stockIndicatorsService.insertData();
        return RepeatStatus.FINISHED;
    }
}

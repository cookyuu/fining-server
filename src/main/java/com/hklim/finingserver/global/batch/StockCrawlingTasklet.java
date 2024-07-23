package com.hklim.finingserver.global.batch;

import com.hklim.finingserver.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockCrawlingTasklet implements Tasklet {
    private final StockService stockService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("[BATCH-STOCK-CRAWLING] Process Start. ");
        stockService.insertTotalData();
        return RepeatStatus.FINISHED;
    }
}

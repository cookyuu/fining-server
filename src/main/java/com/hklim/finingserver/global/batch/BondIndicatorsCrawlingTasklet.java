package com.hklim.finingserver.global.batch;

import com.hklim.finingserver.domain.indicators.service.BondIndicatorsService;
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
public class BondIndicatorsCrawlingTasklet implements Tasklet {
    private final BondIndicatorsService bondIndicatorsService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("[BATCH-BOND-INDICATORS-CRAWLING] Process Start. ");
        bondIndicatorsService.insertData();
        return RepeatStatus.FINISHED;
    }
}

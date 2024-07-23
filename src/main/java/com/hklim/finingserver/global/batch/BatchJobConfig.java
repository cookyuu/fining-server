package com.hklim.finingserver.global.batch;

import com.hklim.finingserver.domain.indicators.service.BondIndicatorsService;
import com.hklim.finingserver.domain.indicators.service.StockIndicatorsService;
import com.hklim.finingserver.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchJobConfig {
    private final StockService stockService;
    private final StockIndicatorsService stockIndicatorsService;
    private final BondIndicatorsService bondIndicatorsService;

    @Bean(name = "stockCrawlingJob")
    public Job stockCrawlingJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("stockCrawlingJob", jobRepository)
                .start(stockCrawlingStep(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean(name = "indicatorsCrawlingJob")
    public Job indicatorsCrawlingJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("indicatorsCrawlingJob", jobRepository)
                .start(stockIndicatorsCrawlingStep(jobRepository, platformTransactionManager))
                .on("*")
                .to(bondIndicatorsCrawlingStep(jobRepository, platformTransactionManager))
                .end()
                .build();
    }

    @Bean
    public Step stockCrawlingStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("stockCrawlingStep", jobRepository)
                .tasklet(new StockCrawlingTasklet(stockService), platformTransactionManager)
                .build();
    }

    @Bean
    public Step stockIndicatorsCrawlingStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("stockIndicatorsCrawlingStep", jobRepository)
                .tasklet(new StockIndicatorsCrawlingTasklet(stockIndicatorsService), platformTransactionManager)
                .build();
    }

    @Bean
    public Step bondIndicatorsCrawlingStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("bondIndicatorsCrawlingStep", jobRepository)
                .tasklet(new BondIndicatorsCrawlingTasklet(bondIndicatorsService), platformTransactionManager)
                .build();
    }
}

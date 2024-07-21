package com.hklim.finingserver.global.batch;

import com.hklim.finingserver.domain.indicators.service.BondIndicatorsService;
import com.hklim.finingserver.domain.indicators.service.StockIndicatorsService;
import com.hklim.finingserver.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchJobConfig_bak {
    /*
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("crawlingJob", jobRepository)
                .start(stockCrawlingStep(jobRepository, platformTransactionManager))
                .next(stockIndicatorsCrawlingStep(jobRepository, platformTransactionManager))
                .next(bondIndicatorsCrawlingStep(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step stockCrawlingStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {

        return stepBuilderFactory.get("stockCrawlingStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                        return RepeatStatus.FINISHED;
                    }
                })
                .build();

    }

    @Bean
    public Step stockIndicatorsCrawlingStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return stepBuilderFactory.get("stockIndicatorCrawlingStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step bondIndicatorsCrawlingStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {

        return stepBuilderFactory.get("bondIndicatorCrawlingStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
     */
}

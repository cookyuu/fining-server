package com.hklim.finingserver.global.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final BatchJobConfig batchJobConfig;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Scheduled(cron = "0 01 06 * * ?")
    public void stockCrawlingJob() {
        try {
            log.info("[BATCH-SCHEDULE] Stock crawling start, date time : {}", LocalDateTime.now());
            jobLauncher.run(
                    batchJobConfig.stockCrawlingJob(jobRepository, platformTransactionManager),
                        new JobParametersBuilder().addString("dateTime", LocalDateTime.now().toString()).toJobParameters()
            );

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(cron = "0 01 06 * * ?")
    public void indicatorsCrawlingJob() {
        try {
            log.info("[BATCH-SCHEDULE] Indicators crawling start, date time : {}", LocalDateTime.now());
            jobLauncher.run(
                    batchJobConfig.indicatorsCrawlingJob(jobRepository, platformTransactionManager),
                    new JobParametersBuilder().addString("dateTime", LocalDateTime.now().toString()).toJobParameters()
            );

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

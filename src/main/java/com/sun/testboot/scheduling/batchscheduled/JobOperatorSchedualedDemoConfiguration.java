package com.sun.testboot.scheduling.batchscheduled;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobOperatorSchedualedDemoConfiguration implements JobExecutionListener {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobOperator jobOperator;

    private Map<String, JobParameter> parameters = new HashMap<>();
    @Autowired
    @Qualifier("myJobIncrementer")
    private JobParametersIncrementer myJobIncrementer;

    @Scheduled(fixedDelay = 5000)
    public void schedual() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, JobParametersNotFoundException, NoSuchJobException {
        jobOperator.startNextInstance("jobOperatorSchedualedDemoJob");
    }

    @Bean
    public Job jobOperatorSchedualedDemoJob(){
        return jobBuilderFactory.get("jobOperatorSchedualedDemoJob")
                .start(jobOperatorSchedualedDemoStep())
                .incrementer(myJobIncrementer)
                .listener(this)
                .build();
    }

    @Bean
    public Step jobOperatorSchedualedDemoStep() {
        return stepBuilderFactory.get("jobOperatorSchedualedDemoStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("job operator schedualed started with param:"+parameters.get("jobschedualedparam").getValue());
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        parameters = jobExecution.getJobParameters().getParameters();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }

}

package com.sun.testboot.batch.jobLancher;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobLauncherDemoConfiguration implements JobExecutionListener {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> parameters = new HashMap<>();

    @Bean
    public Job jobLauncherDemoJob(){
        return jobBuilderFactory.get("jobLauncherDemoJob")
                .start(jobLauncherDemoStep())
                .listener(this)
                .build();
    }

    @Bean
    public Step jobLauncherDemoStep() {
        return stepBuilderFactory.get("jobLauncherDemoStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("job launcher started with param:"+parameters.get("jobparam"));
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

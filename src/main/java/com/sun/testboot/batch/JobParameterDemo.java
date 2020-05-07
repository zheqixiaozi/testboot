package com.sun.testboot.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class JobParameterDemo implements StepExecutionListener {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    private Map<String, JobParameter> params;

    @Bean
    public Job myParameterJob(){
        return jobBuilderFactory.get("myParameterJob")
                .start(myParameterStep())
                .build();
    }

    @Bean
    public Step myParameterStep() {
        return stepBuilderFactory.get("myParameterStep")
                .listener(this)
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("Parameter is "+params.get("info"));
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        params = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}

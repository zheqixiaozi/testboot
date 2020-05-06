package com.sun.testboot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * 并行step
 */
@Configuration
public class JobSplitDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobSplit(){
        return jobBuilderFactory.get("jobSplit")
                .start(jobSplitFlow1())
                .split(new SimpleAsyncTaskExecutor()).add(jobSplitFlow2())
                .end().build();
    }

    @Bean
    public Flow jobSplitFlow1(){
        return new FlowBuilder<Flow>("jobSplitFlow1")
                .start(stepBuilderFactory.get("jobSplitStep1").tasklet(tasklet()).build()).build();
    }
    @Bean
    public Flow jobSplitFlow2(){
        return new FlowBuilder<Flow>("jobSplitFlow2")
                .start(stepBuilderFactory.get("jobSplitStep2").tasklet(tasklet()).build())
                .next(stepBuilderFactory.get("jobSplitStep3").tasklet(tasklet()).build())
                .build();
    }

    private Tasklet tasklet() {
        return new PrintTasklet();
    }

    private class PrintTasklet implements Tasklet {
        @Override
        public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
            System.out.println(String.format("%s has been executed on thread %s",
                    chunkContext.getStepContext().getStepName(),Thread.currentThread().getName()));
            return RepeatStatus.FINISHED;
        }
    }
}

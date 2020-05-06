package com.sun.testboot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.jsr.step.DecisionStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 决策器
 */
@Configuration
public class JobDecisionDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step firstStep(){
        return stepBuilderFactory.get("firstStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("firstStep");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    /**
     * 偶数走此step
     * @return
     */
    @Bean
    public Step evenStep(){
        return stepBuilderFactory.get("evenStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("evenStep");
                    return RepeatStatus.FINISHED;
                })).build();
    }
    /**
     * 奇数走此step
     * @return
     */
    @Bean
    public Step oddStep(){
        return stepBuilderFactory.get("oddStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("oddStep");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public JobExecutionDecider myDecider(){
        return new MyDecider();
    }

    private class MyDecider implements JobExecutionDecider {
        private int count = 0;
        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            count++;
            if (count % 2 == 0){
                return new FlowExecutionStatus("EVEN");
            } else {
                return new FlowExecutionStatus("ODD");
            }
        }
    }
    @Bean
    public Job decisionJob(){
        return jobBuilderFactory.get("decisionJob")
                .start(firstStep())
                .next(myDecider())
                .from(myDecider()).on("EVEN").to(evenStep())
                .from(myDecider()).on("ODD").to(oddStep())
                .from(oddStep()).on("*").to(myDecider())
                .end().build();
    }
}

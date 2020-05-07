package com.sun.testboot.batch.nestjobDemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 父job,两个子job嵌套进这个父job,并由父job启动执行（在yml文件中指定了入口）
 * 子job不能单独执行
 */
@Configuration
public class EODJobConfigeration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private Job childJob1;
    @Autowired
    private Job childJob2;
    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    public Job EODJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return jobBuilderFactory.get("EODJob")
                .start(childJob1(jobRepository,platformTransactionManager))
                .next(childJob2(jobRepository,platformTransactionManager))
                .build();
    }

    public Step childJob1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob1")) //类似装饰器模式bufferReader
                .job(childJob1)
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    public Step childJob2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob2")) //类似装饰器模式bufferReader
                .job(childJob2)
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

}

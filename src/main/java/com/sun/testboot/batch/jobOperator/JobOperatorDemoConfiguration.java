package com.sun.testboot.batch.jobOperator;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobOperatorDemoConfiguration implements JobExecutionListener, ApplicationContextAware {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> parameters = new HashMap<>();
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    private JobRegistry jobRegistry;
    private ApplicationContext context;

    /**
     * 在controller中根据bean的名字获取到具体的Job的bean
     * @return
     * @throws Exception
     */
    @Bean
    public JobRegistryBeanPostProcessor jobProcessor() throws Exception {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        postProcessor.setBeanFactory(context.getAutowireCapableBeanFactory());
        postProcessor.afterPropertiesSet();
        return postProcessor;
    }

    /**
     * springbatch没有提供构造器，需要我们自己创建JobOperator的bean
     * @return
     */
    @Bean
    public JobOperator jobOperator(){
        SimpleJobOperator operator = new SimpleJobOperator();
        operator.setJobLauncher(jobLauncher);
        operator.setJobParametersConverter(new DefaultJobParametersConverter());//参数解析，默认是key=value的形式
        operator.setJobRepository(jobRepository);
        operator.setJobExplorer(jobExplorer);
        operator.setJobRegistry(jobRegistry);
        return operator;
    }

    @Bean
    public Job jobOperatorDemoJob(){
        return jobBuilderFactory.get("jobOperatorDemoJob")
                .start(jobOperatorDemoStep())
                .listener(this)
                .build();
    }

    @Bean
    public Step jobOperatorDemoStep() {
        return stepBuilderFactory.get("jobOperatorDemoStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("job operator started with param:"+parameters.get("job2param"));
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

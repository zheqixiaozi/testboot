package com.sun.testboot.batch.jobLancher;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job")
public class JobLauncherController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("jobLauncherDemoJob")
    private Job jobLauncherDemoJob;

    @GetMapping("/{jobparam}")
    public String runJob1(@PathVariable("jobparam") String jobparam) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        System.out.println("Request run job with param:"+jobparam);
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobparam",jobparam).toJobParameters();
        jobLauncher.run(jobLauncherDemoJob,jobParameters);
        return "job success";
    }
}

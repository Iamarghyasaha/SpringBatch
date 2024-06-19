package com.ndb.springBatch.listner;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListner implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before Job Instance name: "+jobExecution.getJobInstance().getJobName());
        System.out.println("Before Job Parameter: "+jobExecution.getJobParameters());
        System.out.println("Before Job Execution Context: "+jobExecution.getExecutionContext());
        // putting dummy value inside the JobExecution context
        jobExecution.getExecutionContext().put("JobExecutionContext: key","JobExecutionContext: value");

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("After Job Instance name: "+jobExecution.getJobInstance().getJobName());
        System.out.println("After Job Parameter: "+jobExecution.getJobParameters());
        System.out.println("After Job Execution Context: "+jobExecution.getExecutionContext());
    }
}

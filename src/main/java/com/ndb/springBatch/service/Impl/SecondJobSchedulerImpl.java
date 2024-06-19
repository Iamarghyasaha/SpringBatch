package com.ndb.springBatch.service.Impl;

import com.ndb.springBatch.service.SecondJobScheduler;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecondJobSchedulerImpl implements SecondJobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("job2")
    private Job secondJob;

    //corn says when you want to execute this scheduler or when to run
    // exp from http://www.cronmaker.com/
    // launch in every 1 min
    @Override
    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void secondJobStarter(){
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime",new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters =new JobParameters(params);
        try{
            // using jobexecution to pring the job execution ID
            JobExecution jobExecution = jobLauncher.run(secondJob,jobParameters);
            System.out.println("Job Execution ID :"+jobExecution.getId());
        }catch (Exception e){
            System.out.println("Exception :"+e.getMessage());
        }
    }
}

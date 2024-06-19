package com.ndb.springBatch.service.Impl;

import com.ndb.springBatch.entity.JobParamRequest;
import com.ndb.springBatch.service.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobLauncher jobLauncher;

    @Qualifier("job1")
    @Autowired
    private Job firstJob;

    @Qualifier("job2")
    @Autowired
    private Job secondJob;


// Now this Job is running in different thread not in the main thread
    @Async
    @Override
    public void startJob(String jobName, List<JobParamRequest> jobParamRequestList) {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime",new JobParameter(System.currentTimeMillis()));
        jobParamRequestList.stream().forEach(jobParamReq->{
                params.put(jobParamReq.getParamKey(), new JobParameter(jobParamReq.getParamValue()));
        });
        JobParameters jobParameters = new JobParameters(params);
        try{
            if(jobName.equals("First Job")){
                jobLauncher.run(firstJob,jobParameters);
            } else if (jobName.equals("Second Job")) {
                jobLauncher.run(secondJob,jobParameters);
            }
        }catch (Exception e){
            System.out.println("Exception :"+e.getMessage());
        }

    }
}

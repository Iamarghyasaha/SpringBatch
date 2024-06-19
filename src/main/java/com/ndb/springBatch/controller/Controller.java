package com.ndb.springBatch.controller;

import com.ndb.springBatch.entity.JobParamRequest;
import com.ndb.springBatch.service.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.batch.operations.JobOperator;
import java.util.List;


@RestController
@RequestMapping("/api/job")
public class Controller {

  @Autowired
  private JobService jobService;

  // to stop job we need to inject JobOperator
  @Autowired
  private JobOperator jobOperator;

    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName, @RequestBody List<JobParamRequest> jobParamRequestList){
        jobService.startJob(jobName,jobParamRequestList);
        return "Job Strated....";
    }

    @GetMapping("/stop/{jobExecutionId}")
    public String stopJob(@PathVariable long jobExecutionId){
        try{
            jobOperator.stop(jobExecutionId);
        }catch (Exception e){
           e.printStackTrace();
        }
        return "Job Stopped....";
    }

}

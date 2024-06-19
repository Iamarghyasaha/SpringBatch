package com.ndb.springBatch.service;

import com.ndb.springBatch.entity.JobParamRequest;

import java.util.List;

public interface JobService {
    void startJob(String jobName, List<JobParamRequest> jobParamRequestList);
}

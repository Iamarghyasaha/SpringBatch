package com.ndb.springBatch.listner;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListner implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Before Step StepName: "+stepExecution.getStepName());
        System.out.println("Job Execution Context: "+stepExecution.getJobExecution().getExecutionContext());
        System.out.println("Before Step Execution Context: "+stepExecution.getExecutionContext());

        // putting dummy value inside the stepExecution context
        stepExecution.getExecutionContext().put("StepExecutionContext: key","StepExecutionContext: value");


    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("After Step StepName: "+stepExecution.getStepName());
        System.out.println("Job Execution Context: "+stepExecution.getJobExecution().getExecutionContext());
        System.out.println("After Step Execution Context: "+stepExecution.getExecutionContext());
        return null;
    }
}

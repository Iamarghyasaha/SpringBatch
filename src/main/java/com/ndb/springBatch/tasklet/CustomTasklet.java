package com.ndb.springBatch.tasklet;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomTasklet implements Tasklet, StepExecutionListener {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("This is the custom tasklet");
        // accessing the Dummy Job Execution context inside the Step
        System.out.println(chunkContext.getStepContext().getJobExecutionContext());
        return RepeatStatus.FINISHED;

    }
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Before Step StepName: "+stepExecution.getStepName());
        System.out.println("Job Execution Context: "+stepExecution.getJobExecution().getExecutionContext());
        System.out.println("Before Step Execution Context: "+stepExecution.getExecutionContext());

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("After Step StepName: "+stepExecution.getStepName());
        System.out.println("Job Execution Context: "+stepExecution.getJobExecution().getExecutionContext());
        System.out.println("After Step Execution Context: "+stepExecution.getExecutionContext());
        return null;
    }
}

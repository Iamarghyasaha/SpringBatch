package com.ndb.springBatch.config;

import com.ndb.springBatch.listner.FirstJobListner;
import com.ndb.springBatch.listner.FirstStepListner;
import com.ndb.springBatch.processor.FirstItemProcessor;
import com.ndb.springBatch.reader.FirstItemReader;
import com.ndb.springBatch.tasklet.CustomTasklet;
import com.ndb.springBatch.writer.FirstItemWriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class sampleJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private CustomTasklet customTasklet;
    @Autowired
    private FirstJobListner firstJobListner;
    @Autowired
    private FirstStepListner firstStepListner;
    @Autowired
    private FirstItemReader firstItemReader;
    @Autowired
    private FirstItemProcessor firstItemProcessor;
    @Autowired
    private FirstItemWriter firstItemWriter;

    @Bean
    public Job job1(){
        return jobBuilderFactory.get("First Job")
                //RunIdIncrementer will give run.id as key and value will be incremented by +1, starts from 1
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .next(secondStep())
                .next(customStep())
                .listener(firstJobListner)
                .build();
    }


    @Bean
    public Job job2(){
        return jobBuilderFactory.get("Second Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .next(secondStep())
                .build();
    }

    private Step firstStep(){
        return stepBuilderFactory.get("First Step")
                .tasklet(firstTask())
                .listener(firstStepListner)
                .build();
    }
    private Step secondStep(){
        return stepBuilderFactory.get("Second Step")
                .tasklet(secondTask())
                .build();

    }
    private Step customStep(){
        return stepBuilderFactory.get("Custom Step")
                // there is no listener but that also perform the listening operation because customtasklet implements the listener in its Class
                .tasklet(customTasklet)
                .build();
    }
    private Step firstChunkStep(){
        return stepBuilderFactory.get("First Chunk Step")
                // input and output DataType for the chunk-don't focus for the processor
                // see what data type we are reading and what datatype we are writing
                // we are reading integer writing Long
                .<Integer,Long>chunk(4)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

    private Tasklet firstTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("First tasklet step");
                //accessing the step execution context inside the Step task
                System.out.println("Step Execution Context"+ chunkContext.getStepContext().getStepExecutionContext());
                return RepeatStatus.FINISHED;
            }
        };
    }
    private Tasklet secondTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Second tasklet step");
                return RepeatStatus.FINISHED;
            }
        };
    }
}

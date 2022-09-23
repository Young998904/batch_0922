package com.ll.exam.app_2022_09_22.job.withParam;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WithParamJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean // 실행 시 수집
    public Job withParamJob(Step withParamStep1) {
        return jobBuilderFactory.get("withParamJob")
            .start(withParamStep1)
            .build();
    }
    @Bean
    @JobScope // 해당 JOB 이 실행될 때 생성
    public Step withParamStep1(Tasklet withParamStep1Tasklet) {
        return stepBuilderFactory
            .get("withParamStep1")
            .tasklet(withParamStep1Tasklet)// 방법 (1) tasklet (2) item
            .build();
    }

    @Bean
    @StepScope // 해당 Step 이 실행될 때 생성
    public Tasklet withParamStep1Tasklet (
        @Value ("#{jobParameters['age']}") int age,
        @Value ("#{jobParameters['name']}") String name
    ) {
        return (contribution, chunkContext) -> {
            System.out.println("WithParam 테스트릿 1, %s, %d".formatted(name,age));

            return RepeatStatus.FINISHED;
        };
    }
}
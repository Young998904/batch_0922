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

    // 인자값 넘겨줄 때 방법 1
    @Bean
    @JobScope // 해당 JOB 이 실행될 때 생성
    public Step withParamStep1(Tasklet withParamStep1Tasklet) {
        // withParamStep1Tasklet 을 받아올라면 해당 태스크릿이 Bean 으로 등록 되어있어야 함
        return stepBuilderFactory
            .get("withParamStep1")
            .tasklet(withParamStep1Tasklet)// 방법 (1) tasklet (2) item
            .build();
    }

//    // 인자값 넘겨줄 때 방법 2
//    @Bean
//    @JobScope
//    public Step withParamStep1() {
//        return stepBuilderFactory
//            .get("withParamStep1")
//            .tasklet(withParamStep1Tasklet(null, null))
//            .build();
//    }
//    // 실행 시켜 보면 테스크릿 출력값 null, null 로 나옴 => 방법 1 이 맞음!

    @Bean
    @StepScope // 해당 Step 이 실행될 때 생성
    public Tasklet withParamStep1Tasklet (
        @Value ("#{jobParameters['age']}") Integer age,
        @Value ("#{jobParameters['name']}") String name
    ) {
        return (contribution, chunkContext) -> {
            System.out.println("WithParam 태스크릿 1, %s, %d".formatted(name,age));

            return RepeatStatus.FINISHED;
        };
    }
}
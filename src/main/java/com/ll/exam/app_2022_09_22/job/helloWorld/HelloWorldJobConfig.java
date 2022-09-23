package com.ll.exam.app_2022_09_22.job.helloWorld;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 필수 어노테이션
@Configuration
@RequiredArgsConstructor
public class HelloWorldJobConfig {
    // 필수 변수
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    // Job 생성
    @Bean // 실행 시 수집
    public Job helloWorldJob() {
        // get 은 얻다가 아닌 생성의 의미로 해석!
        // get 은 builder 를 가지고 오는 것이다.
        return jobBuilderFactory.get("helloWorldJob")
            .incrementer(new RunIdIncrementer()) // 강제로 매번 다른 ID를 실행시에 파라미터로 부여
            .start(helloWorldStep1 ()) // Step 들을 넣는 부분
            .next(helloWorldStep2())// helloWorldStep1 이 종료되면 다음에 수행됨
            .build();
    }

    // Step 생성
    @Bean
    @JobScope // 해당 JOB 이 실행될 때 생성
    public Step helloWorldStep1 () {
        return stepBuilderFactory
            .get("helloWorldStep1")
            .tasklet(helloWorldStep1Tasklet())// 방법 (1) tasklet (2) item
            .build();
    }
    @Bean
    @JobScope // 해당 JOB 이 실행될 때 생성
    public Step helloWorldStep2 () {
        return stepBuilderFactory
            .get("helloWorldStep2")
            .tasklet(helloWorldStep2Tasklet())// 방법 (1) tasklet (2) item
            .build();
    }

    // 테스크릿 생성
    @Bean
    @StepScope // 해당 Step 이 실행될 때 생성
    public Tasklet helloWorldStep1Tasklet () {
        return (contribution, chunkContext) -> {
                System.out.println("헬로 월드 테스크릿 1!");

                return RepeatStatus.FINISHED;
        };
    }
    @Bean
    @StepScope // 해당 Step 이 실행될 때 생성
    public Tasklet helloWorldStep2Tasklet () {
        return (contribution, chunkContext) -> {
            System.out.println("헬로 월드 테스크릿 2!");

            return RepeatStatus.FINISHED;
        };
    }

}

package com.ll.exam.app_2022_09_22.job.helloWorld;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
            .start(helloWorldStep1 ()) // Step 들을 넣는 부분
            .build();
    }

    // Step 생성
    @Bean
    public Step helloWorldStep1 () {
        return stepBuilderFactory
            .get("helloWorldStep1")
            .tasklet(helloWorldTasklet())// 방법 (1) tasklet (2) item
            .build();
    }

    @Bean
    public Tasklet helloWorldTasklet () {
        return (contribution, chunkContext) -> {
                System.out.println("헬로 월드!");

                return RepeatStatus.FINISHED;
        };
    }

}

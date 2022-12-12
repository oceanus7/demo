package com.tllab.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EnableJpaAuditing 을 여기에 선언할 경우, @WebMvcTest 테스트 실행 시 @SpringBootApplication 을 스캔하면서
// @EnableJpaAuditing 도 스캔하게 되는데, 이때 오류가 발생한다.
// @EnableJpaAuditing 을 사용하기 위해서는 최소 1개 이상의 Entity 가 필요한데, @WebMvcTest 테스트 실행 시에는 Entity 가
// 존재하지 않기 때문에 발생하는 오류이며, @EnableJpaAuditing 을 별도의 Configuration Bean 에 선언하면 된다.

//@EnableJpaAuditing
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

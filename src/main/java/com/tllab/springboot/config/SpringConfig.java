package com.tllab.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @EnableJpaAuditing : JPA Auditing 어노테이션 활성화
// Application 클래스(메인 클래스)에 선언하면, @WebMvcTest 에서 오류가 발생하므로 별도의 Configuration Bean 를 만들어서 선언한다.

@EnableJpaAuditing
@Configuration
public class SpringConfig {
}

package com.tllab.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @EnableJpaAuditing 을 @SpringBootApplication 이 선언된 Application(메인 클래스) 클래스에 선언할 경우,
// @WebMvcTest 테스트 실행 시 @SpringBootApplication 을 스캔하면서 @EnableJpaAuditing 도 스캔하게 되는데, 이 때 오류가 발생한다.

// @EnableJpaAuditing 을 사용하기 위해서는 최소 1개 이상의 Entity 가 필요한데, @WebMvcTest 테스트 실행 시에는 Entity 가
// 존재하지 않기 때문에 발생하는 오류이며, @EnableJpaAuditing 을 아래와 같이 별도의 Configuration Bean 에 선언하여
// @WebMvcTest 테스트 실행시 자동 스캔하지 않도록 하면 된다. (@WebMvcTest 는 일반적인 @Configuration 은 스캔하지 않음)

// @EnableJpaAuditing : JPA Auditing(데이터의 생성/변경 시간을 자동으로 체크하여 해당 컬럼에 저장) 어노테이션 활성화

@EnableJpaAuditing
@Configuration
public class JpaAuditConfig {
}

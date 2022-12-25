package com.tllab.demo.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// @MappedSuperclass : 본 객체를 상속하는 Entity 클래스에 본 객체의 필드들을 자동으로 컬럼 매핑시킨다.
// @EntityListeners(AuditingEntityListener.class) : JPA Auditing 기능을 적용시킨다. JPA Auditing 기능이란 시간(생성, 변경)에
// 대해서 변경되는 것을 자동으로 감지하여 해당 컬럼에 값을 넣어주는 기능이다.
// @CreatedDate, @LastModifiedDate 로 선언된 필드에 자동으로 값이 할당되며 DB 에 저장된다.
// 별도의 Configuration Bean 에 @EnableJpaAuditing 를 선언해야 JPA Auditing 어노테이션들을 사용할 수 있다.

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}

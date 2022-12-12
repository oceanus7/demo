package com.tllab.springboot.domain.posts;

import com.tllab.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// @NoArgsConstructor : 디폴트 생성자 자동 추가
// Entity 클래스에는 Setter 를 지정하지 않도록 하고, 그 목적과 의도가 명확한 메소드를 통해 값을 변경한다.
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    // @Id : PK 지정
    // GenerationType.IDENTITY : Auto Increment 지정
    // Long 으로 선언할 경우 데이터 타입 -> BIGINT
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 데이터 타입 -> VARCHAR(500)
    @Column(length = 500, nullable = false)
    private String title;

    // 데이터 타입 -> TEXT
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // @Column 이 선언되지 않아도 @Entity 로 선언된 클래스의 모든 인스턴스 변수는 컬럼이 된다.
    private String author;

    // @Builder : 빌더 패턴 클래스 생성, 생성자에 포함된 필드만 빌더에 포함
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // Entity 클래스에는 Setter 를 만들지 말고, 아래와 같이 그 목적과 의도가 명확한 메소드를 통해 값을 변경한다.
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}

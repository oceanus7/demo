package com.tllab.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// Java 의 enum 은 다음의 특징을 가진다.
// 1. 안에 선언된 GUEST, USER 등의 값들은 Role 클래스의 인스턴스이다. (static final 로 상수 인스턴스임)
// 2. 안에 선언된 GUEST, USER 등 이외에는 인스턴스 생성이 불가능하다. (생성자가 private 이므로)
// 3. switch 문의 레이블로 사용이 가능하다. (!중요한 특징!)

// enum 은 valueOf 라는 메소드를 사용해서 문자열에 해당하는 열거값을 얻을 수 있다.
// Role r = Role.valueOf("GUEST") // r : Role.GUEST

// switch 문의 레이블 코딩 시, Role.GUEST 가 아니라 그냥 GUEST 로만 쓰면 된다.
// switch 문에 전달되는 변수를 통해서 Role 열거체의 인스턴스임이 확인 가능하기 때문이다.

// 아래의 Role 열거체는 GUEST, USER 의 두 개의 값을 가진다.
// @RequiredArgsConstructor 와 private final 로 선언된 key, title 필드에 의해,
// Role(String key, String title) 과 같은 생성자가 자동 생성된다.
// @Getter 에 의해 getKey(), getTitle() 메소드가 자동 생성된다.

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}

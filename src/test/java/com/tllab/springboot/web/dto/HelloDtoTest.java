package com.tllab.springboot.web.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloDtoTest {

    @Test
    public void lombokOperationTest() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        // @RequiredArgsConstructor : private final 로 선언됨 변수들을 인자로 하는 생성자 생성
        HelloDto helloDto = new HelloDto(name, amount);

        // then
        // @Getter : private 으로 선언된 변수들에 대해 Getter 함수 생성
        assertThat(helloDto.getName()).isEqualTo(name);
        assertThat(helloDto.getAmount()).isEqualTo(amount);
    }

}

package com.tllab.springboot.web.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloDtoTests {

    @Test
    public void testLombokOperation() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        // @RequiredArgsConstructor : final 로 선언된 인스턴스 변수들을 인자로 하는 생성자 생성
        HelloDto helloDto = new HelloDto(name, amount);

        // then
        // @Getter : 선언된 인스턴스 변수들에 대해 Getter 생성
        assertThat(helloDto.getName()).isEqualTo(name);
        assertThat(helloDto.getAmount()).isEqualTo(amount);
    }

}

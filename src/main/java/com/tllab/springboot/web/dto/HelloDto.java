package com.tllab.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// @Getter : private 으로 선언된 변수들에 대해 getter 생성
// @RequiredArgsConstructor : private final 로 선언된 변수들을 인자로 하는 생성자 생성
@Getter
@RequiredArgsConstructor
public class HelloDto {

    private final String name;
    private final int amount;

}

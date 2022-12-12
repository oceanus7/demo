package com.tllab.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// @Getter : 선언된 인스턴스 변수들에 대해 Getter 생성
// @RequiredArgsConstructor : final 로 선언된 인스턴스 변수들을 인자로 하는 생성자 생성
@Getter
@RequiredArgsConstructor
public class HelloDto {

    private final String name;
    private final int amount;

}

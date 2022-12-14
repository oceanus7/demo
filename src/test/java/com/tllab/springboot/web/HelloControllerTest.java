package com.tllab.springboot.web;

import com.tllab.springboot.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest 는 WebSecurityConfigurerAdapter, WebMvcConfigurer 를 비롯한 @ControllerAdvice, @Controller 을 스캔한다.
// 즉, @Repository, @Service, @Component 는 스캔하지 않는다. 따라서 SecurityConfig 은 스캔하지만 CustomOAuth2UserService 는
// 스캔하지 않음으로 인하여 SecurityConfig 생성시 오류가 발생한다.
// 따라서 SecurityConfig 을 스캔 대상에서 제외한다.

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HelloController.class,
            excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
            })
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    // SecurityConfig 을 제외했더라도, Spring Security 를 적용하는 경우, 기본 설정에 의해
    // 사용자 권한 일증이 동작하므로 MockUser 를 사용해야 한다.

    @Test
    @WithMockUser(roles = "USER")
    public void testHello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testHelloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}

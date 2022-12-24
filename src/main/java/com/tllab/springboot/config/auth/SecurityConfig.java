package com.tllab.springboot.config.auth;

import com.tllab.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

// @EnableWebSecurity : Spring Security 설정들을 활성화

@RequiredArgsConstructor
@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
public class SecurityConfig {

    private final CustomOAuth2UserService oauth2UserService;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .csrf().disable()   // h2-console 화면을 사용하기 위한 설정
//            .headers().frameOptions().disable() // h2-console 화면을 사용하기 위한 설정
//        .and()
//            .authorizeRequests()    // URL 별 권한 관리 시작
//                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()  // 전체 열람 권한 부여
//                .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // API 들은 USER 권한을 가진 경우에만 접근 가능
//                .anyRequest().authenticated()   // 그 밖의 URL 들은 인증된 사용자(로그인된 사용자)만 접근 가능
//        .and()
//            .logout()   // 로그아웃 기능 설정 시작
//                .logoutSuccessUrl("/")  // 로그아웃 성공시 / 주소로 이동
//        .and()
//            .oauth2Login()  // OAuth2 로그인 설정 시작
//                .userInfoEndpoint() // 로그인 성공 이후 가져오며, 가져온 사용자 정보에 대한 설정 시작
//                    .userService(oauth2UserService);    // 가져온 사용자 정보로 추가로 진행하고자 하는 기능을 명시한 UserService 인터페이스 구현체 등록
//    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf().disable()   // h2-console 화면을 사용하기 위한 설정
            .headers().frameOptions().disable() // h2-console 화면을 사용하기 위한 설정
        .and()
            .authorizeRequests()    // URL 별 권한 관리 시작
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()  // 전체 열람 권한 부여
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // API 들은 USER 권한을 가진 경우에만 접근 가능
                .anyRequest().authenticated()   // 그 밖의 URL 들은 인증된 사용자(로그인된 사용자)만 접근 가능
        .and()
            .logout()   // 로그아웃 기능 설정 시작
            .logoutSuccessUrl("/")  // 로그아웃 성공시 / 주소로 이동
        .and()
            .oauth2Login()  // OAuth2 로그인 설정 시작
                .userInfoEndpoint() // 로그인 성공 이후 사용자 정보를 가져오며, 가져온 사용자 정보에 대한 설정 시작
                .userService(oauth2UserService) // 가져온 사용자 정보로 추가로 진행하고자 하는 기능을 명시한 UserService 인터페이스 구현체 등록
            .and()
        .and()
        .build();
    }

}

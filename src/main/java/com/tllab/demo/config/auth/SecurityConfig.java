package com.tllab.demo.config.auth;

import com.tllab.demo.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// @EnableWebSecurity : Spring Security 설정들을 활성화

@RequiredArgsConstructor
@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
public class SecurityConfig {

    private final CustomOAuth2UserService oauth2UserService;
    private final Environment env;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        List<String> urlList = new ArrayList<>();
//        urlList.add("/");
//        urlList.add("/css/**");
//        urlList.add("/images/**");
//        urlList.add("/js/**");
//        urlList.add("/profile");
//
//        List<String> profiles = Arrays.asList(env.getActiveProfiles());
//        if (profiles.isEmpty() || profiles.get(0).equals("default")) {
//            urlList.add("/h2-console/**");
//            http.csrf().disable().headers().frameOptions().disable();       // h2-console 화면을 위한 설정
//        }
//
//        http
//            .authorizeRequests()    // URL 별 권한 관리 시작
//                .antMatchers(urlList.toArray(new String[0])).permitAll()    // 전체 열람 권한 부여
//                .antMatchers("/api/v1/**").hasRole(Role.USER.name())        // API 들은 USER 권한을 가진 경우에만 접근 가능
//                .anyRequest().authenticated()                               // 그 밖의 URL 들은 인증된 사용자(로그인된 사용자)만 접근 가능
//        .and()
//            .logout()               // 로그아웃 기능 설정 시작
//                .logoutSuccessUrl("/")                                      // 로그아웃 성공시 root(/) 주소로 이동
//        .and()
//            .oauth2Login()          // OAuth2 로그인 설정 시작
//                .userInfoEndpoint()                                         // 로그인 성공 이후 사용자 정보를 가져오며, 가져온 사용자 정보에 대한 설정 시작
//                .userService(oauth2UserService);                            // 가져온 사용자 정보로 추가로 진행하고자 하는 기능을 명시한 UserService 인터페이스 구현체 등록
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        List<String> urlList = new ArrayList<>();
        urlList.add("/");
        urlList.add("/css/**");
        urlList.add("/images/**");
        urlList.add("/js/**");
        urlList.add("/profile");

        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        if (profiles.isEmpty() || profiles.get(0).equals("default")) {
            urlList.add("/h2-console/**");
            http.headers().frameOptions().disable();    // h2-console 화면을 사용하기 위한 설정
        }

        http
            .csrf().disable()       // rest api 호출을 위해서 설정. 설정하지 않으면 403 에러가 난다.
            .authorizeRequests()    // URL 별 권한 관리 시작
                .antMatchers(urlList.toArray(new String[0])).permitAll()    // 전체 열람 권한 부여
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())        // API 들은 USER 권한을 가진 경우에만 접근 가능
                .anyRequest().authenticated()                               // 그 밖의 URL 들은 인증된 사용자(로그인된 사용자)만 접근 가능
        .and()
            .logout()               // 로그아웃 기능 설정 시작
                .logoutSuccessUrl("/")                                      // 로그아웃 성공시 root(/) 주소로 이동
        .and()
            .oauth2Login()          // OAuth2 로그인 설정 시작
                .userInfoEndpoint()                                         // 로그인 성공 이후 사용자 정보를 가져오며, 가져온 사용자 정보에 대한 설정 시작
                .userService(oauth2UserService);                            // 가져온 사용자 정보로 추가로 진행하고자 하는 기능을 명시한 UserService 인터페이스 구현체 등록

        return http.build();
    }

}

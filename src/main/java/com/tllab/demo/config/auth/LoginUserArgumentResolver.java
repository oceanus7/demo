package com.tllab.demo.config.auth;

import com.tllab.demo.config.auth.dto.SessionUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 해당 메소드 파라미터에 LoginUser 어노테이션이 적용되어 있고, 타입이 SessionUsers 이면 true 리턴
        return parameter.getParameterAnnotation(LoginUser.class) != null && parameter.getParameterType().equals(SessionUsers.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 세션에서 사용자 정보를 가져와서 리턴한다.
        // 이렇게 하면 해당 메소드 라파미터로 여기에서 리턴하는 값이 전달된다.
        return httpSession.getAttribute("users");
    }

}

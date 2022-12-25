package com.tllab.demo.config.auth;

import com.tllab.demo.config.auth.dto.OAuthAttributes;
import com.tllab.demo.config.auth.dto.SessionUsers;
import com.tllab.demo.domain.user.Users;
import com.tllab.demo.domain.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

// OAuth2UserService : 소셜 로그인 이후, 가져온 사용자 정보(email, name, picture 등)를 기반으로 가입 및 정보 수정, 세션 저장 등을 설정
// WebSecurityConfigurerAdapter 를 상속하여 구현한 configure() 메소드에서 HttpSecurity 권한 관리 객체에 등록한다.

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsersRepository usersRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1) DefaultOAuth2UserService 로 기본 loadUser() 처리하고 attributes 를 가져온다.
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();

        // 2) OAuth2UserRequest 로부터 클라이언트 정보(registrationId, userNameAttributeName)를 가져온다.
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String registrationId = clientRegistration.getRegistrationId();
        String userNameAttributeName = clientRegistration.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // 1), 2) 에서 가져온 사용자 정보를 DB의 User 테이블에 저장/업데이트하고, 세션에 저장한다.
        // 이때, User 엔티티를 직접 세션에 저장하지 않고 SessionUser 라는 직렬화가 구현된 Dto 객체를 만들어서 저장해야 한다.
        // (SessionUser 클래스 주석 참고)
        OAuthAttributes attrs = OAuthAttributes.of(registrationId, userNameAttributeName, attributes);
        Users users = saveOrUpdate(attrs);
        httpSession.setAttribute("users", new SessionUsers(users));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(users.getRoleKey())), attrs.getAttributes(), attrs.getNameAttributeKey());
    }

    private Users saveOrUpdate(OAuthAttributes attributes) {
        Users users = usersRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return usersRepository.save(users);
    }

}

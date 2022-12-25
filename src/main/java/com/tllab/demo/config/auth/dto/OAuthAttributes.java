package com.tllab.demo.config.auth.dto;

import com.tllab.demo.domain.user.Role;
import com.tllab.demo.domain.user.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        // Naver 로그인의 경우, attributes 가 "resultCode", "message", "response" 세 개의 항목으로 구성되고,
        // 다른 소셜 로그인의 로그인 결과가 그 중 userNameAttributeName 값인 "response" 하위에 담겨있다.
        // 따라서 attributes 에서 "response" 항목을 추출하여 처리한다.
        // 이 때, nameAttributeKey 는 response 하위 항목 중 "id" 를 사용한다.
        @SuppressWarnings("unchecked") Map<String, Object> response = (Map<String, Object>) attributes.get(userNameAttributeName);
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey("id")
                .build();
    }

    public Users toEntity() {
        return Users.builder()
                .name(name)
                .email(email)
                .picture(picture)
                //.role(Role.GUEST)
                .role(Role.USER)
                .build();
    }

}

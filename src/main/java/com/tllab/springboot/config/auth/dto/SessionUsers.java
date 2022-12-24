package com.tllab.springboot.config.auth.dto;

import com.tllab.springboot.domain.user.Users;
import lombok.Getter;

import java.io.Serializable;

// 세션에 사용자 객체를 저장할 때 직렬화(Serialization)가 구현되어 있어야 한다. (클래스를 직렬화하려면 Serializable 을 상속하면 됨)
// 성능 저하 이슈가 있으므로 일반적으로 Entity 클래스 자체를 Serializable 하게 하지 않고 별도의 Dto 객체를 만들어 사용한다.

// Entity 클래스는 @OneToMany, @ManyToMany 등 자식 엔티티를 가질 수 있고, 이런 경우 자식들까지 직렬화 대상에 포함되므로,
// 성능 저하 이슈, 부스 효과 등이 발생할 가능성이 있다. 따라서 작렬화가 구현된 세션용 Dto 를 추가로 만들어 사용하는 것이
// 운영 및 유지보수에 유리하다.

// 세션 저장용 사용자 객체(직렬화 구현)
@Getter
public class SessionUsers implements Serializable {

    //private static final long serialVersionUID = 1L;

    private final String name;
    private final String email;
    private final String picture;

    public SessionUsers(Users users) {
        this.name = users.getName();
        this.email = users.getEmail();
        this.picture = users.getPicture();
    }

}

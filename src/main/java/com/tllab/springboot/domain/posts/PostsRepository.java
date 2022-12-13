package com.tllab.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    // 별도의 특별한 조회 쿼리가 필요한 경우 아래와 같이 @Query 로 지정할 수 있다.
    // 쿼리가 길고 복합한 경우, MyBatis 를 사용해서 XML 파일에 담는 것이 좋다.
    // MyBatis 와 JPA 를 같이 사용할 수 있다.

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

}

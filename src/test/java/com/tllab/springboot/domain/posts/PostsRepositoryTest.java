package com.tllab.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    // 매 단위 테스트(현재 클래스의 각 테스트 메소드)가 끝날 때마다 수행되는 메소드를 지정한다.
    // 현재 클래스의 모든 단위 테스트(모든 테스트 메소드)가 끝날 때 수행되도록 하려면 @AfterAll 로 지정한다.
    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void TestSaveAndFindPosts() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // 테이블 posts 에 insert/update 쿼리를 실행한다.
        // id 값이 있으면 update, 없으면 insert 쿼리가 실행된다.
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("yhcho@cslee.co.kr")
                .build());

        // when
        // 테이블 posts 에 있는 모든 데이터를 조회해온다.
        List<Posts> list = postsRepository.findAll();

        // then
        assertThat(list.size()).isGreaterThan(0);
        Posts entity = list.get(0);
        assertThat(entity.getTitle()).isEqualTo(title);
        assertThat(entity.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2022,11,30,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>> createdDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}

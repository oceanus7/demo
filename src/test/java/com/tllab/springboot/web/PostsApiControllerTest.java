package com.tllab.springboot.web;

import com.tllab.springboot.domain.posts.Posts;
import com.tllab.springboot.domain.posts.PostsRepository;
import com.tllab.springboot.web.dto.PostsSaveDto;
import com.tllab.springboot.web.dto.PostsUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception {
        // given
        String title = "title";
        String content = "content";
        PostsSaveDto request = PostsSaveDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        ResponseEntity<Long> response = restTemplate.postForEntity(url, request, Long.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isGreaterThan(0L);

        List<Posts> list = postsRepository.findAll();
        assertThat(list.get(0).getTitle()).isEqualTo(title);
        assertThat(list.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다() throws Exception {
        // given
        Posts entity = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = entity.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateDto posts = PostsUpdateDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateDto> request = new HttpEntity<>(posts);

        // when
        ResponseEntity<Long> response = restTemplate.exchange(url, HttpMethod.PUT, request, Long.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isGreaterThan(0L);

        List<Posts> list = postsRepository.findAll();
        assertThat(list.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(list.get(0).getContent()).isEqualTo(expectedContent);
    }

}

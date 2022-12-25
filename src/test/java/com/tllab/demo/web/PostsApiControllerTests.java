package com.tllab.demo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tllab.demo.domain.posts.Posts;
import com.tllab.demo.domain.posts.PostsRepository;
import com.tllab.demo.web.dto.PostsSaveDto;
import com.tllab.demo.web.dto.PostsUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest 는 JPA 를 지원하지 않는다. (Controller, ControllerAdvice 등 외부연동과 관련된 것만 활성화 됨)
// SpringBootTest 와 TestRestTemplate 를 사용하여 테스트한다.
// @WebMvcTest 는 일반적은 @Configuration 은 스캔하지 않는다.

// 스프링 시큐리티 적용시에는 TestRestTemplate 을 사용할 때 사용자 권한 때문에 테스트에 실패한다.
// @WithMockUser 를 사용해야 하며, @WithMockUser 는 MockMvc 에서만 사용 가능하다.
// 따라서, TestRestTemplate 이 아닌 MockMvc 를 생성하여 테스트를 수행해야 한다.

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTests {

    @LocalServerPort
    private int port;

    //@Autowired
    //private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private PostsRepository postsRepository;

    // Spring Security 사용시
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRegisterPosts() throws Exception {
        // given
        String title = "title";
        String content = "content";

        PostsSaveDto posts = PostsSaveDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
//        // Spring Security 사용하지 않을 경우
//        ResponseEntity<Long> response = restTemplate.postForEntity(url, posts, Long.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isGreaterThan(0L);

        // Spring Security 사용시
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(posts)))
                .andExpect(status().isOk());

        // then
        List<Posts> list = postsRepository.findAll();
        assertThat(list.size()).isGreaterThan(0);
        Posts entity = list.get(0);

        assertThat(entity.getTitle()).isEqualTo(title);
        assertThat(entity.getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUpdatePosts() throws Exception {
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

        // when
//        // Spring Security 사용하지 않을 경우
//        HttpEntity<PostsUpdateDto> request = new HttpEntity<>(posts);
//        ResponseEntity<Long> response = restTemplate.exchange(url, HttpMethod.PUT, request, Long.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isGreaterThan(0L);

        // Spring Security 사용시
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(posts)))
                .andExpect(status().isOk());

        // then
        List<Posts> list = postsRepository.findAll();
        assertThat(list.size()).isGreaterThan(0);
        Posts updatedEntity = list.get(0);

        assertThat(updatedEntity.getTitle()).isEqualTo(expectedTitle);
        assertThat(updatedEntity.getContent()).isEqualTo(expectedContent);
    }

}

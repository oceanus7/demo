package com.tllab.demo.web;

import com.tllab.demo.service.posts.PostsService;
import com.tllab.demo.web.dto.PostsDto;
import com.tllab.demo.web.dto.PostsSaveDto;
import com.tllab.demo.web.dto.PostsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    // Bean 객체를 생성자를 통해 주입받는다. @Autowired 로 주입받는 것은 권장하지 않는 방법이다.
    // @RequiredArgsConstructor 를 선언하고 해당 Bean 객체를 final 로 선언하면 생성자를 통해서 Bean 이 주입된다.
    private final PostsService postsService;

    // Posts 엔터티 객체를 직접 사용하지 않고 Dto 객체를 별도로 만들어서 사용한다.
    // 이는 DB Layer 와 View Layer 를 엄격하게 분리하기 위한 것이다.
    // 데이터베이스와 직접적으로 연결된 Entity 객체는 변경 시 연관된 많은 클래스에 영향을 미치게 되므로,
    // Dto 객체를 별도로 정의, 빈번한 변경이 발생하더라도 DB Layer 에 미치는 영향이 없도록 한다.
    // Dto 객체는 Request/Response 를 위한 객체로서 엄밀히 말새 View 레이어를 위한 객체이다.
    // Post/Put 방식의 호출시에는 RequestBody 를 통해 데이터를 전달받는다. (데이터 자체를 @PathVariable 로 받지 않도록 한다.)
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveDto posts) {
        return postsService.save(posts);
    }

    // Post/Put 방식의 호출시에는 RequestBody 를 통해 데이터를 전달받는다. (데이터 자체를 @PathVariable 로 받지 않도록 한다.)
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateDto posts) {
        return postsService.update(id, posts);
    }

    // Get 방식의 호출시에는 @PathVariable 을 통해 값을 전달받는다. (보안 키와 같은 중요한 데이터는 이렇게 전달하면 안된다.)
    @GetMapping("/api/v1/posts/{id}")
    public PostsDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

}

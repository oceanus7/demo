package com.tllab.springboot.web;

import com.tllab.springboot.service.posts.PostsService;
import com.tllab.springboot.web.dto.PostsDto;
import com.tllab.springboot.web.dto.PostsSaveDto;
import com.tllab.springboot.web.dto.PostsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveDto posts) {
        return postsService.save(posts);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateDto posts) {
        return postsService.update(id, posts);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

}

package com.tllab.springboot.web;

import com.tllab.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Mustache 사용시 @RestController 이 아니라 @Controller 을 사용해야 한다.
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    // 스프링부트 2.7.x 에서 Mustache 사용 시 한글이 깨지는 문제가 있다.
    // application.properties 에 다음을 추가한다.
    // server.servlet.encoding.force=true

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("post", postsService.findById(id));
        return "posts-update";
    }

}

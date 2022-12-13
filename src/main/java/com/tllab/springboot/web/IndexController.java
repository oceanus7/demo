package com.tllab.springboot.web;

import com.tllab.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 스프링부트 2.7.x 에서 Mustache 사용 시 한글이 깨지는 문제가 있다.
// application.properties 에 다음을 추가한다. (HTTP request/response 를 UTF-8 로 강제 인코딩하도록 함)
// server.servlet.encoding.force=true

// Mustache 사용시 @RestController 이 아니라 @Controller 을 사용해야 한다.
// @RestController 을 사용하면 ResponseBody 가 JSON 으로 인코딩되므로,
// ViewResolver 가 제대로 처리하지 못하기 때문이다.
// Mustache 라이브러리를 임포트하면,
// Prefix 는 src/main/resources/templates/ 이고, Postfix 는 .mustache 인 ViewResolver 가 자동 등록된다.
// 따라서 "index" 를 리턴하면, src/main/resources/templates/index.mustache 라는 전체 경로명으로 처리된다.
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        // Thymeleaf 나 Mustache 와 같은 뷰 템플릿은 서버 템플릿으로,
        // 서버 사이드에서 Model 객체의 속성을 통해 뷰 템플릿에 값을 넘겨 줄 수 있다.
        // 내림차순으로 정렬된 PostsListDto 객체 리스트를 뷰 템플릿에 posts 라는 이름으로 넘겨준다.
        model.addAttribute("posts", postsService.findAllDesc());

        // index.mustache 뷰 템플릿 로딩
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        // posts-save.mustache 뷰 템플릿 로딩
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        // Thymeleaf 나 Mustache 와 같은 뷰 템플릿은 서버 템플릿으로,
        // 서버 사이드에서 Model 객체의 속성을 통해 뷰 템플릿에 값을 넘겨 줄 수 있다.
        // id 로 조회한 PostsDto 객체를 post 라는 이름으로 뷰 템플릿에 넘겨준다.
        model.addAttribute("post", postsService.findById(id));

        // posts-update.mustache 뷰 템플릿 로딩
        return "posts-update";
    }

}

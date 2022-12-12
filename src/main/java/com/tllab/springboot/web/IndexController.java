package com.tllab.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Mustache 사용시 @RestController 이 아니라 @Controller 을 사용해야 한다.
@Controller
public class IndexController {

    // 스프링부트 2.7.x 에서 Mustache 사용 시 한글이 깨지는 문제가 있다.
    // application.properties 에 다음을 추가한다.
    // server.servlet.encoding.force=true

    @GetMapping("/")
    public String index() {
        return "index";
    }

}

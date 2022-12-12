package com.tllab.springboot.web.dto;

import com.tllab.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }

}

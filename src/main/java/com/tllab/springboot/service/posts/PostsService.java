package com.tllab.springboot.service.posts;

import com.tllab.springboot.domain.posts.Posts;
import com.tllab.springboot.domain.posts.PostsRepository;
import com.tllab.springboot.web.dto.PostsDto;
import com.tllab.springboot.web.dto.PostsSaveDto;
import com.tllab.springboot.web.dto.PostsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveDto posts) {
        return postsRepository.save(posts.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateDto posts) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        entity.update(posts.getTitle(), posts.getContent());
        return id;
    }

    @Transactional(readOnly = true)
    public PostsDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsDto(entity);
    }

}

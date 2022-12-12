package com.tllab.springboot.service.posts;

import com.tllab.springboot.domain.posts.Posts;
import com.tllab.springboot.domain.posts.PostsRepository;
import com.tllab.springboot.web.dto.PostsDto;
import com.tllab.springboot.web.dto.PostsListDto;
import com.tllab.springboot.web.dto.PostsSaveDto;
import com.tllab.springboot.web.dto.PostsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        // 여기서 entity 를 별도로 save 하지 않아도 변경 사항이 DB 에 적용된다.
        // JPA 에서 지원하는 기능으로, 트랜잭션 안에서 DB 에서 데이터를 가져오면 영속성 컨텍스트가 유지되며,
        // 트랜잭션이 끝나는 시점에 변경사항을 체크(더티 체킹)해서 자동으로 적용시킨다.
        entity.update(posts.getTitle(), posts.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public PostsDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(entity);
    }

}

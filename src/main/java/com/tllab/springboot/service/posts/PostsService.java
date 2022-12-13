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
        // Posts 엔터티 클래스는 자신의 dto 클래스에 의존적이지 않도록 해야 한다.
        // 반대로 dto 클래스들은 자신들이 참조하는 Entity 클래스에 의존적이다.
        // Posts entity = Posts.createEntity(PostsSaveDto posts) 와 같은 형태로 작성하지 않도록 주의한다.
        Posts entity = posts.toEntity();
        return postsRepository.save(entity).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateDto posts) {
        // findById 는 결과 값이 null 이 될 수 있으므로, orElseThrow() 를 통해 예외처리를 해주어야 한다.
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
        // findAllDesc() 함수는 List<Posts> 타입의 리스트를 반환한다.
        // 이 리스트를 Stream<Posts> 타입의 스트림으로 변환한 다음,
        // 람다식(posts -> new PostsListDto(posts))으로 매핑한다.
        // 매핑 결과는 Stream<PostsListDto> 타입의 스트림이며,
        // 이를 List<PostsListDto> 타입의 리스트로 변환한다.
        return postsRepository.findAllDesc().stream()
                .map(PostsListDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        // findById 는 결과 값이 null 이 될 수 있으므로, orElseThrow() 를 통해 예외처리를 해주어야 한다.
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(entity);
    }

}

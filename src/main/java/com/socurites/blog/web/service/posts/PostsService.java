package com.socurites.blog.web.service.posts;

import com.socurites.blog.web.domain.posts.Posts;
import com.socurites.blog.web.domain.posts.PostsRepository;
import com.socurites.blog.web.dto.PostsUpdateRequestDto;
import com.socurites.blog.web.dto.PostsResponseDto;
import com.socurites.blog.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostsService {
  private final PostsRepository postsRepository;

  public Long save(PostsSaveRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, PostsUpdateRequestDto requestDto) {
    Posts post = postsRepository.findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException(String.format("No posts for %d", id)));

    post.update(requestDto.getTitle(), requestDto.getContent());

    return id;
  }

  public PostsResponseDto findById(Long id) {
    Posts post = postsRepository.findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException(String.format("No posts for %d", id)));

    return new PostsResponseDto(post);
  }
}

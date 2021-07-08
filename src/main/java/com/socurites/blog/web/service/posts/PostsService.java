package com.socurites.blog.web.service.posts;

import com.socurites.blog.web.domain.posts.Posts;
import com.socurites.blog.web.domain.posts.PostsRepository;
import com.socurites.blog.web.dto.PostsListResponseDto;
import com.socurites.blog.web.dto.PostsUpdateRequestDto;
import com.socurites.blog.web.dto.PostsResponseDto;
import com.socurites.blog.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsService {
  private final PostsRepository postsRepository;

  @Transactional
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

  @Transactional
  public void delete(Long id) {
    Posts post = postsRepository.findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException(String.format("No posts for %d", id)));

    postsRepository.delete(post);
  }

  @Transactional(readOnly = true)
  public List<PostsListResponseDto> findAllDesc() {
    return postsRepository.findAllDesc().stream()
      .map(PostsListResponseDto::new)
      .collect(Collectors.toList());
  }

  public PostsResponseDto findById(Long id) {
    Posts post = postsRepository.findById(id)
      .orElseThrow(() ->
        new IllegalArgumentException(String.format("No posts for %d", id)));

    return new PostsResponseDto(post);
  }
}

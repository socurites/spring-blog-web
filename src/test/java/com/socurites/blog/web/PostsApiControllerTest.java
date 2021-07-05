package com.socurites.blog.web;

import com.socurites.blog.web.domain.posts.Posts;
import com.socurites.blog.web.domain.posts.PostsRepository;
import com.socurites.blog.web.dto.PostsSaveRequestDto;
import com.socurites.blog.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PostsRepository postsRepository;

  @AfterEach
  public void tearDown() {
    postsRepository.deleteAll();
  }

  @Test
  public void requestSave() {
    final String requestUrl = "http://localhost:" + port + "/api/v1/posts";
    final String title = "post's title";
    final String content = "post's content";
    final String author = "socurites@gmail.com";

    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
      .title(title)
      .content(content)
      .author(author)
      .build();

    ResponseEntity<Long> responseEntity = restTemplate.postForEntity(requestUrl, requestDto, Long.class);

    assertThat(responseEntity.getStatusCode())
      .isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody())
      .isGreaterThan(0L);
  }

  @Test
  public void requestUpdate() {
    final String title = "post's title";
    final String content = "post's content";
    final String author = "socurites@gmail.com";

    Posts savedPost = postsRepository.save(Posts.builder()
      .title(title)
      .content(content)
      .author(author)
      .build());

    final Long updateId = savedPost.getId();
    final String updatedTitle = "post's title - 2";
    final String updatedContent = "post's content - 2";

    final String requestUrl = "http://localhost:" + port + "/api/v1/posts/" + updateId;

    PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
      .title(updatedTitle)
      .content(updatedContent)
      .build();
    HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

    ResponseEntity<Long> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.PUT, requestEntity, Long.class);

    assertThat(responseEntity.getStatusCode())
      .isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody())
      .isGreaterThan(0L);

    Posts updatedPost = postsRepository.findById(updateId)
      .orElseThrow(() ->
        new IllegalArgumentException("No post found"));

    assertThat(updatedPost.getTitle()).isEqualTo(updatedTitle);
    assertThat(updatedPost.getContent()).isEqualTo(updatedContent);
  }
}

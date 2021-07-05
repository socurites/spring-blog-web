package com.socurites.blog.web.domain.posts;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {
  private static final Logger log = LoggerFactory.getLogger(PostsRepositoryTest.class);

  @Autowired
  private PostsRepository postsRepository;

  @AfterEach
  public void clearnUp() {
    postsRepository.deleteAll();
  }

  @Test
  public void findAll() {
    final String title = "post's title";
    final String content = "post's content";
    final String author = "socurites@gmail.com";

    postsRepository.save(Posts.builder()
      .title(title)
      .content(content)
      .author(author)
      .build());

    List<Posts> posts = postsRepository.findAll();

    Posts post = posts.get(0);
    assertThat(title).isEqualTo(post.getTitle());
    assertThat(content).isEqualTo(post.getContent());
  }

  @Test
  public void saveBaseTimeEntity() {
    LocalDateTime now = LocalDateTime.now();

    final String title = "post's title";
    final String content = "post's content";
    final String author = "socurites@gmail.com";

    Posts savedPost = postsRepository.save(Posts.builder()
      .title(title)
      .content(content)
      .author(author)
      .build());

    log.info("Created: {}, Modifeid: {}", savedPost.getCreatedDate(), savedPost.getModifiedDate());

    assertThat(savedPost.getCreatedDate()).isAfter(now);
    assertThat(savedPost.getModifiedDate()).isAfter(now);
  }

}

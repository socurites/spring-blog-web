package com.socurites.blog.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void indexView() {
    final String targetUrl = "/";
    final String bodyText = "스프링부트로 시작하는 웹 서비스";

    String body = restTemplate.getForObject(targetUrl, String.class);

    assertThat(body).contains(bodyText);
  }
}

package com.socurites.blog.web.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HelloResponseDtoTest {
  @Test
  public void create() {
    String name = "test";
    int amount = 1000;

    HelloResponseDto dto = new HelloResponseDto(name, amount);

    assertThat(name).isEqualTo(dto.getName());
    assertThat(amount).isEqualTo(dto.getAmount());
  }
}
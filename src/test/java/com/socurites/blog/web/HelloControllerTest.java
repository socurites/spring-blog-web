package com.socurites.blog.web;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HelloController.class)
//@AllArgsConstructor
public class HelloControllerTest {
  @Autowired
  private MockMvc mvc;

  @Test
  public void hello() throws Exception {
    final String url = "/hello";
    final String result = "hello";

    mvc.perform(get(url))
      .andExpect(status().isOk())
      .andExpect(content().string(result));
  }

  @Test
  public void helloDto() throws Exception {
    final String url = "/hello/dto";
    final String name = "hello";
    final int amount = 1000;

    mvc.perform(get(url)
        .param("name", name)
        .param("amount", String.valueOf(amount)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name", is(name)))
      .andExpect(jsonPath("$.amount", is(amount)));

  }
}

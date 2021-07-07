package com.socurites.blog.web;

import com.socurites.blog.config.auth.LoginUser;
import com.socurites.blog.config.auth.dto.SessionUser;
import com.socurites.blog.web.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {
  private static final Logger log = LoggerFactory.getLogger(IndexController.class);
  private final PostsService postsService;

  @GetMapping("/")
  public String index(Model model,
                      @LoginUser SessionUser user) {
    model.addAttribute("posts", postsService.findAllDesc());

    if (null != user) {
      log.info("{} logged in", user.getEmail());
      model.addAttribute("user", user);
    } else {
      log.info("Not logged in");
    }

    return "index";
  }

  @GetMapping("/posts/update/{id}")
  public String postsUpdate(@PathVariable Long id, Model model) {
    model.addAttribute("post", postsService.findById(id));

    return "posts-update";
  }
}

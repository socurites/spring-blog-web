package com.socurites.blog.config.auth;

import com.socurites.blog.web.domain.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomUserOAuth2UserService customUserOAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**")
          .permitAll()
        .antMatchers("/api/v1/**")
          .hasRole(Role.USER.name())
        .anyRequest().authenticated()
      .and()
        .logout()
          .logoutSuccessUrl("/")
      .and()
        .oauth2Login()
          .userInfoEndpoint()
            .userService(customUserOAuth2UserService);
  }
}

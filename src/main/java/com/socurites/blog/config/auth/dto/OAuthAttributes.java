package com.socurites.blog.config.auth.dto;

import com.socurites.blog.web.domain.user.Role;
import com.socurites.blog.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

@Getter
public class OAuthAttributes {
  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                   Map<String, Object> attributes) {
    return ofGoogle(userNameAttributeName, attributes);
  }

  public static OAuthAttributes ofGoogle(String userNameAttributeName,
                                   Map<String, Object> attributes) {
    return OAuthAttributes.builder()
      .name(MapUtils.getString(attributes, "name"))
      .email(MapUtils.getString(attributes, "email"))
      .picture(MapUtils.getString(attributes, "picture"))
      .attributes(attributes)
      .nameAttributeKey(userNameAttributeName)
      .build();
  }

  public User toEntity() {
    return User.builder()
      .name(name)
      .email(email)
      .picture(picture)
      .role(Role.GUEST)
      .build();
  }
}

package vn.edu.neu.veaknaz.client.model.group;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberInfoView {
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  @JsonProperty("uid")
  private String id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("username")
  private String username;
}

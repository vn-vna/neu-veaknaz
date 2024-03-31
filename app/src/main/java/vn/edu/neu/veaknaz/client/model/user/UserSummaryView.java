package vn.edu.neu.veaknaz.client.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserSummaryView {
  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  @JsonProperty("uid")
  private String uid;
  @JsonProperty("username")
  private String username;
}

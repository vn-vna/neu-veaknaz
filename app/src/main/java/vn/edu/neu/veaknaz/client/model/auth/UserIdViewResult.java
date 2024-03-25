package vn.edu.neu.veaknaz.client.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserIdViewResult {

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @JsonProperty("uid")
  private String userId;
}

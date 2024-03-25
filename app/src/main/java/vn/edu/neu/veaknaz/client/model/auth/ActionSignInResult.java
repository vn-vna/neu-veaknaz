package vn.edu.neu.veaknaz.client.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionSignInResult {
  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }
  @JsonProperty("token")
  private String tokenId;
}

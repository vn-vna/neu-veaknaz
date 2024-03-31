package vn.edu.neu.veaknaz.client.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserSearchView {
  public List<UserSummaryView> getUsers() {
    return users;
  }

  public void setUsers(List<UserSummaryView> users) {
    this.users = users;
  }
  @JsonProperty("users")
  private List<UserSummaryView> users;
}

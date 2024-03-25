package vn.edu.neu.veaknaz.client.model.group;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupInfoView {
  public List<UserGroupInfoView> getGroups() {
    return groups;
  }

  public void setGroups(List<UserGroupInfoView> groups) {
    this.groups = groups;
  }

  @JsonProperty("groups")
  private List<UserGroupInfoView> groups;
}

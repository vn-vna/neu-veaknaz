package vn.edu.neu.veaknaz.client.model.group;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupMemberView {
  public List<MemberInfoView> getMembers() {
    return members;
  }

  public void setMembers(List<MemberInfoView> members) {
    this.members = members;
  }

  @JsonProperty("members")
  private List<MemberInfoView> members;
}

package vn.edu.neu.veaknaz.client.model.invitation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvitationGroupView {
  public String getGid() {
    return gid;
  }

  public void setGid(String gid) {
    this.gid = gid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  @JsonProperty("gid")
  private String gid;
  @JsonProperty("name")
  private String name;
}

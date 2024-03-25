package vn.edu.neu.veaknaz.client.model.group;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserGroupInfoView {
  public String getGid() {
    return gid;
  }

  public void setGid(String gid) {
    this.gid = gid;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public LatestMessageInfoView getLatestMessage() {
    return latestMessage;
  }

  public void setLatestMessage(LatestMessageInfoView latestMessage) {
    this.latestMessage = latestMessage;
  }
  @JsonProperty("gid")
  private String gid;
  @JsonProperty("name")
  private String groupName;
  @JsonProperty("created")
  private String created;
  @JsonProperty("latest_msgs")
  private LatestMessageInfoView latestMessage;
}

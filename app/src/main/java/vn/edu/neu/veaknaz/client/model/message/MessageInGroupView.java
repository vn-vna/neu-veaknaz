package vn.edu.neu.veaknaz.client.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MessageInGroupView {
  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public List<MessageDataView> getMessages() {
    return messages;
  }

  public void setMessages(List<MessageDataView> messages) {
    this.messages = messages;
  }
  @JsonProperty("gid")
  private String groupId;
  @JsonProperty("messages")
  private List<MessageDataView> messages;

}

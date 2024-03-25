package vn.edu.neu.veaknaz.client.model.group;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LatestMessageInfoView {
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getSenderName() {
    return senderName;
  }

  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  public String getSenderUsername() {
    return senderUsername;
  }

  public void setSenderUsername(String senderUsername) {
    this.senderUsername = senderUsername;
  }

  public String getSent() {
    return sent;
  }

  public void setSent(String sent) {
    this.sent = sent;
  }
  @JsonProperty("mid")
  private String id;
  @JsonProperty("content")
  private String content;
  @JsonProperty("sender_name")
  private String senderName;
  @JsonProperty("sender_username")
  private String senderUsername;
  @JsonProperty("sent")
  private String sent;
}

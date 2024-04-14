package vn.edu.neu.veaknaz.client.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDataView {
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

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  public String getSentAt() {
    return sentAt;
  }

  public void setSentAt(String sentAt) {
    this.sentAt = sentAt;
  }

  @JsonProperty("id")
  private String id;
  @JsonProperty("content")
  private String content;
  @JsonProperty("sender")
  private String senderId;
  @JsonProperty("sent_at")
  private String sentAt;
}

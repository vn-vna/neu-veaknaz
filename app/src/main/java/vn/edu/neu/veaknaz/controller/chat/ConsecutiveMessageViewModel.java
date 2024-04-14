package vn.edu.neu.veaknaz.controller.chat;

import android.os.Build;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import vn.edu.neu.veaknaz.client.model.message.MessageDataView;

public class ConsecutiveMessageViewModel {
  public ConsecutiveMessageViewModel(String senderId, List<MessageContent> messageContentList) {
    this.senderId = senderId;
    this.messageContentList = messageContentList;
  }

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  public List<MessageContent> getMessageContentList() {
    return messageContentList;
  }

  public void setMessageContentList(List<MessageContent> messageContentList) {
    this.messageContentList = messageContentList;
  }

  private String senderId;
  private List<MessageContent> messageContentList;

}

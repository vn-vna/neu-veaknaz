package vn.edu.neu.veaknaz.view;

public class BaseMessage {
  public BaseMessage(String messageText) {
    this.messageText = messageText;
  }

  public String getMessageText() {
    return messageText;
  }
  private final String messageText;
}
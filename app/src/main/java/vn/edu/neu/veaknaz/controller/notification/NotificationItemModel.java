package vn.edu.neu.veaknaz.controller.notification;

public class NotificationItemModel {
  public NotificationItemModel(String name, String displayName) {
    this.groupName = name;
    this.senderDisplay = displayName;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getSenderDisplay() {
    return senderDisplay;
  }

  public void setSenderDisplay(String senderDisplay) {
    this.senderDisplay = senderDisplay;
  }

  private String groupName;
  private String senderDisplay;
}

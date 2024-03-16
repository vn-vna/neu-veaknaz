package vn.edu.neu.veaknaz.client;

import com.microsoft.signalr.HubConnection;

import lombok.Getter;

public class AntNotificationClient {
  private AntNotificationClient() {
  }

  @Getter
  HubConnection notificationHub;
}

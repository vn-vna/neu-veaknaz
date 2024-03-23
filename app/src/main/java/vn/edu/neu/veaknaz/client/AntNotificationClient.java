package vn.edu.neu.veaknaz.client;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.Objects;

public class AntNotificationClient {
  private AntNotificationClient() {
    notificationHub = HubConnectionBuilder.create("http://localhost:5192")
        .build();
  }

  public static AntNotificationClient getInstance() {
    if (Objects.isNull(_instance)) {
      synchronized (AntNotificationClient.class) {
        if (Objects.isNull(_instance)) {
          _instance = new AntNotificationClient();
        }
      }
    }

    return _instance;
  }

  public HubConnection getNotificationHub() {
    return notificationHub;
  }

  public void setNotificationHub(HubConnection notificationHub) {
    this.notificationHub = notificationHub;
  }

  private static AntNotificationClient _instance;
  HubConnection notificationHub;
}

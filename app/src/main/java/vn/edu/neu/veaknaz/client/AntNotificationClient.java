package vn.edu.neu.veaknaz.client;

import android.util.Log;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.Objects;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.application.VeaknazApplication;

public class AntNotificationClient {
  private AntNotificationClient() {
    var url = VeaknazApplication.getInstance().getBaseContext().getString(R.string.ant_base_url);
    notificationHub = HubConnectionBuilder.create(url + "hub/notifications")
        .build();

    notificationHub.on("Invitation", (message) -> {
      Log.d("Notification", "Invitation " + message);
    }, String.class);

    notificationHub.on("Message", (message) -> {
      Log.d("Notification", "Message " + message);
    }, String.class);

    notificationHub.start()
        .subscribe(() -> {
          Log.d("Notification", "Connected");
        }, error -> {
          Log.e("Notification", "Error: " + error.getMessage());
        });
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

  private static AntNotificationClient _instance;
  private final HubConnection notificationHub;
}

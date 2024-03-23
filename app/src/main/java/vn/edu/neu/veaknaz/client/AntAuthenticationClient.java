package vn.edu.neu.veaknaz.client;

import java.util.Objects;
import java.util.Optional;

public class AntAuthenticationClient {

  private AntAuthenticationClient() {

  }

  public static AntAuthenticationClient getInstance() {
    if (Objects.isNull(_instance)) {
      synchronized (AntAuthenticationClient.class) {
        if (Objects.isNull(_instance)) {
          _instance = new AntAuthenticationClient();
        }
      }
    }

    return _instance;
  }

  private static AntAuthenticationClient _instance;
  private Optional<String> _userToken;
}

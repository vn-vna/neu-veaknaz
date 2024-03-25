package vn.edu.neu.veaknaz.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.client.AntAuthenticationClient;

public class AuthenticationActivity extends AppCompatActivity {
  public AuthenticationActivity() {
    super();
    executor = Executors.newCachedThreadPool();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_authentication);

    var future = AntAuthenticationClient.getInstance()
        .signIn("vnvna", "kaorisuki");

    var currentActivity = this;

    Futures.addCallback(future, new FutureCallback<String>() {
      @Override
      public void onSuccess(String result) {
        Intent intent = new Intent(currentActivity, MainActivity.class);
        startActivity(intent);
      }

      @Override
      public void onFailure(Throwable t) {
        t.printStackTrace();
      }
    }, executor);
  }


  ExecutorService executor;
}
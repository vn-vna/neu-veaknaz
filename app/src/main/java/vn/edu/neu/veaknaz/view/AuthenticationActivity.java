package vn.edu.neu.veaknaz.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.fragment.LoginFragment;

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

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.authentication_fragment, new LoginFragment())
        .commit();
  }

  ExecutorService executor;
}
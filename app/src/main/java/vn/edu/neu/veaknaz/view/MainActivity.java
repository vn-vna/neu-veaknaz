package vn.edu.neu.veaknaz.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.client.AntAuthenticationClient;
import vn.edu.neu.veaknaz.fragment.NavigationFragment;

public class MainActivity extends AppCompatActivity {

  public MainActivity() {
    super();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    var authenticationClient = AntAuthenticationClient.getInstance();
    var isCovered = !authenticationClient.getUserToken().isPresent();

    if (isCovered) {
      startActivity(new Intent(this, WelcomeActivity.class));
      finish();
    } else {
      var fragmentManager = getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.replace(R.id.navigation_fragment, new NavigationFragment());
      fragmentTransaction.commit();
    }
  }

}
package vn.edu.neu.veaknaz.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.fragment.NavigationFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_main_activity), Context.MODE_PRIVATE);
    boolean isCovered = sharedPreferences.getBoolean("covered", true);

    if (isCovered) {
      startActivity(new Intent(this, WelcomeActivity.class));
      finish();
    } else {
      FragmentManager fragmentManager = getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.replace(R.id.navigation_fragment, new NavigationFragment());
      fragmentTransaction.commit();
    }
  }

  public enum MainActivityState {
    COVERED,
    UNCOVERED
  }

}
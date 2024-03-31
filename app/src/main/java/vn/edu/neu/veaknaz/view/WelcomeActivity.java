package vn.edu.neu.veaknaz.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.util.LocaleManager;

public class WelcomeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    localeManager = new LocaleManager(this);

    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_welcome);
    this.<ImageView>findViewById(R.id.welcome_button_go_next)
        .setOnClickListener(e -> {
          Intent intent = new Intent(this, AuthenticationActivity.class);
          startActivity(intent);
          finish();
        });
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  private LocaleManager localeManager;
}
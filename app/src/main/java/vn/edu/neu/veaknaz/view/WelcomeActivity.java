package vn.edu.neu.veaknaz.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.neu.veaknaz.R;

public class WelcomeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_welcome);
    this.<ImageView>findViewById(R.id.welcome_button_go_next)
        .setOnClickListener(e -> {
          Intent intent = new Intent(this, AuthenticationActivity.class);
          startActivity(intent);
          finish();
        });
  }
}
package vn.edu.neu.veaknaz.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.neu.veaknaz.R;

public class WelcomeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);
    this.<ImageView>findViewById(R.id.welcome_button_go_next)
        .setOnClickListener(this::onButtonNextClicked);
  }

  private void onButtonNextClicked(View e) {
    Intent intent = new Intent(this, AuthenticationActivity.class);
    startActivity(intent);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
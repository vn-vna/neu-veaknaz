package vn.edu.neu.veaknaz.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.neu.veaknaz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setSupportActionBar(binding.toolbar);
  }

}
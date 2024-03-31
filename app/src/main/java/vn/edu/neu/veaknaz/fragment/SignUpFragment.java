package vn.edu.neu.veaknaz.fragment;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.neu.veaknaz.R;

public class SignUpFragment extends Fragment {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    requireView()
        .<Button>findViewById(R.id.fragment_login_button_go_to_login)
        .setOnClickListener(e -> {
          requireActivity()
              .getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.authentication_fragment, new LoginFragment())
              .commit();
        });

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_sign_up, container, false);
  }
}
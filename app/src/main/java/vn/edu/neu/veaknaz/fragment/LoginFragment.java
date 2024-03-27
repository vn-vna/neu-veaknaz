package vn.edu.neu.veaknaz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.client.AntAuthenticationClient;
import vn.edu.neu.veaknaz.view.MainActivity;

public class LoginFragment extends Fragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    requireView()
        .<Button>findViewById(R.id.fragment_login_button_go_to_signup)
        .setOnClickListener(e -> {
          requireActivity()
              .getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.authentication_fragment, new SignUpFragment())
              .commit();
        });

    requireView()
        .<Button>findViewById(R.id.fragment_login_button_login)
        .setOnClickListener(e -> {
          var username = requireView().<EditText>findViewById(R.id.fragment_login_username_edit).getText().toString();
          var password = requireView().<EditText>findViewById(R.id.fragment_login_password_edit).getText().toString();
          AntAuthenticationClient.getInstance().login(username, password, new OnLoginListener());
        });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_login, container, false);
  }

  class OnLoginListener implements AntAuthenticationClient.LoginEventListener {
    @Override
    public void onLoginSuccess() {
      var intent = new Intent(requireContext(), MainActivity.class);
      startActivity(intent);
      requireActivity().finish();
    }

    @Override
    public void onLoginFailed() {
      requireView()
          .<EditText>findViewById(R.id.fragment_login_username_edit)
          .setError("Invalid username or password");
    }
  }
}
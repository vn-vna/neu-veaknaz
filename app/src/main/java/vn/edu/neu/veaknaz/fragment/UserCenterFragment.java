package vn.edu.neu.veaknaz.fragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import io.supercharge.shimmerlayout.ShimmerLayout;
import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.client.AntUserInfoClient;
import vn.edu.neu.veaknaz.client.model.user.UserInfoView;

public class UserCenterFragment extends Fragment {

  public UserCenterFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void onResume() {
    super.onResume();
    changeMenu();
    refreshUserData();
  }

  private void refreshUserData() {

    AntUserInfoClient.getInstance().getCurrentUserInfo(new AntUserInfoClient.UserInfoEventListener() {
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onGetUserInfoSuccess(UserInfoView userInfo) {
        requireActivity().runOnUiThread(() -> {
          var shimmer = requireView().<ShimmerLayout>findViewById(R.id.usercenter_shimmer_layout);
          var userCenter = requireView().findViewById(R.id.usercenter_main_layout);

          shimmer.stopShimmerAnimation();
          shimmer.setVisibility(View.GONE);
          userCenter.setVisibility(View.VISIBLE);
          var username = "@" + userInfo.getUsername();
          requireView().<TextView>findViewById(R.id.usercenter_username).setText(username);

          var displayName = Optional.ofNullable(userInfo.getFirstname()).orElse("") + " " + Optional.ofNullable(userInfo.getLastname()).orElse("");
          if (displayName.trim().isEmpty()) {
            displayName = getString(R.string.usercenter_displayname_default);
          }

          displayNameTextView.setText(displayName.trim());

          var birthdate = Date.from(Instant.parse(userInfo.getBirth()));
          var birthdateString = String.format("%tF", birthdate);

          firstNameInput.setText(Optional.ofNullable(userInfo.getFirstname()).orElse(""));
          lastNameInput.setText(Optional.ofNullable(userInfo.getLastname()).orElse(""));
          birthdayInput.setText(Optional.of(birthdateString).orElse(""));
        });

      }

      @Override
      public void onGetUserInfoFailed() {
        Log.e("UserCenterFragment", "Failed to get user info");
      }
    });

  }

  private void changeMenu() {
    Optional.ofNullable(requireActivity().<NavigationView>findViewById(R.id.nav_view))
        .ifPresent(navView -> {
          navView.getMenu().clear();
          navView.inflateMenu(R.menu.activity_main_menu_fragment_usercenter);

          var editProfileMenuItem = navView.getMenu().findItem(R.id.activity_main_menu_item_edit_user);
          editProfileMenuItem.setOnMenuItemClickListener((item) -> {
            firstNameInput.setEnabled(true);
            lastNameInput.setEnabled(true);
            birthdayInput.setEnabled(true);
            saveInfoButton.setVisibility(View.VISIBLE);

            Optional.ofNullable(requireActivity().<DrawerLayout>findViewById(R.id.activity_main_drawer))
                .ifPresent(DrawerLayout::close);

            return true;
          });
        });

    birthdayInput.setOnClickListener(view -> {
      if (!birthdayInput.isEnabled()) {
        return;
      }

      new DatePickerDialog(requireContext(), (datePicker, year, month, day) -> {
        var monthString = String.valueOf(month + 1);
        if (monthString.length() == 1) {
          monthString = "0" + monthString;
        }

        var dayString = String.valueOf(day);
        if (dayString.length() == 1) {
          dayString = "0" + dayString;
        }

        birthdayInput.setText(year + "-" + monthString + "-" + dayString);
      }, 2000, 0, 1).show();
    });
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    var shimmer = requireView().<ShimmerLayout>findViewById(R.id.usercenter_shimmer_layout);
    shimmer.startShimmerAnimation();

    displayNameTextView = requireView().findViewById(R.id.usercenter_displayname);
    firstNameInput = requireView().findViewById(R.id.usercenter_input_firstname);
    lastNameInput = requireView().findViewById(R.id.usercenter_input_lastname);
    birthdayInput = requireView().findViewById(R.id.usercenter_input_birthday);
    saveInfoButton = requireView().findViewById(R.id.usercenter_button_save_info);

    saveInfoButton.setOnClickListener(button -> {
      saveInfoButton.setVisibility(View.GONE);
      firstNameInput.setEnabled(false);
      lastNameInput.setEnabled(false);
      birthdayInput.setEnabled(false);



      AntUserInfoClient.getInstance().updateUserInfo(
          firstNameInput.getText().toString(),
          lastNameInput.getText().toString(),
          birthdayInput.getText().toString() + "T00:00:00Z",
          0,
          new AntUserInfoClient.UserUpdateEventListener() {
            @Override
            public void onUpdateUserInfoSuccess() {
              refreshUserData();
            }

            @Override
            public void onUpdateUserInfoFailed() {
              Log.e("UserCenterFragment", "Failed to update user info");
            }
          });

      refreshUserData();
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_user_center, container, false);
  }

  private TextView displayNameTextView;
  private TextView firstNameInput;
  private TextView lastNameInput;
  private TextView birthdayInput;

  private Button saveInfoButton;

}
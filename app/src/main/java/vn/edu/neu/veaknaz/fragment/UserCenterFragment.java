package vn.edu.neu.veaknaz.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

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
          firstNameInput.setText(Optional.ofNullable(userInfo.getFirstname()).orElse(""));
          lastNameInput.setText(Optional.ofNullable(userInfo.getLastname()).orElse(""));
          birthdayInput.setText(Optional.ofNullable(userInfo.getBirth()).orElse(""));
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
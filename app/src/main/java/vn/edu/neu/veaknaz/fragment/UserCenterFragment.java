package vn.edu.neu.veaknaz.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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

    var shimmer = requireView().<ShimmerLayout>findViewById(R.id.usercenter_shimmer_layout);
    var userCenter = requireView().findViewById(R.id.usercenter_main_layout);
    shimmer.startShimmerAnimation();

    AntUserInfoClient.getInstance().getCurrentUserInfo(new AntUserInfoClient.UserInfoEventListener() {
      @Override
      public void onGetUserInfoSuccess(UserInfoView userInfo) {
        requireActivity().runOnUiThread(() -> {
          shimmer.stopShimmerAnimation();
          shimmer.setVisibility(View.GONE);
          userCenter.setVisibility(View.VISIBLE);
          var username = "@" + userInfo.getUsername();
          requireView().<TextView>findViewById(R.id.usercenter_username).setText(username);

          var displayName = Optional.ofNullable(userInfo.getFirstname()).orElse("") + " " + Optional.ofNullable(userInfo.getLastname()).orElse("");
          if (displayName.trim().isEmpty()) {
            displayName = getString(R.string.usercenter_displayname_default);
          }
          requireView().<TextView>findViewById(R.id.usercenter_displayname).setText(displayName.trim());
        });

      }

      @Override
      public void onGetUserInfoFailed() {
        Log.e("UserCenterFragment", "Failed to get user info");
      }
    });

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_user_center, container, false);
  }
}
package vn.edu.neu.veaknaz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.Optional;

import io.supercharge.shimmerlayout.ShimmerLayout;
import vn.edu.neu.veaknaz.R;

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
    Optional.of(requireView().<ShimmerLayout>findViewById(R.id.usercenter_shimmer_layout))
        .ifPresent(ShimmerLayout::startShimmerAnimation);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_user_center, container, false);
  }
}
package vn.edu.neu.veaknaz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.Optional;

import io.supercharge.shimmerlayout.ShimmerLayout;
import vn.edu.neu.veaknaz.R;

public class HomeFragment extends Fragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onResume() {
    super.onResume();

    Optional.ofNullable(requireView().<LinearLayout>findViewById(R.id.home_fragment_loading_layout))
        .ifPresent(e -> {
          if (e.getVisibility() == View.VISIBLE) {
            for (int i = 0; i < e.getChildCount(); i++) {
              var child = e.getChildAt(i);
              if (child instanceof ShimmerLayout) {
                ((ShimmerLayout) child).startShimmerAnimation();
              }
            }
          }
        });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

}
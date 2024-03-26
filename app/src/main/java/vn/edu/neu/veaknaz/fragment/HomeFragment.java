package vn.edu.neu.veaknaz.fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    DisplayMetrics displayMetrics = requireView().getResources().getDisplayMetrics();
    float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

    LinearLayout shimmer_container = requireView()
        .findViewById(R.id.shimmer_list_container);

    for (int i = 0; i < dpHeight / 70; ++i) {
      TextView tv = new TextView(requireContext());
      tv.setText("");
      tv.setBackgroundResource(R.color.background_dimmed);
      var params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (60 * displayMetrics.density));
      params.setMargins(0, 5, 0, 5);
      tv.setLayoutParams(params);
      shimmer_container.addView(tv);
    }
  }

  @Override
  public void onResume() {
    super.onResume();

    Optional.of(requireView().<ShimmerLayout>findViewById(R.id.home_shimmer_layout))
        .ifPresent(ShimmerLayout::startShimmerAnimation);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

}
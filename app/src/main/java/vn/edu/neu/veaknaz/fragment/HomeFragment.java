package vn.edu.neu.veaknaz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

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
    changeMenu();
    Optional.ofNullable(requireView().<LinearLayout>findViewById(R.id.fragment_home_loading_layout))
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

  private void changeMenu() {
    Optional.ofNullable(requireActivity().<NavigationView>findViewById(R.id.nav_view))
        .ifPresent(navView -> {
          navView.getMenu().clear();
          navView.inflateMenu(R.menu.activity_main_menu_fragment_usercenter);
        });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

}
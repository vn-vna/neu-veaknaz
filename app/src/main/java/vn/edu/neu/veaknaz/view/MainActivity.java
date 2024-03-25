package vn.edu.neu.veaknaz.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.client.AntAuthenticationClient;
import vn.edu.neu.veaknaz.fragment.GroupCenterFragment;
import vn.edu.neu.veaknaz.fragment.HomeFragment;
import vn.edu.neu.veaknaz.fragment.NotificationFragment;
import vn.edu.neu.veaknaz.fragment.UserCenterFragment;
import vn.edu.neu.veaknaz.util.StateController;

public class MainActivity extends AppCompatActivity {

  public MainActivity() {
    super();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    var authenticationClient = AntAuthenticationClient.getInstance();
    var isCovered = !authenticationClient.getUserToken().isPresent();

    if (isCovered) {
      startActivity(new Intent(this, WelcomeActivity.class));
      finish();
    } else {
      pagerAdapter = new MainActivityPagerAdapter(this);
      this.<ViewPager2>findViewById(R.id.main_activity_pager)
          .setAdapter(pagerAdapter);

      new TabLayoutMediator(
          this.findViewById(R.id.home_tab_navigation),
          this.findViewById(R.id.main_activity_pager),
          (tab, position) -> {
            switch (position) {
              case 0:
                tab.setIcon(R.drawable.ic_bottom_navigation_home);
                break;
              case 1:
                tab.setIcon(R.drawable.ic_bottom_navigation_chat);
                break;
              case 2:
                tab.setIcon(R.drawable.ic_bottom_navigation_notification);
                break;
              case 3:
                tab.setIcon(R.drawable.ic_bottom_navigation_user);
                break;
            }
          }).attach();
    }
  }

  MainActivityPagerAdapter pagerAdapter;

  class MainActivityPagerAdapter extends FragmentStateAdapter {
    public MainActivityPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
      super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      switch (position) {
        case 0:
          return new HomeFragment();
        case 1:
          return new GroupCenterFragment();
        case 2:
          return new NotificationFragment();
        case 3:
          return new UserCenterFragment();
        default:
          throw new IllegalArgumentException("Invalid position: " + position);
      }
    }

    @Override
    public int getItemCount() {
      return 4;
    }
  }

}
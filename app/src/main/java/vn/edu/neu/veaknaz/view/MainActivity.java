package vn.edu.neu.veaknaz.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Optional;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.client.AntAuthenticationClient;
import vn.edu.neu.veaknaz.fragment.GroupCenterFragment;
import vn.edu.neu.veaknaz.fragment.HomeFragment;
import vn.edu.neu.veaknaz.fragment.NotificationFragment;
import vn.edu.neu.veaknaz.fragment.UserCenterFragment;

public class MainActivity extends AppCompatActivity {

  public MainActivity() {
    super();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    var token = AntAuthenticationClient.getInstance().getUserToken();
    if (token.isPresent()) {
      verifyToken();
    }

    attachViewPager();
  }

  @Override
  protected void onResume() {
    super.onResume();
    verifyToken();
    configureNavigationDrawer();
  }

  private void verifyToken() {
    AntAuthenticationClient.getInstance().verifyToken(new OnVerifyTokenListener());
  }

  private void attachViewPager() {
    this.<ViewPager2>findViewById(R.id.main_activity_pager)
        .setAdapter(new MainActivityPagerAdapter(this));

    Optional.ofNullable(findViewById(R.id.home_tab_navigation))
        .ifPresent((tab_layout) -> {
          new TabLayoutMediator(
              (TabLayout) tab_layout,
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
        });
  }

  private void configureNavigationDrawer() {
    Optional
        .ofNullable(this.<NavigationView>findViewById(R.id.main_drawer_navigation))
        .ifPresent((navigationView) -> {
          navigationView.setNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
            }
            return true;
          });
        });
  }


  public static class MainActivityPagerAdapter extends FragmentStateAdapter {
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

  class OnVerifyTokenListener implements AntAuthenticationClient.VerifyTokenListener {
    @Override
    public void onVerifySuccess() {
    }

    @Override
    public void onVerifyFailed() {
      AntAuthenticationClient.getInstance().signOut(() -> {
        var intent = new Intent(MainActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
      });
    }
  }


}
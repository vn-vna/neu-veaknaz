package vn.edu.neu.veaknaz.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;
import java.util.Optional;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.client.AntAuthenticationClient;
import vn.edu.neu.veaknaz.fragment.GroupCenterFragment;
import vn.edu.neu.veaknaz.fragment.NotificationFragment;
import vn.edu.neu.veaknaz.fragment.UserCenterFragment;
import vn.edu.neu.veaknaz.util.LocaleManager;
import vn.edu.neu.veaknaz.util.SavedConfiguration;

public class MainActivity extends AppCompatActivity {

  public MainActivity() {
    super();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    localeManager = new LocaleManager(this);
    lastSelectedMenuItem = new SavedConfiguration<>("menu_configuration", "last_selected");

    var token = AntAuthenticationClient.getInstance().getUserToken();
    if (token.isPresent()) {
      verifyToken();
    }

    attachViewPager();
    configureNavigationDrawer();
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
    var viewPager = this.<ViewPager2>findViewById(R.id.main_activity_pager);
    viewPager.setOffscreenPageLimit(2);
    viewPager.setAdapter(new MainActivityPagerAdapter(this));

    Optional.ofNullable(this.<TabLayout>findViewById(R.id.home_tab_navigation))
        .ifPresent((tab_layout) -> {
          new TabLayoutMediator(
              tab_layout,
              this.findViewById(R.id.main_activity_pager),
              (tab, position) -> {
                switch (position) {
                  case 0:
                    tab.setIcon(R.drawable.ic_bottom_navigation_chat);
                    break;
                  case 1:
                    tab.setIcon(R.drawable.ic_bottom_navigation_notification);
                    break;
                  case 2:
                    tab.setIcon(R.drawable.ic_bottom_navigation_user);
                    break;
                }
              }).attach();

          tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            void setAppTitle(TabLayout.Tab tab) {
              int titleId = switch (tab.getPosition()) {
                case 0 -> R.string.activity_main_title_groups;
                case 1 -> R.string.activity_main_title_messages;
                case 2 -> R.string.activity_main_title_usercenter;
                default -> 0;
              };
              MainActivity.this.<TextView>findViewById(R.id.activity_main_title).setText(titleId);
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
              setAppTitle(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
              setAppTitle(tab);
            }
          });

          Objects
              .requireNonNull(tab_layout.getTabAt(lastSelectedMenuItem.getValue().orElse(0)))
              .select();
        });
  }

  private void configureNavigationDrawer() {
    Optional.<DrawerLayout>ofNullable(findViewById(R.id.activity_main_drawer))
        .ifPresent(drawer -> {
          drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.activity_main_accessibility_drawer_open, R.string.activity_main_accessibility_drawer_close);
          drawer.addDrawerListener(drawerToggle);
          drawerToggle.syncState();

          Optional.<ImageButton>ofNullable(findViewById(R.id.activity_main_button_toggle_drawer))
              .ifPresent(button -> {
                button.setOnClickListener((view) -> {
                  if (drawer.isDrawerOpen(findViewById(R.id.nav_view))) {
                    drawer.close();
                  } else {
                    drawer.open();
                  }
                });
              });
        });
  }

  public LocaleManager getLocaleManager() {
    return localeManager;
  }

  private ActionBarDrawerToggle drawerToggle;
  private SavedConfiguration<Integer> lastSelectedMenuItem;
  private LocaleManager localeManager;

  public static class MainActivityPagerAdapter extends FragmentStateAdapter {
    public MainActivityPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
      super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      return switch (position) {
        case 0 -> new GroupCenterFragment();
        case 1 -> new NotificationFragment();
        case 2 -> new UserCenterFragment();
        default -> throw new IllegalArgumentException("Invalid position: " + position);
      };
    }

    @Override
    public int getItemCount() {
      return 3;
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
package vn.edu.neu.veaknaz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Optional;

import io.supercharge.shimmerlayout.ShimmerLayout;
import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.client.AntGroupClient;
import vn.edu.neu.veaknaz.client.model.group.GroupInfoView;
import vn.edu.neu.veaknaz.controller.group.GroupItemModel;
import vn.edu.neu.veaknaz.controller.group.GroupListAdapter;

public class GroupCenterFragment extends Fragment {

  public GroupCenterFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    groupListAdapter = new GroupListAdapter(this.requireActivity());

    Optional.<LinearLayout>ofNullable(requireView().findViewById(R.id.fragment_group_center_loading_layout))
        .ifPresent(loadingLayout -> {
          loadingLayout.setVisibility(View.VISIBLE);
          for (int i = 0; i < loadingLayout.getChildCount(); i++) {
            var child = loadingLayout.getChildAt(i);
            if (child instanceof ShimmerLayout) {
              ((ShimmerLayout) child).startShimmerAnimation();
            }
          }
        });

    groupListView = requireView().findViewById(R.id.group_center_recycler_view);
    groupListView.setAdapter(groupListAdapter);
    groupListView.setLayoutManager(new LinearLayoutManager(this.requireActivity()));
  }

  @Override
  public void onResume() {
    super.onResume();
    changeMenu();
    refreshGroupList();
  }

  private void changeMenu() {
    Optional.ofNullable(requireActivity().<NavigationView>findViewById(R.id.nav_view))
        .ifPresent(navView -> {
          navView.getMenu().clear();
          navView.inflateMenu(R.menu.activity_main_menu_fragment_group);
        });
  }

  private void refreshGroupList() {
    AntGroupClient.getInstance()
        .getUserGroups(new AntGroupClient.GetUserGroupsListener() {
          @Override
          public void onGetSuccess(GroupInfoView infoView) {
            Optional.<LinearLayout>ofNullable(requireView().findViewById(R.id.fragment_group_center_loading_layout))
                .ifPresent(loadingLayout -> {
                  requireActivity().runOnUiThread(() -> {
                    loadingLayout.setVisibility(View.GONE);

                    var groupItems = new ArrayList<GroupItemModel>();
                    for (var group : infoView.getGroups()) {
                      var groupItem = new GroupItemModel();
                      groupItem.setGroupName(group.getGroupName());
                      groupItem.setGroupDescription(group.getLatestMessage().getContent());
                      groupItem.setGroupId(group.getGid());
                      groupItems.add(groupItem);
                    }

                    groupListAdapter.setGroupItems(groupItems);
                    groupListAdapter.notifyDataSetChanged();
                    groupListView.setVisibility(View.VISIBLE);
                  });
                });
          }

          @Override
          public void onGetFailed() {
          }
        });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_group_center, container, false);
  }

  private RecyclerView groupListView;

  private GroupListAdapter groupListAdapter;
}
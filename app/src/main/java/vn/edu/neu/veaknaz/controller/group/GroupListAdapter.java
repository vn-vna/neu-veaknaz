package vn.edu.neu.veaknaz.controller.group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.fragment.GroupCenterFragment;
import vn.edu.neu.veaknaz.view.ChatActivity;

public  class GroupListAdapter extends RecyclerView.Adapter<GroupListViewHolder> {
  public GroupListAdapter(Context context) {
    this.context = context;
    this.groupItems = new ArrayList<>();
  }

  public List<GroupItemModel> getGroupItems() {
    return groupItems;
  }

  public void setGroupItems(List<GroupItemModel> groupItems) {
    this.groupItems = groupItems;
  }

  @NonNull
  @Override
  public GroupListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    var inflater = LayoutInflater.from(context);
    var fragmented = inflater.inflate(R.layout.fragment_group_center_list_item, parent, false);
    return new GroupListViewHolder(fragmented);
  }

  @Override
  public void onBindViewHolder(@NonNull GroupListViewHolder holder, int position) {
    GroupItemModel groupItem = groupItems.get(position);
    holder.getGroupNameView().setText(groupItem.getGroupName());
    holder.getGroupDescriptionView().setText(groupItem.getGroupDescription());

    holder.getItemView().setOnClickListener(v -> {
      var intent = new Intent(context, ChatActivity.class);
      var bundle = new Bundle();
      bundle.putString("gid", groupItem.getGroupId());
      bundle.putString("groupName", groupItem.getGroupName());
      intent.putExtras(bundle);
      context.startActivity(intent);
    });
  }

  @Override
  public int getItemCount() {
    return groupItems.size();
  }

  private final Context context;
  List<GroupItemModel> groupItems;
}

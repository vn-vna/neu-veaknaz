package vn.edu.neu.veaknaz.controller.group;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.neu.veaknaz.R;

public class GroupListViewHolder extends RecyclerView.ViewHolder {
  public GroupListViewHolder(@NonNull View itemView) {
    super(itemView);
    groupNameView = itemView.findViewById(R.id.group_center_item_group_title);
    groupDescriptionView = itemView.findViewById(R.id.group_center_item_desc);
    this.itemView = itemView;
  }

  public TextView getGroupNameView() {
    return groupNameView;
  }

  public TextView getGroupDescriptionView() {
    return groupDescriptionView;
  }

  public View getItemView() {
    return itemView;
  }

  private final View itemView;
  private final TextView groupNameView;
  private final TextView groupDescriptionView;
}

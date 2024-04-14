package vn.edu.neu.veaknaz.controller.notification;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.neu.veaknaz.R;

public class NotificationListViewHolder extends RecyclerView.ViewHolder {
  public NotificationListViewHolder(@NonNull View itemView) {
    super(itemView);

    groupNameView = itemView.findViewById(R.id.notification_center_groupname);
    senderDisplayView = itemView.findViewById(R.id.notification_center_sender_name);
    notificationItemView = itemView;
  }

  public TextView getGroupNameView() {
    return groupNameView;
  }

  public TextView getSenderDisplayView() {
    return senderDisplayView;
  }

  public View getNotificationItemView() {
    return notificationItemView;
  }

  private final TextView groupNameView;
  private final TextView senderDisplayView;
  private final View notificationItemView;

}

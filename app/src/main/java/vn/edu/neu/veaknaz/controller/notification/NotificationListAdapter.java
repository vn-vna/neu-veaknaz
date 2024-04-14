package vn.edu.neu.veaknaz.controller.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.neu.veaknaz.R;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListViewHolder> {
  public NotificationListAdapter(Context context) {
    this.context = context;
    this.notificationItems = new ArrayList<>();
  }

  @NonNull
  @Override
  public NotificationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    var inflater = LayoutInflater.from(context);
    var fragmented = inflater.inflate(R.layout.fragment_notification_list_item, parent, false);
    return new NotificationListViewHolder(fragmented);
  }

  @Override
  public void onBindViewHolder(@NonNull NotificationListViewHolder holder, int position) {
    NotificationItemModel notificationItem = notificationItems.get(position);
    holder.getGroupNameView().setText(notificationItem.getGroupName());
    holder.getSenderDisplayView().setText(notificationItem.getSenderDisplay());

    holder.getNotificationItemView().setOnClickListener(v -> {
    });
  }

  @Override
  public int getItemCount() {
    return notificationItems.size();
  }

  public void setNotificationItems(List<NotificationItemModel> notificationItems) {
    this.notificationItems.clear();
    this.notificationItems.addAll(notificationItems);
    notifyDataSetChanged();
  }

  private final Context context;
  private final List<NotificationItemModel> notificationItems;
}

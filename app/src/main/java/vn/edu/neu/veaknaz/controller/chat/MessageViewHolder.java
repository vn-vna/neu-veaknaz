package vn.edu.neu.veaknaz.controller.chat;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.neu.veaknaz.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {
  public MessageViewHolder(@NonNull View itemView) {
    super(itemView);
    usernameView = itemView.findViewById(R.id.activity_chat_message_item_username);
    messageContentLayout = itemView.findViewById(R.id.activity_chat_message_item_consecutive_message_layout);
    lastMessageDateView = itemView.findViewById(R.id.activity_chat_last_message_sent_date);
  }

  public void bind(ConsecutiveMessageViewModel message) {
  }

  public TextView getUsernameView() {
    return usernameView;
  }

  public LinearLayout getMessageContentLayout() {
    return messageContentLayout;
  }

  public TextView getLastMessageDateView() {
    return lastMessageDateView;
  }

  private final TextView lastMessageDateView;
  private final TextView usernameView;
  private final LinearLayout messageContentLayout;
}

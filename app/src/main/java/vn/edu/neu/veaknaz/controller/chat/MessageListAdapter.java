package vn.edu.neu.veaknaz.controller.chat;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.edu.neu.veaknaz.R;

public class MessageListAdapter extends RecyclerView.Adapter<MessageViewHolder> {
  public MessageListAdapter() {
    this.messageList = new ArrayList<>();
    this.senderNameMap = Map.of();
  }

  public void setMessageList(List<ConsecutiveMessageViewModel> messageList) {
    this.messageList = messageList;
    notifyDataSetChanged();
  }

  public void setSenderNameMap(Map<String, String> senderNameMap) {
    this.senderNameMap = senderNameMap;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.layout_activity_chat_item_message, parent, false);
    return new MessageViewHolder(itemView);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
    ConsecutiveMessageViewModel message = messageList.get(position);
    var lastMessage = message.getMessageContentList().get(message.getMessageContentList().size() - 1);

    holder.getUsernameView().setText(senderNameMap.get(message.getSenderId()));
    holder.getLastMessageDateView().setText(lastMessage.getDateString());

    holder.getMessageContentLayout().removeAllViews();
    for (MessageContent messageContent : message.getMessageContentList()) {
      TextView textView = new TextView(holder.itemView.getContext());
      textView.setText(messageContent.text());
      holder.getMessageContentLayout().addView(textView);
    }
    holder.bind(message);
  }

  @Override
  public int getItemCount() {
    return messageList.size();
  }

  private Map<String, String> senderNameMap;
  private List<ConsecutiveMessageViewModel> messageList;

}

package vn.edu.neu.veaknaz.view;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.client.AntGroupClient;
import vn.edu.neu.veaknaz.client.AntMessageClient;
import vn.edu.neu.veaknaz.client.model.group.GroupMemberView;
import vn.edu.neu.veaknaz.client.model.group.MemberInfoView;
import vn.edu.neu.veaknaz.client.model.message.MessageInGroupView;
import vn.edu.neu.veaknaz.controller.chat.ConsecutiveMessageViewModel;
import vn.edu.neu.veaknaz.controller.chat.MessageContent;
import vn.edu.neu.veaknaz.controller.chat.MessageListAdapter;
import vn.edu.neu.veaknaz.fragment.InviteUserDialog;

public class ChatActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_chat);

    var bundle = getIntent().getExtras();
    if (Objects.nonNull(bundle)) {
      gid = bundle.getString("gid");
      groupName = bundle.getString("groupName");
    }

    TextView textViewGroupName = findViewById(R.id.activity_chat_group_name);
    textViewGroupName.setText(groupName);

    MessageListAdapter adapter = new MessageListAdapter();

    recyclerView = findViewById(R.id.recyclerView);
    var layoutManager = new LinearLayoutManager(this);
    layoutManager.setStackFromEnd(true);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);

    editTextMessage = findViewById(R.id.editTextMessage);
    ImageButton buttonSend = findViewById(R.id.buttonSend);

    buttonSend.setOnClickListener(v -> {
      String messageText = editTextMessage.getText().toString().trim();

      AntMessageClient.getInstance().sendMessage(gid, messageText, new AntMessageClient.SendMessageListener() {
        @Override
        public void onSendSuccess() {
          runOnUiThread(() -> {
            editTextMessage.setText("");
            refreshMessages();
          });
        }

        @Override
        public void onSendFailed() {
        }
      });
    });

    ImageButton buttonInvite = findViewById(R.id.activity_chat_button_add_member);
    buttonInvite.setOnClickListener(v -> {
      var inviteDialog = new InviteUserDialog();
      inviteDialog.setGid(gid);
      inviteDialog.show(getSupportFragmentManager(), "inviteDialog");
    });

    ImageButton buttonBack = findViewById(R.id.activity_chat_button_back);
    buttonBack.setOnClickListener(v -> finish());
  }

  @Override
  protected void onResume() {
    super.onResume();

    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        refreshMemberList();
        refreshMessages();
      }

    }, 0, 1000);
  }

  private void refreshMessages() {
    AntMessageClient.getInstance().getMessages(gid, null, null, 100, new AntMessageClient.GetMessageListener() {
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onGetSuccess(MessageInGroupView messageInGroupView) {
        runOnUiThread(() -> {
          List<ConsecutiveMessageViewModel> messageList = new ArrayList<>();
          Collections.reverse(messageInGroupView.getMessages());
          for (var message : messageInGroupView.getMessages()) {
            if (messageList.isEmpty() || !messageList.get(messageList.size() - 1).getSenderId().equals(message.getSenderId())) {
              messageList.add(new ConsecutiveMessageViewModel(message.getSenderId(), new ArrayList<>()));
            }

            var lastConsecutiveGroup = messageList.get(messageList.size() - 1);
            if (message.getSenderId().equals(lastConsecutiveGroup.getSenderId())) {
              var sentDate = Date.from(Instant.parse(message.getSentAt()));
              lastConsecutiveGroup.getMessageContentList().add(new MessageContent(sentDate, message.getContent()));
            }
          }

          ((MessageListAdapter) recyclerView.getAdapter()).setMessageList(messageList);
          recyclerView.scrollToPosition(messageList.size() - 1);
        });

      }

      @Override
      public void onGetFailed() {
        Log.d("ChatActivity", "Failed to get messages");
      }
    });
  }

  @Override
  protected void onPause() {
    super.onPause();
    timer.cancel();
  }

  private void refreshMemberList() {
    AntGroupClient.getInstance().getGroupMembers(gid, new AntGroupClient.GetGroupMemberListener() {

      @Override
      public void onGetSuccess(GroupMemberView memberView) {
        runOnUiThread(() -> {
          Map<String, String> memberList = memberView.getMembers().stream()
              .collect(Collectors.toMap(MemberInfoView::getId, member -> {
                if (Objects.nonNull(member.getName()) && !member.getName().isEmpty()) {
                  return member.getName();
                } else {
                  return "@" + member.getUsername();
                }
              }));

          ((MessageListAdapter) recyclerView.getAdapter()).setSenderNameMap(memberList);
        });
      }

      @Override
      public void onGetFailed() {
        Log.d("ChatActivity", "Failed to get members");
      }
    });
  }

  private String gid;
  private String groupName;
  private EditText editTextMessage;
  private RecyclerView recyclerView;
  private Timer timer;
}
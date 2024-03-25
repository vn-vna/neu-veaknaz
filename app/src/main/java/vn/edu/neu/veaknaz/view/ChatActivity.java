package vn.edu.neu.veaknaz.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.view.adapters.MessageListAdapter;

public class ChatActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);

    // Khởi tạo danh sách tin nhắn và adapter
    messageList = new ArrayList<>();
    adapter = new MessageListAdapter(messageList);

    // Thiết lập RecyclerView
    recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);

    // Ánh xạ các thành phần trong giao diện
    editTextMessage = findViewById(R.id.editTextMessage);
    buttonSend = findViewById(R.id.buttonSend);

    // Xử lý sự kiện khi nhấn nút "Send"
    buttonSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
          // Tạo một tin nhắn mới và thêm vào danh sách
          BaseMessage message = new BaseMessage(messageText);
          messageList.add(message);

          // Cập nhật giao diện và đặt vị trí cuối cùng của danh sách tin nhắn
          adapter.notifyDataSetChanged();
          recyclerView.smoothScrollToPosition(messageList.size() - 1);

          // Xóa nội dung tin nhắn trong EditText
          editTextMessage.setText("");
        }
      }
    });
  }
  private RecyclerView recyclerView;
  private MessageListAdapter adapter;
  private List<BaseMessage> messageList;
  private EditText editTextMessage;
  private Button buttonSend;
}
package vn.edu.neu.veaknaz.view;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import vn.edu.neu.veaknaz.R;

public class ListGroupActivity extends AppCompatActivity {

    private ListView lvGroupList;
    private List<Group> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group);

        lvGroupList = findViewById(R.id.lvGroupList);

        // Giả định danh sách nhóm hộp chứa
        groupList = new ArrayList<>();
        groupList.add(new Group("Group 1", "Latest message 1", "10:00 AM"));
        groupList.add(new Group("Group 2", "Latest message 2", "11:30 AM"));
        groupList.add(new Group("Group 3", "Latest message 3", "1:45 PM"));
        groupList.add(new Group("Group 4", "Latest message 4", "3:20 PM"));
        groupList.add(new Group("Group 5", "Latest message 5", "5:10 PM"));

        GroupListAdapter adapter = new GroupListAdapter();
        lvGroupList.setAdapter(adapter);

        lvGroupList.setOnItemClickListener((parent, view, position, id) -> {
            Group selectedGroup = groupList.get(position);
            String groupName = selectedGroup.getName();
            Toast.makeText(ListGroupActivity.this,
                    "Selected group: " + groupName,
                    Toast.LENGTH_SHORT).show();
        });
    }

    private class GroupListAdapter extends ArrayAdapter<Group> {

        public GroupListAdapter() {
            super(ListGroupActivity.this, R.layout.list_item_group, groupList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                LayoutInflater inflater = getLayoutInflater();
                itemView = inflater.inflate(R.layout.list_item_group, parent, false);
            }

            Group currentGroup = groupList.get(position);

            TextView tvGroupName = itemView.findViewById(R.id.tvGroupName);
            TextView tvLatestMessage = itemView.findViewById(R.id.tvLatestMessage);
            TextView tvMessageTime = itemView.findViewById(R.id.tvMessageTime);

            tvGroupName.setText(currentGroup.getName());
            tvLatestMessage.setText(currentGroup.getLatestMessage());
            tvMessageTime.setText(currentGroup.getMessageTime());

            return itemView;
        }
    }

    private class Group {
        private String name;
        private String latestMessage;
        private String messageTime;

        public Group(String name, String latestMessage, String messageTime) {
            this.name = name;
            this.latestMessage = latestMessage;
            this.messageTime = messageTime;
        }

        public String getName() {
            return name;
        }

        public String getLatestMessage() {
            return latestMessage;
        }

        public String getMessageTime() {
            return messageTime;
        }
    }
}
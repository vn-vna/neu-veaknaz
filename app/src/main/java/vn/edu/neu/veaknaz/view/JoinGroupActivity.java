package vn.edu.neu.veaknaz.view;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.edu.neu.veaknaz.R;

public class JoinGroupActivity extends AppCompatActivity {

    private EditText etGroupId;
    private Button btnJoinGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        etGroupId = findViewById(R.id.etGroupId);
        btnJoinGroup = findViewById(R.id.btnJoinGroup);

        btnJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupId = etGroupId.getText().toString();

                // Thực hiện xử lý tham gia nhóm hộp chứa tại đây

                Toast.makeText(JoinGroupActivity.this,
                        "Joined group: " + groupId,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
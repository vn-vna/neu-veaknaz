package vn.edu.neu.veaknaz.view;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.neu.veaknaz.R;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText etGroupName, etGroupDescription;
    private Button btnCreateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        etGroupName = findViewById(R.id.etGroupName);
        etGroupDescription = findViewById(R.id.etGroupDescription);
        btnCreateGroup = findViewById(R.id.btnCreateGroup);

        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = etGroupName.getText().toString();
                String groupDescription = etGroupDescription.getText().toString();

                // Thực hiện xử lý tạo nhóm hộp chứa tại đây

                Toast.makeText(CreateGroupActivity.this,
                        "Group created: " + groupName + "\nDescription: " + groupDescription,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
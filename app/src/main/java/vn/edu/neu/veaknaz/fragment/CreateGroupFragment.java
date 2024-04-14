package vn.edu.neu.veaknaz.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import vn.edu.neu.veaknaz.R;

public class CreateGroupFragment extends Fragment {

    private EditText etGroupName;
    private Button btnCreateGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_group, container, false);

        etGroupName = view.findViewById(R.id.etGroupName);
        btnCreateGroup = view.findViewById(R.id.btnCreateGroup);

        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = etGroupName.getText().toString();

                // Thực hiện xử lý tạo nhóm hộp chứa tại đây

                Toast.makeText(getActivity(),
                        "Group created: " + groupName,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
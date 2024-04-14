package vn.edu.neu.veaknaz.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import vn.edu.neu.veaknaz.R;

public class CreateGroupDialog extends DialogFragment {

    private EditText etGroupName;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_create_group, null);

        etGroupName = view.findViewById(R.id.etGroupName);
        Button btnCreateGroup = view.findViewById(R.id.btnCreateGroup);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = etGroupName.getText().toString();

                // Thực hiện xử lý tạo nhóm hộp chứa tại đây

                Toast.makeText(getActivity(),
                        "Group created: " + groupName,
                        Toast.LENGTH_SHORT).show();

                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
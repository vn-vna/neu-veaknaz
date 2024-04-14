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

public class InviteUserDialog extends DialogFragment {

    private EditText etUserName;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_invite_user, null);

        etUserName = view.findViewById(R.id.etUserName);
        Button btnCreateGroup = view.findViewById(R.id.btnInviteUser);
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
                String userName = etUserName.getText().toString();

                // Thực hiện xử lý tạo nhóm hộp chứa tại đây

                Toast.makeText(getActivity(),
                        "You invited: " + userName,
                        Toast.LENGTH_SHORT).show();

                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
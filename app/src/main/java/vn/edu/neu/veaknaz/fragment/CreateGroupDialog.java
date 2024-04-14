package vn.edu.neu.veaknaz.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
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
import vn.edu.neu.veaknaz.client.AntGroupClient;

public class CreateGroupDialog extends DialogFragment {

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    LayoutInflater inflater = requireActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.activity_create_group, null);

    etGroupName = view.findViewById(R.id.etGroupName);
    Button btnCreateGroup = view.findViewById(R.id.btnCreateGroup);
    Button btnCancel = view.findViewById(R.id.btnCancel);

    btnCancel.setOnClickListener(v -> dismiss());

    btnCreateGroup.setOnClickListener(v -> {
      String groupName = etGroupName.getText().toString();

      AntGroupClient.getInstance()
          .createGroup(groupName, new AntGroupClient.CreateGroupListener() {
            @Override
            public void onCreateSuccess() {
              requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Create group success", Toast.LENGTH_SHORT).show();
                getListener().onCreateGroupSuccess();
              });
            }

            @Override
            public void onCreateFailed() {
              requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Create group failed", Toast.LENGTH_SHORT).show();
                getListener().onCreateGroupFailed();
              });
            }
          });

      dismiss();
    });

    builder.setView(view);
    return builder.create();
  }

  public CreateGroupDialogListener getListener() {
    return listener;
  }

  public void setListener(CreateGroupDialogListener listener) {
    this.listener = listener;
  }

  private EditText etGroupName;
  private CreateGroupDialogListener listener;

  public interface CreateGroupDialogListener {
    void onCreateGroupSuccess();

    void onCreateGroupFailed();
  }
}
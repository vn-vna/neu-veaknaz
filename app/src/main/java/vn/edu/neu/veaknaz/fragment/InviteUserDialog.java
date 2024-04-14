package vn.edu.neu.veaknaz.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.client.AntInvitationClient;

public class InviteUserDialog extends DialogFragment {

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    LayoutInflater inflater = requireActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.fragment_invite_user, null);

    etUserName = view.findViewById(R.id.etUserName);
    Button btnCreateGroup = view.findViewById(R.id.btnInviteUser);
    Button btnCancel = view.findViewById(R.id.btnCancel);

    btnCancel.setOnClickListener(v -> dismiss());

    btnCreateGroup.setOnClickListener(v -> {
      String userName = etUserName.getText().toString();

      AntInvitationClient
          .getInstance()
          .sendInvitation(gid, userName, new AntInvitationClient.SendInvitationListener() {
            @Override
            public void onSendSuccess() {
            }

            @Override
            public void onSendFailed() {
            }
          });

      dismiss();
    });

    builder.setView(view);
    return builder.create();
  }

  public void setGid(String gid) {
    this.gid = gid;
  }

  private EditText etUserName;
  private String gid;
}
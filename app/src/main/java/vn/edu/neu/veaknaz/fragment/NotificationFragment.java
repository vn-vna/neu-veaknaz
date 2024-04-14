package vn.edu.neu.veaknaz.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.stream.Collectors;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.client.AntInvitationClient;
import vn.edu.neu.veaknaz.client.model.invitation.UserInvitationView;
import vn.edu.neu.veaknaz.controller.notification.NotificationItemModel;
import vn.edu.neu.veaknaz.controller.notification.NotificationListAdapter;

public class NotificationFragment extends Fragment {
  public NotificationFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_notification, container, false);
  }

  @Override
  public void onResume() {
    super.onResume();

    refreshNotificationList();
  }

  private void refreshNotificationList() {
    AntInvitationClient.getInstance()
        .getInvitations(new AntInvitationClient.GetInvitationsListener() {
          @Override
          public void onGetSuccess(UserInvitationView invitations) {
            var notificationItems = invitations.getInvitations().stream()
                .map(invitation -> new NotificationItemModel(
                    invitation.getGroup().getName(),
                    invitation.getSender().getDisplayName()
                ))
                .collect(Collectors.toList());

            notificationListAdapter.setNotificationItems(notificationItems);
          }

          @Override
          public void onGetFailed() {
            Log.e("NotificationFragment", "Failed to get invitations");
          }
        });
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    notificationListAdapter = new NotificationListAdapter(requireContext());
    recyclerView = view.findViewById(R.id.notification_center_recycler_view);

    recyclerView.setAdapter(notificationListAdapter);
  }

  private RecyclerView recyclerView;
  private NotificationListAdapter notificationListAdapter;
}
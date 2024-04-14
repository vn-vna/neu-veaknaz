package vn.edu.neu.veaknaz.client.model.invitation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserInvitationView {
  public List<InvitationDataView> getInvitations() {
    return invitations;
  }
  @JsonProperty("invitations")
  List<InvitationDataView> invitations;
}

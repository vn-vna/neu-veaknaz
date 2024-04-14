package vn.edu.neu.veaknaz.client.model.invitation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvitationDataView {
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public InvitationSenderView getSender() {
    return sender;
  }

  public void setSender(InvitationSenderView sender) {
    this.sender = sender;
  }

  public InvitationGroupView getGroup() {
    return group;
  }

  public void setGroup(InvitationGroupView group) {
    this.group = group;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getExpired() {
    return expired;
  }

  public void setExpired(String expired) {
    this.expired = expired;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public boolean isResponded() {
    return responded;
  }

  public void setResponded(boolean responded) {
    this.responded = responded;
  }
  @JsonProperty("id")
  private String id;
  @JsonProperty("sender")
  private InvitationSenderView sender;

  @JsonProperty("group")
  private InvitationGroupView group;

  @JsonProperty("created")
  private String created;

  @JsonProperty("expired")
  private String expired;

  @JsonProperty("accepted")
  private boolean accepted;

  @JsonProperty("responded")
  private boolean responded;

}

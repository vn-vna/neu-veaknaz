package vn.edu.neu.veaknaz.client.model.invitation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvitationSenderView {
  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getDisplayName() {
    if (firstname != null && lastname != null) {
      return (firstname + " " + lastname).trim();
    } else {
      return username;
    }
  }

  @JsonProperty("uid")
  private String uid;
  @JsonProperty("firstname")
  private String firstname;
  @JsonProperty("lastname")
  private String lastname;
  @JsonProperty("username")
  private String username;

}

package vn.edu.neu.veaknaz.client;

import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.edu.neu.veaknaz.client.model.ApiResponse;
import vn.edu.neu.veaknaz.client.model.invitation.UserInvitationView;

public class AntInvitationClient extends ApiClient<AntInvitationClient.AntInvitationRepository> {

  protected AntInvitationClient() {
    super(AntInvitationRepository.class);
  }

  public static AntInvitationClient getInstance() {
    if (Objects.isNull(instance)) {
      synchronized (AntInvitationClient.class) {
        if (Objects.isNull(instance)) {
          instance = new AntInvitationClient();
        }
      }
    }
    return instance;
  }

  public void getInvitations(GetInvitationsListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var result = getRepository().getInvitations(token).execute();
        if (result.isSuccessful()) {
          listener.onGetSuccess(Objects.requireNonNull(result.body()).getResult());
        } else {
          listener.onGetFailed();
        }
      } catch (Exception e) {
        listener.onGetFailed();
      }
    });
  }

  public void acceptInvitation(String invitationId, AcceptInvitationListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        getRepository().acceptInvitation(token, invitationId);
        listener.onAcceptSuccess();
      } catch (Exception e) {
        listener.onAcceptFailed();
      }
    });
  }

  public void rejectInvitation(String invitationId, RejectInvitationListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        getRepository().rejectInvitation(token, invitationId);
        listener.onRejectSuccess();
      } catch (Exception e) {
        listener.onRejectFailed();
      }
    });
  }

  public void sendInvitation(String gid, String username, SendInvitationListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("gid", gid)
            .addFormDataPart("username", username)
            .build();
        getRepository().sendInvitation(token, body);
        listener.onSendSuccess();
      } catch (Exception e) {
        listener.onSendFailed();
      }
    });
  }

  private static AntInvitationClient instance;

  public interface AntInvitationRepository {
    @GET("/api/invitations")
    Call<ApiResponse<UserInvitationView>> getInvitations(
        @Header("USER_TOKEN") String userToken);

    @PUT("/api/invitations/{invitationId}/accept")
    void acceptInvitation(
        @Header("USER_TOKEN") String userToken,
        @Path("invitationId") String invitationId);

    @PUT("/api/invitations/{invitationId}/reject")
    void rejectInvitation(
        @Header("USER_TOKEN") String userToken,
        @Path("invitationId") String invitationId);

    @POST("/api/invitations")
    void sendInvitation(
        @Header("USER_TOKEN") String userToken,
        @Body RequestBody body);
  }

  public interface GetInvitationsListener {
    void onGetSuccess(UserInvitationView invitations);

    void onGetFailed();
  }

  public interface AcceptInvitationListener {
    void onAcceptSuccess();

    void onAcceptFailed();
  }

  public interface RejectInvitationListener {
    void onRejectSuccess();

    void onRejectFailed();
  }

  public interface SendInvitationListener {
    void onSendSuccess();

    void onSendFailed();
  }
}

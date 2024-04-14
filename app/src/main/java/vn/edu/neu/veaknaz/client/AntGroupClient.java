package vn.edu.neu.veaknaz.client;

import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.neu.veaknaz.client.model.ApiResponse;
import vn.edu.neu.veaknaz.client.model.group.GroupInfoView;
import vn.edu.neu.veaknaz.client.model.group.GroupMemberView;

public class AntGroupClient extends ApiClient<AntGroupClient.AntGroupRepository> {
  public AntGroupClient() {
    super(AntGroupRepository.class);
  }

  public static AntGroupClient getInstance() {
    if (Objects.isNull(instance)) {
      synchronized (AntGroupClient.class) {
        if (Objects.isNull(instance)) {
          instance = new AntGroupClient();
        }
      }
    }

    return instance;
  }

  public void createGroup(String name, CreateGroupListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", name)
            .build();
        var result = getRepository().create(token, body).execute();
        if (result.isSuccessful()) {
          listener.onCreateSuccess();
        } else {
          listener.onCreateFailed();
        }
      } catch (Exception e) {
        listener.onCreateFailed();
      }
    });
  }

  public void getUserGroups(GetUserGroupsListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var result = getRepository().getGroups(token).execute();
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

  public void getGroupMembers(String gid, GetGroupMemberListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var result = getRepository().getMembers(token, gid).execute();
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

  private static AntGroupClient instance;

  public interface AntGroupRepository {
    @POST("/api/groups")
    Call<ApiResponse<Boolean>> create(
        @Header("USER_TOKEN") String token,
        @Body RequestBody body);

    @GET("/api/groups")
    Call<ApiResponse<GroupInfoView>> getGroups(
        @Header("USER_TOKEN") String token);

    @GET("/api/groups/{gid}/members")
    Call<ApiResponse<GroupMemberView>> getMembers(
        @Header("USER_TOKEN") String token,
        @Path("gid") String gid);
  }

  public interface CreateGroupListener {
    void onCreateSuccess();

    void onCreateFailed();
  }

  public interface GetUserGroupsListener {
    void onGetSuccess(GroupInfoView infoView);

    void onGetFailed();
  }

  public interface GetGroupMemberListener {
    void onGetSuccess(GroupMemberView memberView);

    void onGetFailed();
  }
}

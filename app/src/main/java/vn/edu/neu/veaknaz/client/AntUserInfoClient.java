package vn.edu.neu.veaknaz.client;

import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.neu.veaknaz.client.model.ApiResponse;
import vn.edu.neu.veaknaz.client.model.user.UserInfoView;
import vn.edu.neu.veaknaz.client.model.user.UserSearchView;

public class AntUserInfoClient extends ApiClient<AntUserInfoClient.AntUserInfoRepository> {
  public AntUserInfoClient() {
    super(AntUserInfoRepository.class);
  }

  public static AntUserInfoClient getInstance() {
    if (Objects.isNull(instance)) {
      synchronized (AntUserInfoClient.class) {
        if (Objects.isNull(instance)) {
          instance = new AntUserInfoClient();
        }
      }
    }
    return instance;
  }

  public void getCurrentUserInfo(UserInfoEventListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var result = getRepository().getCurrentUserInfo(token).execute();
        var userInfo = Objects.requireNonNull(result.body()).getResult();
        listener.onGetUserInfoSuccess(userInfo);
      } catch (Exception e) {
        listener.onGetUserInfoFailed();
      }
    });
  }

  public void getUserInfo(String uid, UserInfoEventListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var result = getRepository().getUserInfo(token, uid).execute();
        var userInfo = Objects.requireNonNull(result.body()).getResult();
        listener.onGetUserInfoSuccess(userInfo);
      } catch (Exception e) {
        listener.onGetUserInfoFailed();
      }
    });
  }

  public void updateUserInfo(RequestBody userInfo, UserUpdateEventListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var result = getRepository().updateUserInfo(token, userInfo).execute();
        var success = Objects.requireNonNull(result.body()).getResult();
        if (success) {
          listener.onUpdateUserInfoSuccess();
        } else {
          listener.onUpdateUserInfoFailed();
        }
      } catch (Exception e) {
        listener.onUpdateUserInfoFailed();
      }
    });
  }

  public void updatePassword(RequestBody password, UserUpdateEventListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var result = getRepository().updatePassword(token, password).execute();
        var success = Objects.requireNonNull(result.body()).getResult();
        if (success) {
          listener.onUpdateUserInfoSuccess();
        } else {
          listener.onUpdateUserInfoFailed();
        }
      } catch (Exception e) {
        listener.onUpdateUserInfoFailed();
      }
    });
  }

  public void searchUser(String username, int limit, UserSearchEventListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var result = getRepository().searchUser(token, username, limit).execute();
        var userSearchView = Objects.requireNonNull(result.body()).getResult();
        listener.onSearchUserSuccess(userSearchView);
      } catch (Exception e) {
        listener.onSearchUserFailed();
      }
    });
  }

  private static AntUserInfoClient instance;

  public interface AntUserInfoRepository {
    @GET("/api/users")
    Call<ApiResponse<UserInfoView>> getCurrentUserInfo(@Header("USER_TOKEN") String userToken);

    @GET("/api/users/{uid}")
    Call<ApiResponse<UserInfoView>> getUserInfo(@Header("USER_TOKEN") String userToken, @Path("uid") String uid);

    @PUT("/api/users")
    Call<ApiResponse<Boolean>> updateUserInfo(@Header("USER_TOKEN") String userToken, @Body RequestBody userInfo);

    @PUT("/api/users")
    Call<ApiResponse<Boolean>> updatePassword(@Header("USER_TOKEN") String userToken, @Body RequestBody password);

    @GET("/api/users/search/username")
    Call<ApiResponse<UserSearchView>> searchUser(@Header("USER_TOKEN") String userToken, @Query("q") String username, @Query("lim") int limit);
  }

  public interface UserInfoEventListener {
    void onGetUserInfoSuccess(UserInfoView userInfo);

    void onGetUserInfoFailed();
  }

  public interface UserUpdateEventListener {
    void onUpdateUserInfoSuccess();

    void onUpdateUserInfoFailed();
  }

  public interface UserSearchEventListener {
    void onSearchUserSuccess(UserSearchView userSearchView);

    void onSearchUserFailed();
  }
}

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
import retrofit2.http.Query;
import vn.edu.neu.veaknaz.client.model.ApiResponse;
import vn.edu.neu.veaknaz.client.model.message.MessageInGroupView;

public class AntMessageClient extends ApiClient<AntMessageClient.AntMessageRepository> {
  public AntMessageClient() {
    super(AntMessageRepository.class);
  }

  public static AntMessageClient getInstance() {
    if (Objects.isNull(instance)) {
      synchronized (AntMessageClient.class) {
        if (Objects.isNull(instance)) {
          instance = new AntMessageClient();
        }
      }
    }

    return instance;
  }

  public void getMessages(String groupId, String timeFrom, String timeTo, int size, GetMessageListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var result = getRepository().getMessages(token, groupId, timeFrom, timeTo, size).execute();
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

  public void sendMessage(String groupId, String content, SendMessageListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = AntAuthenticationClient.getInstance().getUserToken().get();
        var body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("content", content)
            .build();

        var result = getRepository().sendMessage(token, groupId, body).execute();
        if (result.isSuccessful()) {
          listener.onSendSuccess();
        } else {
          listener.onSendFailed();
        }
      } catch (Exception e) {
        listener.onSendFailed();
      }
    });
  }

  private static AntMessageClient instance;

  public interface AntMessageRepository {
    @GET("/api/messages/{gid}")
    Call<ApiResponse<MessageInGroupView>> getMessages(
        @Header("USER_TOKEN") String token,
        @Path("gid") String groupId,
        @Query("tf") String timeFrom,
        @Query("tt") String timeTo,
        @Query("sz") int size);

    @POST("/api/messages/{gid}")
    Call<ApiResponse<Void>> sendMessage(
        @Header("USER_TOKEN") String token,
        @Path("gid") String groupId,
        @Body RequestBody body);
  }

  public interface GetMessageListener {
    void onGetSuccess(MessageInGroupView messageInGroupView);

    void onGetFailed();
  }

  public interface SendMessageListener {
    void onSendSuccess();

    void onSendFailed();
  }
}

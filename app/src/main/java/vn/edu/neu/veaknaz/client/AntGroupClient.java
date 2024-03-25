package vn.edu.neu.veaknaz.client;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.application.VeaknazApplication;
import vn.edu.neu.veaknaz.client.model.ApiResponse;
import vn.edu.neu.veaknaz.client.model.group.GroupInfoView;
import vn.edu.neu.veaknaz.client.model.group.GroupMemberView;

public class AntGroupClient {
  public AntGroupClient() {
    String baseUrl = VeaknazApplication.getInstance().getBaseContext().getString(R.string.ant_base_url) + "api/groups/";

    Retrofit retrofit = new Retrofit.Builder()
        .addConverterFactory(JacksonConverterFactory.create())
        .baseUrl(baseUrl)
        .build();

    repository = retrofit.create(AntGroupRepository.class);
    executor = Executors.newCachedThreadPool();
  }

  public Future<String> createGroup(String groupName) {
    return executor.submit(() -> {
      AntAuthenticationClient auth = AntAuthenticationClient.getInstance();

      if (!auth.getUserToken().isPresent()) {
        throw new RuntimeException("Authentication ERROR");
      }

      String token = auth.getUserToken().get();
      RequestBody body = new MultipartBody.Builder()
          .setType(MultipartBody.FORM)
          .addFormDataPart("name", groupName)
          .build();
      try {
        ApiResponse<String> result = repository.create(token, body)
            .execute().body();

        return Objects.requireNonNull(result).getResult();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  public Future<GroupInfoView> getUserGroups() {
    return executor.submit(() -> {
      AntAuthenticationClient auth = AntAuthenticationClient.getInstance();

      if (!auth.getUserToken().isPresent()) {
        throw new RuntimeException("Authentication ERROR");
      }

      String token = auth.getUserToken().get();
      try {
        ApiResponse<GroupInfoView> result = repository.getGroups(token)
            .execute().body();

        return Objects.requireNonNull(result).getResult();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  public Future<GroupMemberView> getMembers(String gid) {
    return executor.submit(() -> {
      AntAuthenticationClient auth = AntAuthenticationClient.getInstance();

      if (!auth.getUserToken().isPresent()) {
        throw new RuntimeException("Authentication ERROR");
      }

      String token = auth.getUserToken().get();
      try {
        ApiResponse<GroupMemberView> result = repository.getMembers(token, gid)
            .execute().body();

        return Objects.requireNonNull(result).getResult();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private final AntGroupRepository repository;
  private final ExecutorService executor;

  public interface AntGroupRepository {
    @POST
    Call<ApiResponse<String>> create(@Header("USER_TOKEN") String token, @Body RequestBody body);

    @GET
    Call<ApiResponse<GroupInfoView>> getGroups(@Header("USER_TOKEN") String token);

    @GET("{gid}/members")
    Call<ApiResponse<GroupMemberView>> getMembers(@Header("USER_TOKEN") String token, @Path("gid") String gid);
  }
}

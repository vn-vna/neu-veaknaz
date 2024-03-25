package vn.edu.neu.veaknaz.client;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
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
import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.application.VeaknazApplication;
import vn.edu.neu.veaknaz.client.model.ApiResponse;
import vn.edu.neu.veaknaz.client.model.auth.ActionSignInResult;
import vn.edu.neu.veaknaz.client.model.auth.ActionSignUpResult;
import vn.edu.neu.veaknaz.client.model.auth.UserIdViewResult;
import vn.edu.neu.veaknaz.util.SavedConfiguration;

public class AntAuthenticationClient {

  private AntAuthenticationClient() {
    var baseUrl = VeaknazApplication.getInstance().getBaseContext().getString(R.string.ant_base_url) + "api/auth/";
    var retrofit = new Retrofit.Builder()
        .addConverterFactory(JacksonConverterFactory.create())
        .baseUrl(baseUrl)
        .build();

    repository = retrofit.create(AntAuthenticationRepository.class);
    executor = Executors.newCachedThreadPool();
    userToken = new SavedConfiguration<>("veaknaz.auth", "user_token");
  }

  public static AntAuthenticationClient getInstance() {
    if (Objects.isNull(_instance)) {
      synchronized (AntAuthenticationClient.class) {
        if (Objects.isNull(_instance)) {
          _instance = new AntAuthenticationClient();
        }
      }
    }

    return _instance;
  }

  public Future<String> signUp(String username, String password) {
    return executor.submit(() -> {
      try {
        var result = repository.signUp(
            new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .build()
        ).execute();

        return Objects.requireNonNull(result.body()).getResult().getUserId();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  public Future<String> signIn(String username, String password) {
    return executor.submit(() -> {
      try {
        var result = repository.signIn(
            new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .build()
        ).execute();

        var token = Objects.requireNonNull(result.body()).getResult().getTokenId();
        userToken.save(token);
        return token;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  public Optional<String> getUserToken() {
    return userToken.getValue();
  }

  private static AntAuthenticationClient _instance;
  private final AntAuthenticationRepository repository;
  private final ExecutorService executor;
  private final SavedConfiguration<String> userToken;

  public interface AntAuthenticationRepository {
    @POST("sign-in")
    Call<ApiResponse<ActionSignInResult>> signIn(@Body RequestBody body);

    @POST("sign-up")
    Call<ApiResponse<ActionSignUpResult>> signUp(@Body RequestBody body);

    @GET("uid")
    Call<ApiResponse<UserIdViewResult>> getUid(@Header("USER_TOKEN") String token);
  }
}

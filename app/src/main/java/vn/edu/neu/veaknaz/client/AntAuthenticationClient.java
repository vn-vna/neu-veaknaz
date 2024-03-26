package vn.edu.neu.veaknaz.client;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;

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
    executor = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
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

  public void signUp(String username, String password, SignUpEventListener listener) {
    executor.execute(() -> {
      try {
        var result = repository.signUp(
            new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .build()
        ).execute();

        var userid = Objects.requireNonNull(result.body()).getResult().getUserId();
        listener.onSignUpSuccess();
      } catch (IOException e) {
        listener.onSignUpFailed();
      }
    });
  }

  public void login(String username, String password, LoginEventListener listener) {
    executor.execute(() -> {
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
        listener.onLoginSuccess();
      } catch (IOException e) {
        listener.onLoginFailed();
      }
    });
  }

  public void verifyToken(VerifyTokenListener listener) {
    executor.execute(() -> {
      try {
        var token = userToken.getValue().orElseThrow(() -> new RuntimeException("No token found"));
        var result = repository.getUid(token).execute();

        if (result.isSuccessful()) {
          listener.onVerifySuccess();
        } else {
          throw new RuntimeException("Invalid token");
        }
      } catch (Exception e) {
        listener.onVerifyFailed();
      }
    });
  }

  public void signOut(SignOutListener listener) {
    executor.execute(() -> {
      userToken.clear();
      listener.onSignOutSuccess();
    });
  }

  public Optional<String> getUserToken() {
    return userToken.getValue();
  }

  private static AntAuthenticationClient _instance;
  private final AntAuthenticationRepository repository;
  private final ListeningExecutorService executor;
  private final SavedConfiguration<String> userToken;


  public interface AntAuthenticationRepository {
    @POST("sign-in")
    Call<ApiResponse<ActionSignInResult>> signIn(@Body RequestBody body);

    @POST("sign-up")
    Call<ApiResponse<ActionSignUpResult>> signUp(@Body RequestBody body);

    @GET("uid")
    Call<ApiResponse<UserIdViewResult>> getUid(@Header("USER_TOKEN") String token);
  }

  public interface LoginEventListener {
    void onLoginSuccess();

    void onLoginFailed();
  }

  public interface SignUpEventListener {
    void onSignUpSuccess();

    void onSignUpFailed();
  }

  public interface VerifyTokenListener {
    void onVerifySuccess();

    void onVerifyFailed();
  }

  @FunctionalInterface
  public interface SignOutListener {
    void onSignOutSuccess();
  }
}

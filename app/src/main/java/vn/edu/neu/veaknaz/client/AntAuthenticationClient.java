package vn.edu.neu.veaknaz.client;

import java.util.Objects;
import java.util.Optional;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import vn.edu.neu.veaknaz.client.model.ApiResponse;
import vn.edu.neu.veaknaz.client.model.auth.ActionSignInResult;
import vn.edu.neu.veaknaz.client.model.auth.ActionSignUpResult;
import vn.edu.neu.veaknaz.client.model.auth.UserIdViewResult;
import vn.edu.neu.veaknaz.util.SavedConfiguration;

public class AntAuthenticationClient extends ApiClient<AntAuthenticationClient.AntAuthenticationRepository> {

  private AntAuthenticationClient() {
    super(AntAuthenticationRepository.class);
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
    getExecutor().execute(() -> {
      try {
        var result = getRepository().signUp(
            new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .build()
        ).execute();

        var userid = Objects.requireNonNull(result.body()).getResult().getUserId();

        if (userid == null) {
          throw new RuntimeException("User ID is null");
        }
        listener.onSignUpSuccess();
      } catch (Exception e) {
        listener.onSignUpFailed();
      }
    });
  }

  public void login(String username, String password, LoginEventListener listener) {
    getExecutor().execute(() -> {
      try {
        var result = getRepository().signIn(
            new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .build()
        ).execute();

        var token = Objects.requireNonNull(result.body()).getResult().getTokenId();
        userToken.save(token);
        listener.onLoginSuccess();
      } catch (Exception e) {
        listener.onLoginFailed();
      }
    });
  }

  public void verifyToken(VerifyTokenListener listener) {
    getExecutor().execute(() -> {
      try {
        var token = userToken.getValue().orElseThrow(() -> new RuntimeException("No token found"));
        var result = getRepository().getUid(token).execute();

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
    getExecutor().execute(() -> {
      userToken.clear();
      listener.onSignOutSuccess();
    });
  }

  public Optional<String> getUserToken() {
    return userToken.getValue();
  }

  private static AntAuthenticationClient _instance;
  private final SavedConfiguration<String> userToken;


  public interface AntAuthenticationRepository {
    @POST("/api/auth/sign-in")
    Call<ApiResponse<ActionSignInResult>> signIn(@Body RequestBody body);

    @POST("/api/auth/sign-up")
    Call<ApiResponse<ActionSignUpResult>> signUp(@Body RequestBody body);

    @GET("/api/auth/uid")
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

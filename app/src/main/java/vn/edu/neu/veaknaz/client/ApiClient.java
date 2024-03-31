package vn.edu.neu.veaknaz.client;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.application.VeaknazApplication;

public class ApiClient<T> {
  protected ApiClient(Class<T> repoClass) {

    var baseUrl = VeaknazApplication.getInstance().getBaseContext().getString(R.string.ant_base_url);
    var retrofit = new Retrofit.Builder()
        .addConverterFactory(JacksonConverterFactory.create())
        .baseUrl(baseUrl)
        .build();

    repository = retrofit.create(repoClass);
    executor = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
  }

  public T getRepository() {
    return repository;
  }

  public ListeningExecutorService getExecutor() {
    return executor;
  }

  private final T repository;
  private final ListeningExecutorService executor;
}

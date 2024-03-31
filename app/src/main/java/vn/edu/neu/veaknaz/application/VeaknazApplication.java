package vn.edu.neu.veaknaz.application;

import android.app.Application;

public class VeaknazApplication extends Application {
  public VeaknazApplication() {
    super();
    instance = this;
  }

  public static VeaknazApplication getInstance() {
    return instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  private static VeaknazApplication instance;
}

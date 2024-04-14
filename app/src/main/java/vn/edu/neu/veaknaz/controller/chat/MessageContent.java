package vn.edu.neu.veaknaz.controller.chat;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public record MessageContent(Date date, String text) {

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public String text() {
    return text;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public String getDateString() {
    var dur = Duration.between(Instant.now(), date.toInstant());

    if (dur.abs().compareTo(Duration.of(1, ChronoUnit.MINUTES)) < 0) {
      return dur.abs().getSeconds() + " seconds ago";
    }

    if (dur.abs().compareTo(Duration.of(1, ChronoUnit.HOURS)) < 0) {
      return dur.abs().toMinutes() + " minutes ago";
    }

    if (dur.abs().compareTo(Duration.of(1, ChronoUnit.DAYS)) < 0) {
      return dur.abs().toHours() + " hours ago";
    }

    return dur.abs().toDays() + " days ago";
  }
}

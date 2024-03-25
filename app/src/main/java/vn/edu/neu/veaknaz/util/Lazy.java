package vn.edu.neu.veaknaz.util;

import java.util.Optional;

public class Lazy<T> {
  public Lazy(LazyInitializer<T> initializer) {
    this.initializer = initializer;
    this.value = Optional.empty();
  }

  public T get() {
    return value.orElseGet(() -> {
      synchronized (this) {
        if (!value.isPresent()) {
          value = Optional.of(initializer.initialize());
        }
      }

      return value.get();
    });
  }

  private final LazyInitializer<T> initializer;
  private Optional<T> value;
}

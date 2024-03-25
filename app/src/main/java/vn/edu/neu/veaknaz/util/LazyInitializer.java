package vn.edu.neu.veaknaz.util;

@FunctionalInterface
public interface LazyInitializer<T> {
  T initialize();
}

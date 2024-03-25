package vn.edu.neu.veaknaz.util;

@FunctionalInterface
public interface StateChangeCallback<StateType extends Enum<StateType>> {
  void handle(StateType prev, StateType state);
}


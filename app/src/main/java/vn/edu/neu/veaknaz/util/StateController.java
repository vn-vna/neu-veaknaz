package vn.edu.neu.veaknaz.util;

import java.util.concurrent.atomic.AtomicReference;

public class StateController<StateType extends Enum<StateType>> {

  public StateController(StateType defaultState) {
    currentState = new AtomicReference<>(null);
    setState(defaultState);
  }

  public void setState(StateType state) {
    StateType prev = currentState.get();
    currentState.set(state);
    handler.handle(prev, currentState.get());
  }

  private final AtomicReference<StateType> currentState;
  private StateChangeCallback<StateType> handler;
}


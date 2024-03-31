package vn.edu.neu.veaknaz.util;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class StateController<StateType extends Enum<StateType>> {

  public StateController(StateType defaultState) {
    currentState = new AtomicReference<>(null);
    handlers = Set.of();
    setState(defaultState);
  }

  public void addHandler(StateChangeCallback<StateType> handler) {
    handlers.add(Objects.requireNonNull(handler));
  }

  public void removeHandler(StateChangeCallback<StateType> handler) {
    handlers.remove(Objects.requireNonNull(handler));
  }

  public StateType getState() {
    return currentState.get();
  }

  public void setState(StateType state) {
    StateType prev = currentState.get();
    currentState.set(state);
    for (var handler : handlers) {
      handler.handle(prev, state);
    }
  }

  public boolean isState(StateType state) {
    return currentState.get() == state;
  }

  public void clearHandlers() {
    handlers.clear();
  }

  private final AtomicReference<StateType> currentState;
  private Set<StateChangeCallback<StateType>> handlers;
}


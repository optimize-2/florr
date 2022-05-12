package cn.optimize_2.utils.state;

import java.util.HashMap;
import java.util.Map;

public class StateMachine implements State {

    private final Map<String, State> states;
    public State currentState;

    public StateMachine() {
        states = new HashMap<>();
        currentState = new EmptyState();
        states.put(null, currentState);
    }

    public void add(String name, State state) {
        states.put(name, state);
    }

    public void change(String name) {
        currentState.exit();
        currentState = states.get(name);
        currentState.enter();
    }

    @Override
    public void input() {
        currentState.input();
    }

    @Override
    public void update() {
        currentState.update();
    }

    @Override
    public void update(float delta) {
        currentState.update(delta);
    }

    @Override
    public void render() {
        currentState.render();
    }

    @Override
    public void render(float alpha) {
        currentState.render(alpha);
    }

    @Override
    public void enter() {
        currentState.enter();
    }

    @Override
    public void exit() {
        currentState.exit();
    }
}

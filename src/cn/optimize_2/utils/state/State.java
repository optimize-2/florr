package cn.optimize_2.utils.state;

import cn.optimize_2.client.Florr;

public interface State {

    public void input();

    public default void update() {
        update(1f / Florr.TARGET_UPS);
    }

    public void update(float delta);

    public default void render() {
        render(1f);
    }

    public void render(float alpha);

    public void enter();

    public void exit();
}

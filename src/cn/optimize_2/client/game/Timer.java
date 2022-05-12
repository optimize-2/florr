package cn.optimize_2.client.game;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {
    private double lastLoopTime;
    private float timeCount;
    private int fps;
    private int fpsCount;
    private int ups;
    private int upsCount;

    public void init() {
        lastLoopTime = getTime();
    }

    public double getTime() {
        return glfwGetTime();
    }

    public float getDelta() {
        double time = getTime();
        float delta = (float) (time - lastLoopTime);
        lastLoopTime = time;
        timeCount += delta;
        return delta;
    }

    public void updateFPS() {
        fpsCount++;
    }

    public void updateUPS() {
        upsCount++;
    }

    public void update() {
        if (timeCount > 1f) {
            fps = fpsCount;
            fpsCount = 0;

            ups = upsCount;
            upsCount = 0;

            timeCount -= 1f;
        }
    }

    public int getFPS() {
        return fps > 0 ? fps : fpsCount;
    }

    public int getUPS() {
        return ups > 0 ? ups : upsCount;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }

}

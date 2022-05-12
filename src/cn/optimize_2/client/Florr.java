package cn.optimize_2.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import cn.optimize_2.client.game.Timer;
import cn.optimize_2.client.render.TextureManager;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import cn.optimize_2.utils.input.InputHandler;
import cn.optimize_2.utils.state.*;
import cn.optimize_2.utils.graphic.Renderer;
import cn.optimize_2.utils.graphic.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Florr {
    private static int gameWidth = Client.WIDTH;
    private static int gameHeight = Client.HEIGHT;

    private float delta;
    private float accumulator = 0f;
    private float interval = 1f / TARGET_UPS;
    private float alpha;

    public static final int TARGET_FPS = 10;
    public static final int TARGET_UPS = 30;

    /**
     * The error callback for GLFW.
     */
    private GLFWErrorCallback errorCallback;

    /**
     * Shows if the game is running.
     */
    protected boolean running;

    public Window window;
    public Timer timer;
    protected Renderer renderer;
    public StateMachine state;

    /**
     * Default contructor for the game.
     */
    public Florr() {
        timer = new Timer();
        renderer = new Renderer();
        state = new StateMachine();
    }

    /**
     * This should be called to initialize and start the game.
     */
    public void start() {
        init();
        gameLoop();
        dispose();
    }

    /**
     * Releases resources that where used by the game.
     */
    public void dispose() {
        renderer.dispose();
        state.change(null);
        window.destroy();
        glfwTerminate();
        errorCallback.free();
    }

    /**
     * Initializes the game.
     */
    public void init() {
        errorCallback = GLFWErrorCallback.createPrint();
        glfwSetErrorCallback(errorCallback);
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW!");
        }
        window = new Window(Client.NAME + " | " + Client.VERSION, Client.WIDTH, Client.HEIGHT, Client.vSync);
        InputHandler.init(window.windowHandle);
        TextureManager.init();
        timer.init();
        renderer.init();
        initStates();
        running = true;
    }

    /**
     * Initializes the states.
     */
    public void initStates() {
        state.add("mainMenu", new MainMenuState(renderer));
        state.add("game", new GameState(renderer));
        state.add("disconnected", new DisconnectedState(renderer));
        state.change("mainMenu");
    }

    public void gameLoop() {
        while (running) {
            if (window.windowShouldClose()) {
                Client.getInstance().getNc().sendClientDisconnectMsg();
                running = false;
                Client.info("Closing window");
            }

            delta = timer.getDelta();
            accumulator += delta;

            input();

            while (accumulator >= interval) {
                update();
                timer.updateUPS();
                accumulator -= interval;
            }

            alpha = accumulator / interval;

            render(alpha);
            timer.updateFPS();

            timer.update();

            int height = renderer.getDebugTextHeight("Context");
            renderer.drawText("FPS: " + timer.getFPS() + " | UPS: " + timer.getUPS(), 5, 5 + height);
            renderer.drawText("API Version: " + (Florr.isDefaultContext() ? "3.2 core" : "2.1"), 5, 5);

            if (window.isResized()) {
                gameWidth = window.getWidth();
                gameHeight = window.getHeight();
                window.setResized(false);
            }
            window.update();
            if (!window.isvSync()) {
                sync(TARGET_FPS);
            }
        }
    }

    public void input() {
        state.input();
    }

    public void update() {
        state.update();
    }

    public void update(float delta) {
        state.update(delta);
    }

    public void render() {
        state.render();
    }

    public void render(float alpha) {
        state.render(alpha);
    }

    public void sync(int fps) {
        double lastLoopTime = timer.getLastLoopTime();
        double now = timer.getTime();
        float targetTime = 1f / fps;

        while (now - lastLoopTime < targetTime) {
            Thread.yield();

            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Florr.class.getName()).log(Level.SEVERE, null, ex);
            }

            now = timer.getTime();
        }
    }

    public static boolean isDefaultContext() {
        return GL.getCapabilities().OpenGL32;
    }

    public static int getGameWidth() {
        return gameWidth;
    }

    public static void setGameWidth(int gameWidth) {
        Florr.gameWidth = gameWidth;
    }

    public static int getGameHeight() {
        return gameHeight;
    }

    public static void setGameHeight(int gameHeight) {
        Florr.gameHeight = gameHeight;
    }
}

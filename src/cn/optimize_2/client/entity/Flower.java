package cn.optimize_2.client.entity;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.event.PetalHitEvent;
import cn.optimize_2.client.event.PetalHitListener;
import cn.optimize_2.client.render.TextureManager;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class Flower extends Entity implements PetalHitListener {
    public static final int XSPEED = 3;
    public static final int YSPEED = 3;

    private double r = 50;

    private String name;
    private boolean live = true;
    private Client c;
    private int blood = 100;
    private BloodBar bb = new BloodBar();

    private double rotationSpeed = 0.05;

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] imgs = null;
    private static Map<String, Image> map = new HashMap<>();

    // public static final int WIDTH = imgs[0].getWidth(null);
    // public static final int HEIGHT = imgs[0].getHeight(null);

    public Flower(int x, int y, String name) {
        super(x, y, TextureManager.flowerTexture);
        this.name = name;
        // ((GameState)Client.game.state.currentState).texture.bind();
        // this.texture = TextureManager.flowerTexture;
    }

    public Flower(int x, int y, String name, Client c) {
        this(x, y, name);
        this.c = c;
    }

    @Override
    public void actionToFlowerHitEvent(PetalHitEvent e) {
        this.blood -= e.getSource().getDamage();
    }

    private class BloodBar {
        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
            g.drawRect(getX(), getY() + 30, 30, 8);
            int w = (30 * blood) / 100;
            g.setColor(Color.RED);
            g.fillRect(getX(), getY() + 30, w, 8);
            g.setColor(c);
        }
    }

    public Rectangle getRect() {
        return new Rectangle(super.getX(), super.getY(), imgs[0].getWidth(null), imgs[0].getHeight(null));
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    @Override
    public void input(Entity entity) {
        long window = GLFW.glfwGetCurrentContext();
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            super.setX(super.getX() - XSPEED);
        }
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            super.setY(super.getY() + YSPEED);
        }
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            super.setY(super.getY() - YSPEED);
        }
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            super.setX(super.getX() + XSPEED);
        }
        // F02FlowerMovePacket packet = new F02FlowerMovePacket(getId(), getX(),
        // getY());
        // c.getNc().send(packet);
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Client getC() {
        return c;
    }

    public void setTc(Client c) {
        this.c = c;
    }
}

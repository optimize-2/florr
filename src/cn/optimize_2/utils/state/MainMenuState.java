package cn.optimize_2.utils.state;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.Florr;
import cn.optimize_2.client.entity.Flower;
import cn.optimize_2.client.ui.clickable.entities.ClickableRect;
import cn.optimize_2.client.ui.elements.FlatMainButton;
import cn.optimize_2.client.ui.elements.InputBox;
import cn.optimize_2.utils.graphic.Color;
import cn.optimize_2.utils.graphic.Renderer;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.glClearColor;

public class MainMenuState implements State {

    private final Renderer renderer;

    ArrayList<FlatMainButton> buttons = new ArrayList<>();
    public InputBox name;
    public InputBox ip;

    public MainMenuState(Renderer renderer) {
        this.renderer = renderer;
        double midx = Florr.getGameWidth() / 2, midy = Florr.getGameHeight() / 2;
        buttons.add(new FlatMainButton("Start", new ClickableRect(
                midx - 100, midy - 75,
                180, 25, new Color(32, 32, 32), () -> {
                    Client.info("Clicked");
                    String add, fname;
                    if (ip.getText() == "") {
                        add = "127.0.0.1".trim();
                    } else {
                        add = ip.getText();
                    }
                    if (name.getText() == "") {
                        fname = "Unnamed Flower".trim();
                    } else {
                        fname = name.getText();
                    }

                    try {
                        Client.info("Connecting to " + add + "...");
                        Client.getInstance().setMyFlower(new Flower(50 + (int) (Math.random() * (Florr.getGameWidth() -
                                100)),
                                50 + (int) (Math.random() * (Florr.getGameHeight() - 100)), fname,
                                Client.getInstance()));
                        Client.info("Flower initialized");
                        Client.getInstance().getNc().connect(add);

                        Client.getInstance().getFlowers().add(Client.getInstance().getFlower());

                        Client.game.state.change("game");
                    } catch (Exception e) {
                        Client.info("Server not found");
                    }
                }, () -> {
                }, () -> {
                }, () -> {

                }, () -> {
                }, renderer), renderer));
        name = new InputBox("Unnamed Flower", midx - 100, midy - 20, 180, 25, new Color(32, 32, 32),
                new Color(255, 255, 255), new Color(0, 0, 0), false, renderer);

        ip = new InputBox("127.0.0.1", midx - 100, midy + 10, 180, 25, new Color(32, 32, 32),
                new Color(255, 255, 255), new Color(0, 0, 0), false, renderer);
    }

    @Override
    public void input() {
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(float alpha) {
        renderer.clear();
        double midx = Florr.getGameWidth() / 2, midy = Florr.getGameHeight() / 2;
        for (FlatMainButton button : buttons) {
            button.setX(midx - 100);
            button.setY(midy - 75);
            button.getRect().setColor(new Color(70, 70, 70));
            button.draw();
        }
        name.setX(midx - 100);
        name.setY(midy - 20);
        name.draw();

        ip.setX(midx - 100);
        ip.setY(midy + 10);
        ip.draw();
    }

    @Override
    public void enter() {
        glClearColor(0.8f, 0.8f, 0.8f, 1f);
    }

    @Override
    public void exit() {
    }

    public Color getColor(int type) {
        switch (type) {
            case 0:
                return new Color(235, 235, 235);
            case 1:
                return new Color(246, 246, 246);
            case 2:
                return new Color(0, 0, 0);
            case 3:
                return new Color(200, 200, 200);
            case 4:
                return new Color(215, 215, 215);
        }
        return new Color(0, 0, 0);
    }

}

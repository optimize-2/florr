package cn.optimize_2.utils.state;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.entity.Flower;
import cn.optimize_2.client.Florr;
import cn.optimize_2.utils.graphic.Renderer;
import cn.optimize_2.utils.graphic.Texture;

import static org.lwjgl.opengl.GL11.glClearColor;

/**
 * This class contains a simple game.
 *
 * @author Heiko Brumme
 */
public class GameState implements State {

    public Texture texture;

    private final Renderer renderer;

    public GameState(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void input() {
        Client.getInstance().getFlower().input();
    }

    @Override
    public void update(float delta) {
        for (Flower f : Client.getInstance().getFlowers()) {
            f.update(delta);
        }
    }

    @Override
    public void render(float alpha) {
        renderer.clear();

        renderer.begin();

        for (Flower f : Client.getInstance().getFlowers()) {
            f.render(renderer, alpha);
        }

        renderer.end();

        String scoreText = "X: " + Client.getInstance().getFlower().getX() + " | Y: "
                + Client.getInstance().getFlower().getY();
        int scoreTextWidth = renderer.getTextWidth(scoreText);
        int scoreTextHeight = renderer.getTextHeight(scoreText);
        int scoreTextX = (Florr.getGameWidth() - scoreTextWidth - 5) / 2;
        int scoreTextY = Florr.getGameHeight() - scoreTextHeight - 5;
        renderer.drawText(scoreText, scoreTextX, scoreTextY);
    }

    @Override
    public void enter() {
        glClearColor(0.5f, 0.5f, 0.5f, 1f);
    }

    @Override
    public void exit() {
        // texture.delete();
    }
}

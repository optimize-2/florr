package cn.optimize_2.client.entity;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.Florr;
import cn.optimize_2.utils.graphic.Renderer;
import cn.optimize_2.utils.graphic.Texture;

public abstract class Entity {
    private int x;
    private int y;
    private int id;

    public Texture texture;
    private Boolean isPlayer;
    private Boolean shouldRender = true;

    public Entity(int x, int y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    public void input() {
        input(null);
    }

    public abstract void input(Entity entity);

    public void update(float delta) {

    }

    public void render(Renderer renderer, float alpha) {
        texture.bind();
        if (shouldRender) {
            renderer.drawTexture(texture,
                    x - texture.getWidth() / 2 - Client.getInstance().getFlower().getX() + Florr.getGameWidth() / 2,
                    y - texture.getHeight() / 2 - Client.getInstance().getFlower().getY() + Florr.getGameHeight() / 2);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Boolean getPlayer() {
        return isPlayer;
    }

    public void setPlayer(Boolean player) {
        isPlayer = player;
    }

    public Boolean getShouldRender() {
        return shouldRender;
    }

    public void setShouldRender(Boolean shouldRender) {
        this.shouldRender = shouldRender;
    }
}

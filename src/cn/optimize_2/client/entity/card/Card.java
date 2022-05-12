package cn.optimize_2.client.entity.card;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.entity.Entity;
import cn.optimize_2.client.Florr;
import cn.optimize_2.utils.graphic.Renderer;
import cn.optimize_2.utils.graphic.Texture;

public class Card extends Entity {

//    public Card(int x, int y, Texture texture) {
//        super(x, y, texture);
//    }

    public Card(int id, Texture texture) {
        super(0, 0, texture);
        setId(id);
    }

    private int getOrder() {
        int ord = 0;
        for (Card c : Client.getInstance().getMyCards()) {
            if (c.getId() == getId()) {
                return ord;
            }
            ord += 1;
        }
        return 0;
    }

    @Override
    public void render(Renderer render, float alpha) {
        int width = Florr.getGameWidth();
        int height = Florr.getGameHeight();
        getTexture().bind();
        if (getShouldRender()) {
            render.drawTexture(getTexture(), width / 2 + (getOrder() - 2) * (getTexture().getWidth() + 15) - getTexture().getWidth() / 2, 15);
        }
    }

    @Override
    public void input(Entity entity) {
    }
}

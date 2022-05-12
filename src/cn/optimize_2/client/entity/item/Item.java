package cn.optimize_2.client.entity.item;

import cn.optimize_2.client.entity.Entity;
import cn.optimize_2.utils.graphic.Texture;

public abstract class Item extends Entity {
    public Item(int x, int y, Texture texture) {
        super(x, y, texture);
    }
}

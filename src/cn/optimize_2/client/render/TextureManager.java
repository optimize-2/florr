package cn.optimize_2.client.render;

import cn.optimize_2.utils.graphic.Texture;

public class TextureManager {
    public static Texture flowerTexture;

    public static void init() {
        flowerTexture = Texture.loadTexture("resources/Flower/normal.png");
    }
}

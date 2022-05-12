package cn.optimize_2.utils.text;

public class Glyph {

    public final int width;
    public final int height;
    public final int x;
    public final int y;
    public final float advance;

    public Glyph(int width, int height, int x, int y, float advance) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.advance = advance;
    }
}

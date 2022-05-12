package cn.optimize_2.utils.graphic;

import cn.optimize_2.utils.math.Vector3f;
import cn.optimize_2.utils.math.Vector4f;

public final class Color {

    public static final Color WHITE = new Color(1f, 1f, 1f);
    public static final Color BLACK = new Color(0f, 0f, 0f);
    public static final Color RED = new Color(1f, 0f, 0f);
    public static final Color GREEN = new Color(0f, 1f, 0f);
    public static final Color BLUE = new Color(0f, 0f, 1f);

    private float red;
    private float green;
    private float blue;
    private float alpha;
    public Color() {
        this(0f, 0f, 0f);
    }

    public Color(int i) {
        java.awt.Color c = new java.awt.Color(i);
        this.red = c.getRed();
        this.green = c.getGreen();
        this.blue = c.getBlue();
    }

    public Color(float red, float green, float blue) {
        this(red, green, blue, 1f);
    }

    public Color(float red, float green, float blue, float alpha) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
        setAlpha(alpha);
    }

    public Color(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    public Color(int red, int green, int blue, int alpha) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
        setAlpha(alpha);
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        if (red < 0f) {
            red = 0f;
        }
        if (red > 1f) {
            red = 1f;
        }
        this.red = red;
    }

    public void setRed(int red) {
        setRed(red / 255f);
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        if (green < 0f) {
            green = 0f;
        }
        if (green > 1f) {
            green = 1f;
        }
        this.green = green;
    }

    public void setGreen(int green) {
        setGreen(green / 255f);
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        if (blue < 0f) {
            blue = 0f;
        }
        if (blue > 1f) {
            blue = 1f;
        }
        this.blue = blue;
    }

    public void setBlue(int blue) {
        setBlue(blue / 255f);
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        if (alpha < 0f) {
            alpha = 0f;
        }
        if (alpha > 1f) {
            alpha = 1f;
        }
        this.alpha = alpha;
    }

    public void setAlpha(int alpha) {
        setAlpha(alpha / 255f);
    }

    public Vector3f toVector3f() {
        return new Vector3f(red, green, blue);
    }

    public Vector4f toVector4f() {
        return new Vector4f(red, green, blue, alpha);
    }
}

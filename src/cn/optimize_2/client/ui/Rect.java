package cn.optimize_2.client.ui;

import cn.optimize_2.utils.graphic.Color;
import cn.optimize_2.utils.graphic.Renderer;

public class Rect extends RenderEntity {
    double x;
    double y;
    double width;
    double height;
    Color color;
    Renderer renderer;

    public Rect(double x, double y, double width, double height, Color color, Renderer renderer) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.renderer = renderer;
    }

    public void draw() {
        this.drawRect();
    }

    private void drawRect() {
        renderer.drawRect(x, y, x + width, y + height, color);
    }

    public static enum RenderType {
        Expand, Position;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

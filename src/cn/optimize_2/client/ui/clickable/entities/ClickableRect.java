package cn.optimize_2.client.ui.clickable.entities;

import cn.optimize_2.client.ui.Rect;
import cn.optimize_2.client.ui.clickable.ClickEntity;
import cn.optimize_2.utils.graphic.Color;
import cn.optimize_2.utils.graphic.Renderer;

public class ClickableRect extends ClickEntity {
    private Rect rect;

    public ClickableRect(double x, double y, double x1, double y1, Color color, Runnable click, Runnable hold,
            Runnable focus, Runnable release, Runnable onBlur,
            Renderer renderer) {
        super(x, y, x1, y1, click, hold, focus, release, onBlur);
        this.rect = new Rect(x, y, x1, y1, color, renderer);
    }

    public void draw() {
        rect.draw();
        super.tick();
    }

    public double getX() {
        return rect.getX();
    }

    public void setX(double x) {
        super.setX(x);
        rect.setX(x);
    }

    public double getY() {
        return rect.getY();
    }

    public void setY(double y) {
        super.setY(y);
        rect.setY(y);
    }

    public double getWidth() {
        return rect.getWidth();
    }

    public void setWidth(double width) {
        super.setX1(width);
        rect.setWidth(width);
    }

    public double getHeight() {
        return rect.getHeight();
    }

    public void setHeight(double height) {
        super.setY1(height);
        rect.setHeight(height);
    }

    public Color getColor() {
        return rect.getColor();
    }

    public void setColor(Color color) {
        rect.setColor(color);
    }

}

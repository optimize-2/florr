package cn.optimize_2.client.ui.elements;

import cn.optimize_2.client.ui.Rect;
import cn.optimize_2.client.ui.clickable.entities.ClickableRect;
import cn.optimize_2.utils.graphic.Color;
import cn.optimize_2.utils.graphic.Renderer;
import cn.optimize_2.utils.render.animate.Translate;

public class FlatMainButton {
    String title;
    ClickableRect rect;
    Translate animations;
    Rect animationsRect;
    boolean isHover;
    Runnable lastFocus;
    int hoverTick;

    Renderer renderer;

    public FlatMainButton(String title, ClickableRect rect, Renderer renderer) {
        super();
        this.animations = new Translate(0, 0);
        this.renderer = renderer;
        this.title = title;
        this.rect = rect;
        this.isHover = false;
        this.animationsRect = new Rect(this.getRect().getX(),
                this.getRect().getY(), this.getRect().getX(),
                2, new Color(120, 120, 255), renderer);
        this.lastFocus = this.rect.getFocus();
        this.rect.setFocus(() -> {
            if (hoverTick < 1)
                hoverTick = 1;
            this.lastFocus.run();
            this.isHover = true;
            animations.interpolate((float) this.getRect().getX1() / 2, 0, 0.2f);
            double base = this.getRect().getX() + this.getRect().getX1() / 2;
            this.animationsRect.setX(base - animations.getX());
            this.animationsRect.setWidth(animations.getX() * 2);
        });
    }

    public void draw() {
        hoverTick--;
        if (!this.isFocus()) {
            animations.interpolate(0, 0, 0.3f);
            double base = this.getRect().getX() + this.getRect().getX1() / 2;
            this.animationsRect.setX(base - animations.getX());
            this.animationsRect.setWidth(animations.getX() * 2);
        }
        rect.draw();
        int lenth = renderer.getTextWidth(this.getTitle());
        renderer.drawText(this.getTitle(), (int) (this.getRect().getX() +
                (this.getRect().getWidth() - lenth) / 2), (int) (this.getRect().getY()));

        this.animationsRect.draw();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ClickableRect getRect() {
        return rect;
    }

    public void setRect(ClickableRect rect) {
        this.rect = rect;
    }

    public boolean isFocus() {
        return !(hoverTick < 0);
    }

    public Color getColor(int type) {
        return new Color(127, 127, 127);
    }

    public void setX(double x) {
        this.rect.setX(x);
    }

    public void setY(double y) {
        this.rect.setY(y);
        this.animationsRect.setY(y);
    }
}

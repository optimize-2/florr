package cn.optimize_2.utils.render;

public class MouseBounds {
    double mouseX;
    double mouseY;
    double x;
    double y;
    double x1;
    double y1;

    public MouseBounds(double mouseX, double mouseY, double x, double y, double x1, double y1) {
        super();
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
    }

    public boolean isWhthinBounds() {
        return isWhthinBounds(x + x1, y + y1);
    }

    private boolean isWhthinBounds(double x1, double y1) {
        if (x > x1) {
            double i = x;
            x = x1;
            x1 = i;
        }

        if (y > y1) {
            double j = y;
            y = y1;
            y1 = j;
        }
        return (mouseX >= x && mouseX <= x1) && (mouseY >= y && mouseY <= y1);
    }

    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
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

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }
}

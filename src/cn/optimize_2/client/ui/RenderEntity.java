package cn.optimize_2.client.ui;

public class RenderEntity {
    public double getScaledWidth() {
        return new ScaledResolution().getScaledWidth_double();
    }
    public ScaledResolution getScaledResolution() {
        return new ScaledResolution();
    }
    public double getScaledHeight() {
        return new ScaledResolution().getScaledHeight_double();
    }

    public double getScaledWidth(double scaled) {
        return new ScaledResolution().getScaledWidth_double() / scaled;
    }

    public double getScaledHeight(double scaled) {
        return new ScaledResolution().getScaledHeight_double() / scaled;
    }

    public int getGuiScaleFactor() {
        return new ScaledResolution().getScaleFactor();
    }
}

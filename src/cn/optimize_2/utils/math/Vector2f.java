package cn.optimize_2.utils.math;

import java.nio.FloatBuffer;

public class Vector2f {

    public float x;
    public float y;

    public Vector2f() {
        this.x = 0f;
        this.y = 0f;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public Vector2f normalize() {
        float length = length();
        return divide(length);
    }

    public Vector2f add(Vector2f other) {
        float x = this.x + other.x;
        float y = this.y + other.y;
        return new Vector2f(x, y);
    }

    public Vector2f negate() {
        return scale(-1f);
    }

    public Vector2f subtract(Vector2f other) {
        return this.add(other.negate());
    }

    public Vector2f scale(float scalar) {
        float x = this.x * scalar;
        float y = this.y * scalar;
        return new Vector2f(x, y);
    }

    public Vector2f divide(float scalar) {
        return scale(1f / scalar);
    }

    public float dot(Vector2f other) {
        return this.x * other.x + this.y * other.y;
    }

    public Vector2f lerp(Vector2f other, float alpha) {
        return this.scale(1f - alpha).add(other.scale(alpha));
    }

    public void toBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y);
        buffer.flip();
    }
}

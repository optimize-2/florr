package cn.optimize_2.utils.math;

import java.nio.FloatBuffer;

public class Vector4f {

    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4f() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
        this.w = 0f;
    }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public Vector4f normalize() {
        float length = length();
        return divide(length);
    }

    public Vector4f add(Vector4f other) {
        float x = this.x + other.x;
        float y = this.y + other.y;
        float z = this.z + other.z;
        float w = this.w + other.w;
        return new Vector4f(x, y, z, w);
    }

    public Vector4f negate() {
        return scale(-1f);
    }

    public Vector4f subtract(Vector4f other) {
        return this.add(other.negate());
    }

    public Vector4f scale(float scalar) {
        float x = this.x * scalar;
        float y = this.y * scalar;
        float z = this.z * scalar;
        float w = this.w * scalar;
        return new Vector4f(x, y, z, w);
    }

    public Vector4f divide(float scalar) {
        return scale(1f / scalar);
    }

    public float dot(Vector4f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public Vector4f lerp(Vector4f other, float alpha) {
        return this.scale(1f - alpha).add(other.scale(alpha));
    }

    public void toBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y).put(z).put(w);
        buffer.flip();
    }
}

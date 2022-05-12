package cn.optimize_2.utils.math;

import java.nio.FloatBuffer;

public class Matrix2f {

    private float m00, m01;
    private float m10, m11;

    public Matrix2f() {
        setIdentity();
    }

    public Matrix2f(Vector2f col1, Vector2f col2) {
        m00 = col1.x;
        m10 = col1.y;

        m01 = col2.x;
        m11 = col2.y;
    }

    public final void setIdentity() {
        m00 = 1f;
        m11 = 1f;

        m01 = 0f;
        m10 = 0f;
    }

    public Matrix2f add(Matrix2f other) {
        Matrix2f result = new Matrix2f();

        result.m00 = this.m00 + other.m00;
        result.m10 = this.m10 + other.m10;

        result.m01 = this.m01 + other.m01;
        result.m11 = this.m11 + other.m11;

        return result;
    }

    public Matrix2f negate() {
        return multiply(-1f);
    }

    public Matrix2f subtract(Matrix2f other) {
        return this.add(other.negate());
    }

    public Matrix2f multiply(float scalar) {
        Matrix2f result = new Matrix2f();

        result.m00 = this.m00 * scalar;
        result.m10 = this.m10 * scalar;

        result.m01 = this.m01 * scalar;
        result.m11 = this.m11 * scalar;

        return result;
    }

    public Vector2f multiply(Vector2f vector) {
        float x = this.m00 * vector.x + this.m01 * vector.y;
        float y = this.m10 * vector.x + this.m11 * vector.y;
        return new Vector2f(x, y);
    }

    public Matrix2f multiply(Matrix2f other) {
        Matrix2f result = new Matrix2f();

        result.m00 = this.m00 * other.m00 + this.m01 * other.m10;
        result.m10 = this.m10 * other.m00 + this.m11 * other.m10;

        result.m01 = this.m00 * other.m01 + this.m01 * other.m11;
        result.m11 = this.m10 * other.m01 + this.m11 * other.m11;

        return result;
    }

    public Matrix2f transpose() {
        Matrix2f result = new Matrix2f();

        result.m00 = this.m00;
        result.m10 = this.m01;

        result.m01 = this.m10;
        result.m11 = this.m11;

        return result;
    }

    public void toBuffer(FloatBuffer buffer) {
        buffer.put(m00).put(m10);
        buffer.put(m01).put(m11);
        buffer.flip();
    }
}

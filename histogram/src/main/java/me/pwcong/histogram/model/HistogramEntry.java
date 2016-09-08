package me.pwcong.histogram.model;

/**
 * Created by pwcong on 2016/9/8.
 */
public class HistogramEntry {

    String name;
    float value;

    int color;
    float left;
    float top;
    float right;
    float bottom;

    public HistogramEntry(String name, float value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "HistogramEntry{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", color=" + color +
                ", left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }
}

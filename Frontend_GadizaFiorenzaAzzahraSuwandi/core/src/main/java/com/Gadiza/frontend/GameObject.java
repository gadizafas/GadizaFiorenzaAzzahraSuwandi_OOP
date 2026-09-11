package com.Gadiza.frontend;

public abstract class GameObject {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float speed;
    protected Color color;

    public GameObject(float x, float y, float width, float height, float speed, Color color) {
    }
    public void update(float delta) {
    }

    @Override void update() {

    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
    }
    public class Item {
        private String itemType;
        private long scoreValue;

        public Item(float x, float y, String itemType) {
            super(width = 16, height = 16, speed = 100f, color = color.WHITE, scoreValue = 1000L);
        }
    }

    public void setWidth(float width) {
        if (width > 0) this.width = width;
    }

    public float getWidth() {

    }

    public void setHeight(float height) {
        if (height > 0) this.height = height;
    }

    public float getHeight() {

    }

    public void setSpeed(float speed) {
        if (speed >= 0) this.speed = speed;
    }

    public float getSpeed() {

    }
}

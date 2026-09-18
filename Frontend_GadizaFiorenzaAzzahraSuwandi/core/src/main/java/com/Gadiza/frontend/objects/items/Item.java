package com.Gadiza.frontend.objects.items;

import com.badlogic.gdx.graphics.Color;
import com.Gadiza.frontend.objects.GameObject;

public class Item extends GameObject {
    private String itemType;
    private long scoreValue;

    public Item(float x, float y, String itemType) {
        super(x, y, 16f, 16f, 100f, Color.WHITE);
        this.itemType = itemType;
        this.scoreValue = 1000L;
    }

    public Item(float x, float y, float width, float height, float speed, String itemType) {
        super(x, y, width, height, speed, Color.WHITE);
        this.itemType = itemType;
        this.scoreValue = 1000L;
    }

    public Item(float x, float y, float width, float height, float speed, String itemType, long scoreValue) {
        super(x, y, width, height, speed, Color.WHITE);
        this.itemType = itemType;
        this.scoreValue = scoreValue;
    }

    public String getItemType() {
        return itemType;
    }

    public long getScoreValue() {
        return scoreValue;
    }

    @Override
    public void update(float delta) {
        setY(getY() - getSpeed() * delta);
    }

    private ItemType itemTypeEnum;

    public Item(float x, float y, ItemType itemTypeEnum) {
        super(x, y, 16, 16, 100f); // Default width/height 16, speed 100f
        this.itemTypeEnum = itemTypeEnum;
        this.scoreValue = itemTypeEnum.getScoreValue();
        this.itemType = itemTypeEnum.name(); // Keeps the old string getter working
    }

    public Item(float x, float y, float width, float height, float speed, ItemType itemTypeEnum, long scoreValue) {
        super(x, y, width, height, speed);
        this.itemTypeEnum = itemTypeEnum;
        this.scoreValue = scoreValue;
        this.itemType = itemTypeEnum.name();
    }

    public ItemType getItemTypeEnum() {
        return itemTypeEnum;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Player) {
            // Item pickup is handled on the Player side via collectItem()
            collectItem();
        }
    }

}

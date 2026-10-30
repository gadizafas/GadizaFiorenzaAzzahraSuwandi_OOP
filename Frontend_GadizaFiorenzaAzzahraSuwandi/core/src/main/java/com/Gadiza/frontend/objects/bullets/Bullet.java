package com.Gadiza.frontend.objects.bullets;

import com.badlogic.gdx.graphics.Color;
import com.Gadiza.frontend.objects.Collidable;
import com.Gadiza.frontend.objects.GameObject;
import com.Gadiza.frontend.objects.enemies.Enemy;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends GameObject {
    private BulletType bulletType;
    private int damage;
    private float vx, vy;
    private float angle = 90f;
    private boolean isPlayerBullet;
    private boolean grazed;

    public Bullet(float x, float y, BulletType bulletType, int damage) {
        super(x, y, 16, 16, 400f, Color.YELLOW);
        this.bulletType = bulletType;
        this.damage = damage;
        this.vx = 0f;
        this.vy = 400f;
        this.angle = 90f;
        this.isPlayerBullet = true;
        this.grazed = false;
    }

    public Bullet(float x, float y, float speed, BulletType bulletType, int damage) {
        super(x, y, 8, 16, speed, Color.YELLOW);
        this.bulletType = bulletType;
        this.damage = damage;
        this.vx = 0f;
        this.vy = speed;
        this.angle = (speed < 0) ? 270f : 90f;
        this.isPlayerBullet = true;
        this.grazed = false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // TODO:
        // 1. Update position x by adding velocity component vx multiplied by delta
        setX(getX() + vx * delta);
        // 2. Update position y by adding velocity component vy multiplied by delta
        setY(getY() + vy * delta);
    }


    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy enemy) {
            System.out.println("Bullet hit " + enemy.getName() + " for " + damage + " DMG!");
            boolean defeated = enemy.takeDamage(damage);
            this.destroy(); // Bullet is destroyed on collision
        }
    }

    public BulletType getBulletType() { return bulletType; }
    public int getDamage() { return damage; }

    // Pool Re-initialization for the Object Pool Pattern
    public void init(float x, float y, float vx, float vy, BulletType bulletType, int damage, boolean isPlayerBullet) {
        // TODO:
        // 1. Update position (x, y) and velocity vector components (vx, vy) from the argument parameters
        setX(x);
        setY(y);
        this.vx = vx;
        this.vy = vy;
        // 2. Calculate the visual rotation angle using Math.atan2 as explained above, then convert it to degrees
        this.angle = (float) Math.toDegrees(Math.atan2(vy, vx));
        // 3. Update speed by calculating the resultant length of both velocity vectors using Math.hypot as explained above
        setSpeed((float) Math.hypot(vx, vy));
        // 4. Save the bullet's identity attributes (bulletType, damage, and isPlayerBullet) from the parameters
        this.bulletType = bulletType;
        this.damage = damage;
        this.isPlayerBullet = isPlayerBullet;
        // 5. Reactivate this bullet and reset its animation state time from 0
        setDestroyed(false);
        stateTime = 0f;
        // 6. Make sure the grazed status is reset to inactive so this recycled bullet can be grazed again
        this.grazed = false;

    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && active) {
            if (sprite != null) {
                // TODO: Draw the bullet sprite using batch.draw() with the following parameters:
                // sprite, position (x, y), rotation pivot origin at center (width / 2f, height / 2f),
                // dimensions (width, height), scale (1f, 1f), and rotation angle (angle - 90f)
                batch.draw(sprite, getX(), getY(), getWidth()/2f, getHeight()/2f, getWidth(), getHeight(), 1f, 1f, angle-90f);
            } else {
                super.render(batch);
            }
        }
    }

    // TODO:
    // 1. Create getters for isPlayerBullet, vx, and vy
    public boolean isPlayerBullet() {
        return isPlayerBullet;
    }
    // 2. Create a getter and setter for angle
    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
    // 3. Create a getter hasBeenGrazed() that returns the grazed attribute
    public boolean hasBeenGrazed() {
        return grazed;
    }
    // 4. Create a setter setGrazed(boolean grazed)
    public void setGrazed(boolean grazed) {
        this.grazed = grazed;
    }

}

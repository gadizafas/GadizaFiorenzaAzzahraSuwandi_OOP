package com.Gadiza.frontend.objects.enemies;

import com.Gadiza.frontend.objects.patterns.entityStrategy.FixedMovement;
import com.badlogic.gdx.graphics.Color;
import com.Gadiza.frontend.objects.patterns.ShootingPattern;
import com.Gadiza.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.Gadiza.frontend.systems.BulletManager;

import javax.swing.tree.FixedHeightLayoutCache;

public class Boss extends Enemy {
    private boolean survivesAtZeroHp = false;

    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
        // TODO (2 points): Inject default Boss strategies via setters
        // 1. Set Shooting Pattern as SpreadShot with speed 200f, 3 bullets, and 30f spreadAngleDegrees.
        // 2. Set Movement Pattern as FixedMovement.
        setShootingPattern(new SpreadShot(200f, 3, 30f));
        new FixedMovement();
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
        // TODO (2 points): Perform the same strategy injection in the custom coordinate constructor
        setShootingPattern(new SpreadShot(200f, 3, 30f));
        new FixedMovement();
    }

    public void shootBullet(BulletManager bulletManager) {
        if (shootingPattern != null && !isDestroyed()) {
            shootingPattern.execute(x + width / 2 - 8, y, bulletManager, false);
        }
    }

    public void setLife(int life) {
        this.hp = Math.max(0, life);
        this.maxHp = this.hp;
    }

    @Override
    public boolean takeDamage(int damage) {
        if (!damageable) {
            return false;
        }
        if (!survivesAtZeroHp) {
            return super.takeDamage(damage);
        }
        this.hp = Math.max(0, this.hp - damage);
        System.out.println(name + " took " + damage + " damage! HP: " + this.hp + "/" + this.maxHp);
        return false;
    }

    public ShootingPattern getShootingPattern() { return shootingPattern; }
    public void setShootingPattern(ShootingPattern shootingPattern) { this.shootingPattern = shootingPattern; }

    public boolean isSurvivesAtZeroHp() {
        return survivesAtZeroHp;
    }

    public void setSurvivesAtZeroHp(boolean survivesAtZeroHp) {
        this.survivesAtZeroHp = survivesAtZeroHp;
    }
}

package com.Gadiza.frontend.objects.enemies;

import com.Gadiza.frontend.objects.patterns.ShootingPattern;
import com.Gadiza.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.Gadiza.frontend.systems.BulletManager;
import com.badlogic.gdx.graphics.Color;
import com.Gadiza.frontend.objects.Collidable;
import com.Gadiza.frontend.objects.Player;

public class Boss extends Enemy {
    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Player) {
            System.out.println("Player touches boss");
        }
    }

    // TODO: Declare a private field named `shootingPattern` of type ShootingPattern
        //       and immediately initialize it with the `SpreadShot` we just created.
        //       Make sure speed is 200f, spread angle is 30f, and 3 bullets (num) are fired.
        private ShootingPattern shootingPattern =  new SpreadShot(200f, 30f, 3);

        // Existing Boss constructors you already made before. (Don't touch these!)
        public Boss(String name, int hp) { ... }
        public Boss(float x, float y, String name, int hp) { ... }

        // TODO: Create the shootBullet(BulletManager bulletManager) method:
        //       If shootingPattern is not null and the boss hasn't been destroyed,
        //       execute shootingPattern at the boss's center cone coordinates (x + width / 2 - 8, y).
        public void shootBullet(BulletManager bulletManager) {
            if (shootingPattern != null && != isDestroyed()) {
                shootingPattern.execute();
            }
        }

        // TODO: Provide a getter and setter for shootingPattern
        public ShootingPattern getShootingPattern() {
            return shootingPattern;
        }

        public ShootingPattern setShootingPattern() {
            return shootingPattern;
        }
    }

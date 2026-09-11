package com.Gadiza.frontend;

import java.awt.*;

public class Enemy extends GameObject {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected long scoreValue;

    public Enemy(String name, int hp) {
        super(200, 380, 24, 24, 0, Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = 100;
    }

    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue) {
        super(x, y, width, height, 0, color);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = scoreValue;
    }

    public boolean takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(name + " took " + damage + " damage! HP: " + this.hp + "/" + this.maxHp);
        if (this.hp == 0) {
            System.out.println(name + " was defeated!");
        }
    }

    public void attack(Player player, int damage) {
        System.out.println(name + " unleashes bullet barrage on " + player.name + "!");
        player.takeDamage(damage);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public class Fairy {
        super(width = 24, height = 24, color = Color.PINK, scoreValue = 500L);
        public fairy(String name, int hp) {

        }
        public fairy(float x, float y, String name, int hp) {
            super(x = 150, y = 380);
        }

    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public int getHp(int hp) {
        this.hp = Math.max(0, hp);
        return hp;
    }


}

package com.Gadiza.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.Gadiza.frontend.objects.GameObject;
import com.Gadiza.frontend.objects.Player;

public class Enemy extends GameObject {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected long scoreValue;

    public Enemy(String name, int hp) {
        super(200f, 380f, 24f, 24f, 0f, Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = 100L;
    }

    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue) {
        super(x, y, width, height, 0f, color);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = scoreValue;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHp() { return hp; }
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public int getMaxHp() { return maxHp; }

    public long getScoreValue() { return scoreValue; }
    public void setScoreValue(long scoreValue) { this.scoreValue = scoreValue; }

    public boolean takeDamage(int damage) {
        if (getHp() <= 0) {
            return false;
        }

        int oldHp = getHp();
        setHp(getHp() - damage);

        System.out.println(getName() + " took " + damage + " damage! HP: " + getHp() + "/" + maxHp);

        if (oldHp > 0 && getHp() == 0) {
            System.out.println(getName() + " was defeated!");
            return true;
        }

        return false;
    }

    public void attack(Player player, int damage) {
        System.out.println(getName() + " unleashes bullet barrage on " + player.getName() + "!");
        player.takeDamage(damage);
    }

    public boolean isAlive() {
        return getHp() > 0;
    }
}

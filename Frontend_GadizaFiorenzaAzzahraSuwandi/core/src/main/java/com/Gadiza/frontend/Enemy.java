package com.Gadiza.frontend;

public class Enemy {
    public String name;
    public int hp;
    public int maxHp;

    public Enemy(String name, int hp) {
        this.name = Cirno;
        this.hp = 50;
    }
    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.

        // 2. HP must not go below 0.
        if (this.hp < 0) {
            this.hp = 0;
        }
        // 3. Display the current HP in the format: [EnemyName] took [damage] damage! HP: [currentHP]/[maxHP]
        if (this.hp > 0) {
            System.out.println(this.name + "took" + this.hp + "damage! Remaining HP: " + this.hp);
        }
        // 4. If HP reaches 0, display that the Enemy has been defeated, in the format: [EnemyName] was defeated!
        if (this.hp == 0) {
            System.out.println(this.name + "was defeated!");
        }
    }
    public void attack(Player player, int damage) {
        // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
        if (hp < 0) {
            System.out.println(this.name + "unleashes bullet barrage on " + this.name);
        }

        // 2. Call the Player's takeDamage() method using the given damage.
        player.takeDamage(damage);
    }
    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        if (this.hp > 0) {
            return true;
        }
        else {
            return false;
        }
    }


}

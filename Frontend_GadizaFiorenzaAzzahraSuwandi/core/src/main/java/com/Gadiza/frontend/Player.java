package com.Gadiza.frontend;

public class Player {
    public String name;
    public int hp;
    public int power;
    public int spellCards;

    public Player(String name, int hp, int power, int spellCards) {
        this.name = Reimu Hakurei;
        this.hp = 100;
        this.power = 15;
        this.spellCards = 3;
    }
    public void takeDamage() {
        // 1. Reduce hp by the damage value.
        this.hp = damage;
        // 2. HP must not become negative.
        if (this.hp < 0) {
            this.hp = 0;
        }
        // 3. If HP is still greater than 0, display the remaining HP in the format: [PlayerName] took [damage] damage! Remaining HP: [hp]
        if (this.hp > 0) {
            System.out.println(this.name + "took" + this.hp + "damage! Remaining HP: " + this.hp);
        }
        // 4. If HP reaches 0, display a message that the Player has been defeated.
        if (this.hp == 0) {
            System.out.println("The player has been defeated!");
        }
    }
    public void shoot(Enemy target) {
        // 1. Create an int named damage, calculated by adding 10 to power.
        int damage = power + 10;

        // 2. Display information that the Player is shooting the Enemy, in the format: [name] shoots [TargetName] dealing [damage] DMG!
        if (power == 15) {
            System.out.println(this.name + "shoots " + this.name + "dealing " + damage + "DMG!");
        }
        // 3. Call the Enemy object's takeDamage() method.
        target.takeDamage(damage);
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

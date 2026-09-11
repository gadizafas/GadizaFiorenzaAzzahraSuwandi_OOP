package com.Gadiza.frontend;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 32, 0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 32, 0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public void shoot(Enemy target) {
        int damage = 10 + power;
        System.out.println(name + " shoots " + target.name + " dealing " + damage + " DMG!");
        target.takeDamage(damage);
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(name + " took " + damage + " damage! Remaining HP: " + this.hp);
        if (this.hp == 0) {
            System.out.println(name + " was defeated (Pichuun~)! ");
        }
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void addScore(long points) {
        // TODO: Add the value to the player's score if points is greater than 0.
        this.points = ;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public int getHp(int hp) {
        this.hp = Math.max(0, hp);
        return hp;
    }

    public void setName(){
        this.setName();
    }

    public String getName() {
        this.getName();
    }

    public void setPower() {

    }

    public float getPower() {

    }

    public void setSpellCards() {

    }

    public String getSpellCards() {

    }
}

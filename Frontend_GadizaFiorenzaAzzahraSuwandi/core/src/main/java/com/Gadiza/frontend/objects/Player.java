package com.Gadiza.frontend.objects;

import com.badlogic.gdx.graphics.Color;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;

    public Player(String name, int hp, int power, int spellCards) {
        super(280f, 40f, 32f, 32f, 0f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0L;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32f, 32f, 0f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0L;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHp() { return hp; }
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public int getPower() { return power; }
    public void setPower(int power) { this.power = power; }

    public int getSpellCards() { return spellCards; }
    public void setSpellCards(int spellCards) { this.spellCards = spellCards; }

    public long getScore() { return score; } // Getter only for score

    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            System.out.println(getName() + " gained " + points + " pts! Total Score: " + this.score);
        }
    }

    public void collectItem(Item item) {
        ItemType type = item.getItemTypeEnum();
        if (type != null) {
            switch (type) {
                case POWER -> {
                    // 1. Increase power by type.getPowerBonus() via this.power
                    type.getPowerBonus(this.power);
                    // 2. Add score by item.getScoreValue() via addScore() (addScore() already automatically prints "gained X pts!")
                    item.getScoreValue(addScore());
                    // 3. Print: [name] collected POWER item! Power increased to [power]
                    System.out.println(name+" collected POWER item! Power increased to "+power);
                }
                case POINT -> {
                    // 1. Add score by item.getScoreValue() via addScore()
                    item.getScoreValue(addScore());
                    // 2. Print: [name] collected POINT item!
                    System.out.println(name+" collected POINT item!");
                }
                case BOMB -> {
                    // 1. Increase spellCards by 1
                    spellCards += 1;
                    // 2. Add score by item.getScoreValue() via addScore()
                    item.getScoreValue(addScore());
                    // 3. Print: [name] collected BOMB item! SpellCards: [spellCards]
                    System.out.print(name+" collected BOMB item! SpellCards: "+spellCards);
                }
                case LIFE -> {
                    // 1. Increase hp by 20
                    hp += 20;
                    // 2. Add score by item.getScoreValue() via addScore()
                    item.getScoreValue(addScore());
                    // 3. Print: [name] collected LIFE item! HP: [hp]
                    System.out.println(name+" collected LIFE item! HP: "+hp);
                }
            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }
    }


    public void shoot(Enemy target) {
        int damage = 10 + getPower();
        System.out.println(getName() + " shoots " + target.getName() + " dealing " + damage + " DMG!");
        target.takeDamage(damage);
    }

    public void takeDamage(int damage) {
        setHp(getHp() - damage);
        System.out.println(getName() + " took " + damage + " damage! Remaining HP: " + getHp());
        if (getHp() == 0) {
            System.out.println(getName() + " was defeated (Pichuun~)! ");
        }
    }

    public boolean isAlive() {
        return getHp() > 0;
    }

    @Override
    public void update(float delta) {
        if (Gdx.input != null) {
            // TODO: Check W / UP input   → y += speed * delta
            if (Gdx.input == W) {
                y += speed * delta;
                Gdx.input.isKeyPressed(Input.Keys.W);
            } else if (Gdx.input == S) {
                y -= speed * delta;
                Gdx.input.isKeyPressed(Input.Keys.S);
            }
            // TODO: Check A / LEFT input → x -= speed * delta
            else if (Gdx.input == A) {
                x -= speed * delta;
                Gdx.input.isKeyPressed(Input.Keys.A);
            }
            // TODO: Check D / RIGHT input → x += speed * delta
            else if (Gdx.input == D) {
                x += speed * delta;
                Gdx.input.isKeyPressed(Input.Keys.D);
            }
        }
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is an Item
        if () {
            System.out.println("Player touches item "+collectItem(Item));
        }
        // TODO: Print "Player touches items" then call collectItem((Item) other)
    }

}

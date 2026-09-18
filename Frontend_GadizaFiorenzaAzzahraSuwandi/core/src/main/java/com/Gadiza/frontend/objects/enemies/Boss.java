package com.Gadiza.frontend.objects.enemies;

import com.Gadiza.frontend.objects.Collidable;
import com.badlogic.gdx.graphics.Color;
import com.Gadiza.frontend.objects.GameObject;

public class Boss extends Enemy {
    public Boss(String name, int hp) {
        super(380f, 400f, 48f, 48f, Color.BLUE, name, hp, 5000L);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 48f, 48f, Color.BLUE, name, hp, 5000L);
    }

    public void Collision(Collidable Player) {
        System.out.println("Contact with Player!");
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if () {
            System.out.println("Player touches boss");
        }
        // TODO: Print "Player touches boss"
    }

}

package com.Gadiza.frontend.objects.enemies;

import com.Gadiza.frontend.objects.Collidable;
import com.badlogic.gdx.graphics.Color;
import com.Gadiza.frontend.objects.GameObject;

public class Fairy extends Enemy {
    public Fairy(String name, int hp) {
        super(150f, 380f, 24f, 24f, Color.PINK, name, hp, 500L);
    }

    public Fairy(float x, float y, String name, int hp) {
        super(x, y, 24f, 24f, Color.PINK, name, hp, 500L);
    }

    public void Collision(Collidable Player) {
        System.out.println("Contact with Player!");
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if (Collidable Player) {
            System.out.println("Player touches fairy");
        }
        // TODO: Print "Player touches fairy
    }

}

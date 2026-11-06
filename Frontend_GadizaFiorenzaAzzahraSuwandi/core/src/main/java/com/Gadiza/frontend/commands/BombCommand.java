package com.Gadiza.frontend.commands;

import com.Gadiza.frontend.objects.GameObject;
import com.Gadiza.frontend.objects.Player;
import com.Gadiza.frontend.systems.BulletManager;

import java.util.List;

public class BombCommand implements Command {
    private List<GameObject> entities;

    public BombCommand() {}

    public BombCommand(List<GameObject> entities) {
        // TODO: Initialize the entities attribute from the constructor parameter
        this.entities = entities;
    }

    public void setEntities(List<GameObject> entities) {
        // TODO: Set entities according to the parameter
        this.entities = entities;
    }

    // TODO: Override `execute` with the following business logic:
    // - Check if player exists (is not null)
    // - If so, call the player's useBomb(bulletManager, entities) method
    if (player != null) {
        player.useBomb(bulletManager, entities);
    }
}

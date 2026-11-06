package com.Gadiza.frontend.commands;

import com.Gadiza.frontend.objects.Player;
import com.Gadiza.frontend.systems.BulletManager;

public class MoveCommand implements Command {
    private float dx;
    private float dy;

    public MoveCommand(float dx, float dy) {
        // TODO: Initialize dx and dy attributes from constructor parameters
        this.dx = dx;
        this.dy = dy;
    }

    public void set(float dx, float dy) {
        // TODO: Set dx & dy according to parameters
        this.dx = dx;
        this.dy = dy;
    }

    // TODO: Override `execute` with the following business logic:
    // - Call the player's move(dx, dy) method
    // - Update the player's animation state
    player.move(dx, dy);
    player.;
}

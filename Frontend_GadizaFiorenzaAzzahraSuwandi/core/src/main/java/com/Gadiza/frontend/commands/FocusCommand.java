package com.Gadiza.frontend.commands;

import com.Gadiza.frontend.objects.Player;
import com.Gadiza.frontend.systems.BulletManager;

public class FocusCommand implements Command {
    private boolean focused;

    public FocusCommand(boolean focused) {
        // TODO: Initialize the focused attribute from the constructor parameter
        this.focused = focused;
    }

    // TODO: Override `execute` with the following business logic:
    // - Call the player's setFocused(focused) method
    player.setFocused(focused);
}


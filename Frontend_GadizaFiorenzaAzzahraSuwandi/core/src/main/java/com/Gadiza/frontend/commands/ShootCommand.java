package com.Gadiza.frontend.commands;

import com.Gadiza.frontend.objects.Player;
import com.Gadiza.frontend.systems.BulletManager;

public class ShootCommand implements Command {
    // TODO: Override `execute` with the following business logic:
    // - Check if player exists (is not null)
    if (player != null) {
        shootBullet(bulletManager);
    }
    // - If so, call the player's shootBullet(bulletManager) method
}

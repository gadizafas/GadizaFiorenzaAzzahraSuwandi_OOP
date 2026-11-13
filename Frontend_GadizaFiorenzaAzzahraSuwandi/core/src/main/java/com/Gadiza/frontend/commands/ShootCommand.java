package com.Gadiza.frontend.commands;

import com.Gadiza.frontend.objects.Player;
import com.Gadiza.frontend.systems.BulletManager;

public class ShootCommand implements Command {
    @Override
    public void execute(Player player, BulletManager bulletManager) {
        if (player != null) {
            player.shootBullet(bulletManager);
        }
    }
}

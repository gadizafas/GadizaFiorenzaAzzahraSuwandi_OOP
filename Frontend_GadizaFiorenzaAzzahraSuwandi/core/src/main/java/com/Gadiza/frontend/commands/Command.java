package com.Gadiza.frontend.commands;

import com.Gadiza.frontend.objects.Player;
import com.Gadiza.frontend.systems.BulletManager;

public interface Command {
    void execute(Player player, BulletManager bulletManager);
}



package com.Gadiza.frontend.objects.patterns;

import com.Gadiza.frontend.systems.BulletManager;

public interface ShootingPattern {
    // TODO (6 points): Declare the `execute` method with the contract and parameters described above.
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);
}

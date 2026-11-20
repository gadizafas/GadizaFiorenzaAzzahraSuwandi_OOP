package com.Gadiza.frontend.states;

import com.Gadiza.frontend.objects.enemies.Boss;
import com.Gadiza.frontend.systems.LevelWaveManager;

public class StageClearState implements WaveState {
    // TODO: Implement @Override methods from WaveState:
    // 1. When entering the state, print a stage clear message, then get the boss from manager.
    //    (HINT: Use the Helper in LevelWaveManager!)
    //    1.1. Check! if `boss != null && !boss.isDestroyed()`
    //    1.2. Then destroy that boss (preparation for when the boss is added in CS).
    // 2. Just leave update(manager, delta) empty.
    @Override
    public void onEnter(LevelWaveManager manager) {
        System.out.println("Stage Clear!");
        Boss boss = manager.getBoss();
        if (boss != null && !boss.isDestroyed()) {
            boss.destroy();
        }
    }

    @Override
    public void update(LevelWaveManager manager, float delta) {

    }
}

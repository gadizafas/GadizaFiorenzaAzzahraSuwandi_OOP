package com.Gadiza.frontend.objects.patterns.entityStrategy;

import com.Gadiza.frontend.objects.GameObject;

public interface EntityMovementPattern {
    void move(GameObject entity, float delta);
}

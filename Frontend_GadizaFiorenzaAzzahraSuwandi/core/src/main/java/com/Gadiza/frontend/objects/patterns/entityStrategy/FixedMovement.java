package com.Gadiza.frontend.objects.patterns.entityStrategy;

import com.Gadiza.frontend.objects.GameObject;

public class FixedMovement {
    @Override
    public void move(GameObject entity, float delta) {
        // Fixed position: no positional displacement applied to entity
    }
}

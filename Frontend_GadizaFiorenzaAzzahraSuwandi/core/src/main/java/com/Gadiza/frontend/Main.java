package com.Gadiza.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    private Player player;
    private Fairy fairy;
    private Boss boss;
    private Item pointItem;
    private Item powerItem;
    private List<GameObject> entities;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        entities = new ArrayList<>();

        // Instantiate Player (Red square) at (280, 40)
        player = new Player(280f, 40f, "Reimu Hakurei", 100, 15, 3);

        // Instantiate Fairy (Pink square) at (150, 380)
        fairy = new Fairy(150f, 380f, "Stage 1 Fairy", 20);

        // Instantiate Boss (Blue square) at (380, 400)
        boss = new Boss(380f, 400f, "Cirno (Stage 2 Boss)", 150);

        // Instantiate Items (White squares) with downward speeds
        powerItem = new Item(200, 450, 16, 16, 80f, ItemType.POWER, 500L);
        pointItem = new Item(320, 480, 12, 12, 120f, ItemType.POINT, 1000L);

        // Add all entities into the gameObjects list polymorphically
        gameObjects.add(player);
        gameObjects.add(fairy);
        gameObjects.add(boss);
        gameObjects.add(pointItem);
        gameObjects.add(powerItem);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        for (GameObject obj : entities) {
            obj.update(delta);
        }

        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject obj : entities) {
            obj.render(shapeRenderer);
        }
        shapeRenderer.end();

        // AABB Collision detection between every unique entity pair
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                GameObject a = entities.get(i);
                GameObject b = entities.get(j);

                // TODO: Check whether getCoreHitbox() of a and b overlap (use the .overlaps() method of Rectangle)
                if (getCoreHitbox.overlaps(a, b)) {
                    onCollision(b);
                    onCollision(a);
                }
            }
        }
    }

    @Override
    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}

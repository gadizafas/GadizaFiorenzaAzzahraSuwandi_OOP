package com.Gadiza.frontend;

import com.Gadiza.frontend.systems.EntityFactory;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.Gadiza.frontend.objects.GameObject;
import com.Gadiza.frontend.objects.Player;
import com.Gadiza.frontend.objects.enemies.Boss;
import com.Gadiza.frontend.objects.enemies.Fairy;
import com.Gadiza.frontend.objects.items.Item;
import com.Gadiza.frontend.objects.items.ItemType;
import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private Player player;
    private List<Fairy> fairy;
    private Boss boss;
    private Item powerItem;
    private Item pointItem;
    private List<GameObject> entities;
    private SpriteBatch batch;

    @Override
    public void create() {
        // TODO 1:
        // When initializing the renderer, create a SpriteBatch and store it in batch.
        // LibGDX hint: new SpriteBatch().
        batch = new SpriteBatch();

        // TODO 2:
        // Initialize the fairy and entities lists as empty ArrayLists.
        fairy = new ArrayList<>();
        entities = new ArrayList<>();

        // TODO 3:
        // Before creating entities, get the AssetManager instance and call init();
        AssetManager.getInstance().init();

        // TODO 4:
        // Update how all entities are created! Follow the table and create Player, Fairy, Boss, and Item
        // using the appropriate EntityFactory methods.
        // Add both Fairies to the fairy list using add(...).
        Player player = EntityFactory.createPlayer(280, 40, "Reimu Hakurei", 100, 15, 3);
        Fairy fairy = EntityFactory.createFairy(150, 380, "Stage 1 Fairy", 20);
        Boss boss = EntityFactory.createBoss();
        Item item = EntityFactory.createItem();


        // TODO 5:
        // Add all the objects you have just created to entities.
        entities.add(player);
        entities.add(fairy);
        entities.add(boss);
        entities.add(item);
    }


    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // 1. Check Player Bullet shooting input (Key Z)
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            entities.add(player.shootBullet());
        }

        // 2. Generic update & safe removal of off-screen/destroyed entities using non-static instance method
        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            // 3. Collision detection between active entities
            for (int i = 0; i < entities.size(); i++) {
                for (int j = i + 1; j < entities.size(); j++) {
                    GameObject a = entities.get(i);
                    GameObject b = entities.get(j);

                    if (!a.isDestroyed() && !b.isDestroyed()) {
                        if (a.getCoreHitbox().overlaps(b.getCoreHitbox())) {
                            a.onCollision(b);
                            b.onCollision(a);
                        }
                    }
                }
            }
            ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

            batch.begin();
            for (GameObject entity : entities) {
                if (!entity.isDestroyed()) {
                    // TODO: Call each entity's .render() method with the SpriteBatch as its argument.
                    batch.render(entities);
;                }
            }
            batch.end();
        }


        // 4. Clear screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        // 5. Render filled hitboxes with ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(shapeRenderer);
            }
        }
        shapeRenderer.end();
    }

    // Non-static (Instance) Generic Method with Bounded Type Parameter <T extends GameObject>
    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            entity.update(delta);

            if (entity.isOffScreen(screenWidth, screenHeight) || entity.isDestroyed()) {
                System.out.println("Removed via Generic Iterator: " + entity.getClass().getSimpleName());
                iterator.remove(); // Safe removal using Iterator!
            }
        }
    }

    @Override
    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }

        AssetManager.dispose();
    }
}

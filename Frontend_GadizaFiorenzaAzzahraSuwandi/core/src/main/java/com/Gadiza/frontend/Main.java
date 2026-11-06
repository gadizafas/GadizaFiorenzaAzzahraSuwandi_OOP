package com.Gadiza.frontend;

import com.Gadiza.frontend.commands.Command;
import com.Gadiza.frontend.commands.InputHandler;
import com.Gadiza.frontend.observers.GameObserver;
import com.Gadiza.frontend.systems.*;
import com.Gadiza.frontend.ui.GameHUD;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.Gadiza.frontend.objects.GameObject;
import com.Gadiza.frontend.objects.Player;
import com.Gadiza.frontend.objects.enemies.Boss;
import com.Gadiza.frontend.objects.enemies.Fairy;
import com.Gadiza.frontend.objects.items.Item;
import com.Gadiza.frontend.objects.items.ItemType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.T;

public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private Player player;
    private List<Fairy> fairy;
    private Boss boss;
    private Item powerItem;
    private Item pointItem;
    private List<GameObject> entities;
    private SpriteBatch batch;

    // TODO: Create a private field to store BulletManager
    private BulletManager bulletManager;
    // TODO: Create a private field to store CollisionReferee
    private CollisionReferee collisionReferee;
    private float enemyShootTimer = 0f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fairy = new ArrayList<>();
        entities = new ArrayList<>();
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();
        // TODO: Instantiate new objects for inputHandler and gameHUD
        inputHandler = new InputHandler();
        gameHUD = new GameHUD;


        @Override
        public void create () {
            batch = new SpriteBatch();
            spriteBatch = new SpriteBatch();

            fairy = new ArrayList<>();
            entities = new ArrayList<>();

            bulletManager = new BulletManager();
            collisionReferee = new CollisionReferee();

            AssetManager.getInstance().init();

            AudioSystem.getInstance().playMusic("bgm", true);

            AudioSystem.getInstance().loadMusic("bgm", "u-n-owen-was-her-touhou.mp3");
            AudioSystem.getInstance().loadSound("fires_bullet", "touhou-death-sound-effect.mp3");
            AudioSystem.getInstance().loadSound("enemy_dies", "touhou-boss-death-sfx.mp3");
            AudioSystem.getInstance().loadSound("collect_item", "touhou-old-powerup.mp3");

            player = EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 15, 3);

            Fairy redFairy = EntityFactory.createFairy(150, 380, "Red Fairy", 20);
            Fairy blueFairy = EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue");

            fairy.add(redFairy);
            fairy.add(blueFairy);

            boss = EntityFactory.createBoss(250, 400, "Rumia", 150);

            powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
            pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

            // TODO: Register gameHUD as an Observer on the player object!
            // Hint: player.registerObserver(gameHUD);
            player.registerObserver(gameHUD);

            entities.add(player);
            entities.addAll(fairy);
            entities.add(boss);
            entities.add(powerItem);
            entities.add(pointItem);

            // TODO: Register the entities list to inputHandler for bullet conversion during a Bomb!
            // Hint: inputHandler.setEntities(entities);
            inputHandler.setEntities(entities);
        }

        @Override
        public void render() {
            float delta = Gdx.graphics.getDeltaTime();

            // 0. Restart Game on 'R' key when Game Over
            // TODO: Check if Gdx.input != null AND the R key was just pressed (isKeyJustPressed(Input.Keys.R))
            //       AND player.isGameOver() is true: call restartGame();
            if (Gdx.input != null && isKeyJustPressed(Input.Keys.R) && player.isGameOver() == true) {
                restartGame();
            }

            // 1. Process all player inputs through the Command Pattern (Invoker Command Stream)
            // TODO:
            // - Retrieve the command stream: List<Command> commands = inputHandler.handleInput(delta);
            // - Loop through each command in commands and call: command.execute(player, bulletManager);
            // (Remove any old manual Gdx.input checks in render() if still present!)
            List<Command> commands = inputHandler.handleInput(delta);
                for (InputHandler : bulletManager) {
                    command.execute(player, bulletManager);
                }

            // 2. Periodic Enemy Action Scheduler
            updateEnemyScheduler(delta);

            // 3. Generic update & safe removal of standard entities
            updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            // 4. Object Pool Update (BulletManager)
            bulletManager.update(delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            // 5. Mediator Pattern Collision Resolution (CollisionReferee - Core vs Graze detection)
            collisionReferee.resolveCollisions(player, entities, bulletManager);

            // 6. Clear screen
            ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

            // 7. Render active entity sprites and pooled bullets with SpriteBatch
            batch.begin();
            for (GameObject entity : entities) {
                if (!entity.isDestroyed()) {
                    entity.render(batch);
                }
            }
            bulletManager.render(batch);
            // TODO: Call gameHUD's render method with batch as parameter
            gameHUD.renderCalls(batch);

            batch.end();
            // 8. Render UI border frame lines & Focus Mode Core Hitbox indicator (Drawn ON TOP of sprites!)
            // TODO: Call gameHUD.renderBackground()
            // TODO: Call player.renderFocusIndicator(gameHUD.getShapeRenderer())
            gameHUD.renderBackground();
            player.renderFocusIndicator(gameHUD.getShapeRenderer())
        }

        // Non-static (Instance) Generic Method with Bounded Type Parameter <T extends GameObject>
        public <T extends GameObject > void updateAndClean (List < T > list,float delta, float screenWidth,
        float screenHeight){
            Iterator<T> iterator = list.iterator();
            while (iterator.hasNext()) {
                T entity = iterator.next();
                entity.update(delta);

                if (entity.isOffScreen(screenWidth, screenHeight) || entity.isDestroyed()) {
                    System.out.println("Removed via Generic Iterator: " + entity.getClass().getSimpleName());
                    iterator.remove();
                }
            }
        }

        @Override
        public void dispose () {
            if (batch != null) {
                spriteBatch.dispose();
            }
            if (batch != null) {
                batch.dispose();
            }

            // TODO: Call dispose on AssetManager to release the loaded Textures as well.
            AssetManager.getInstance().dispose();
        }


    }

    private void updateEnemyScheduler(float delta) {
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f) {
            boss.shootBullet(bulletManager);
            enemyShootTimer = 0f;
        }
    }

    private void restartGame() {
        System.out.println("[Main] Restarting game...");
        entities.clear();
        bulletManager.clearEnemyBullets();
        bulletManager.clearPlayerBullets();
        entities.add(player);
        player.resetGame();

        fairy.clear();
        fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);
    }

}

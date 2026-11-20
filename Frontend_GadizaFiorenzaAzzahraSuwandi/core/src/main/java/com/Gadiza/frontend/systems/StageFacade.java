package com.Gadiza.frontend.systems;

import com.Gadiza.frontend.commands.Command;
import com.Gadiza.frontend.states.StageClearState;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.Gadiza.frontend.commands.InputHandler;
import com.Gadiza.frontend.objects.GameObject;
import com.Gadiza.frontend.objects.Player;
import com.Gadiza.frontend.observers.GameObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StageFacade {
    private static final float PLAYFIELD_WIDTH = 416f;
    private static final float PLAYFIELD_HEIGHT = 464f;

    private final List<GameObject> entities = new ArrayList<>();
    private final BulletManager bulletManager = new BulletManager();
    private final CollisionReferee collisionReferee = new CollisionReferee();
    private final InputHandler inputHandler = new InputHandler();
    private final Player player;
    private LevelWaveManager waveManager;

    // ====================================================
    // Constructor
    // ====================================================
    public StageFacade(GameObserver hud) {
        // TODO: Initialize all stage subsystems:
        // 1. Create player with EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 0, 3)
        // 2. Register hud as the player's observer, then add player to the entities list
        // 3. Pass the entities list to bulletManager & inputHandler via setEntities(...)
        // 4. Initialize waveManager = new LevelWaveManager(entities, bulletManager, player)
        this.player = EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 0, 3);
        bulletManager.setEntities();
        inputHandler.setEntities();
        waveManager = new LevelWaveManager(entities, bulletManager, player);
    }

    // ====================================================
    // Core Methods
    // ====================================================

    // TODO: Implement the core Facade methods according to the UML:
    // 1. update(delta):
    //    - While !isStageClear() && !isGameOver(), execute all Commands from inputHandler.handleInput(delta)
    //    - Update waveManager, updateAndClean(entities, delta, PLAYFIELD_WIDTH, PLAYFIELD_HEIGHT),
    //      bulletManager.update(delta, PLAYFIELD_WIDTH, PLAYFIELD_HEIGHT), and
    //      collisionReferee.resolveCollisions(player, entities, bulletManager) in order
    // 2. isStageClear(): returns true if waveManager.getCurrentState() is an instance of StageClearState
    // 3. restart(): clear entities as well as clearEnemyBullets() & clearPlayerBullets(),
    //    re-add player to entities, call player.resetGame(), then recreate waveManager
    public float update(float delta) {
        while (!isStageClear() && !isGameOver()) {
            List<Command> commands = inputHandler.handleInput(delta);
            if (commands != null) {
                for (Command command : commands) {
                    if (command != null) {
                        command.execute();
                    }
                }
            }

            waveManager.update(delta);
            updateAndClean(entities, delta, PLAYFIELD_WIDTH, PLAYFIELD_HEIGHT);
            bulletManager.update(delta, PLAYFIELD_WIDTH, PLAYFIELD_HEIGHT);
            collisionReferee.resolveCollisions(player, entities, bulletManager)
        }
    }

    public boolean isStageClear() {
        return waveManager.getCurrentState() instanceof StageClearState;
    }

    public void restart() {
        entities.clear();
        bulletManager.clearEnemyBullets();
        bulletManager.clearPlayerBullets();
        entities.add(player);
        player.resetGame();
        waveManager = new LevelWaveManager(entities, bulletManager, player);
    }

    // ====================================================
    // Render, Query & Helper
    // ====================================================
    public void render(SpriteBatch batch) {
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }
        bulletManager.render(batch);
    }

    public void renderOverlay(ShapeRenderer shapeRenderer) {
        player.renderFocusIndicator(shapeRenderer);
    }

    public boolean isGameOver() {
        return player.isGameOver();
    }

    public long getScore() {
        return player.getScore();
    }

    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            entity.update(delta);

            if (entity.isOffScreen(screenWidth, screenHeight) || entity.isDestroyed()) {
                iterator.remove();
            }
        }
    }
}

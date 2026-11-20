package com.Gadiza.frontend;

import com.Gadiza.frontend.objects.patterns.entityStrategy.SCurveMovement;
import com.Gadiza.frontend.objects.patterns.entityStrategy.TargetPointMovement;
import com.Gadiza.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.Gadiza.frontend.systems.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.Gadiza.frontend.commands.Command;
import com.Gadiza.frontend.commands.InputHandler;
import com.Gadiza.frontend.objects.GameObject;
import com.Gadiza.frontend.objects.Player;
import com.Gadiza.frontend.objects.enemies.Boss;
import com.Gadiza.frontend.objects.enemies.Fairy;
import com.Gadiza.frontend.objects.items.Item;
import com.Gadiza.frontend.objects.items.ItemType;
import com.Gadiza.frontend.ui.GameHUD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private InputHandler inputHandler;
    private GameHUD gameHUD;

    private StageFacade stage;

    private float phaseTimer = 0f;
    private int currentPhase = 0; // 0: Upper Left (Aimed SpreadShot), 1: Upper Right (AlternatingRingShot)

    @Override
    public void create () {
        batch = new SpriteBatch();
        fairy = new ArrayList<>();
        entities = new ArrayList<>();
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();
        // TODO: Instantiate new objects for inputHandler and gameHUD
        inputHandler = new InputHandler();
        gameHUD = new GameHUD;
        stage = new StageFacade(gameHUD);

        AssetManager.getInstance().init();

        AudioSystem.getInstance().playMusic("bgm", true);

        AudioSystem.getInstance().loadMusic("bgm", "u-n-owen-was-her-touhou.mp3");
        AudioSystem.getInstance().loadSound("fires_bullet", "touhou-death-sound-effect.mp3");
        AudioSystem.getInstance().loadSound("enemy_dies", "touhou-boss-death-sfx.mp3");
        AudioSystem.getInstance().loadSound("collect_item", "touhou-old-powerup.mp3");

        player = EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 15, 3);

        Fairy redFairy = EntityFactory.createFairy(150, 380, "Red Fairy", 20);
        Fairy blueFairy = EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue");

        boss = EntityFactory.createBoss(200, 420, "Rumia", 150);

        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        // TODO: Register gameHUD as an Observer on the player object!
        // Hint: player.registerObserver(gameHUD);
        player.registerObserver(gameHUD);

        entities.add(player);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);

        // TODO: Register the entities list to inputHandler for bullet conversion during a Bomb!
        // Hint: inputHandler.setEntities(entities);
        inputHandler.setEntities(entities);

        bulletManager.setEntities(entities);
        // TODO (3 points): Inject initial strategies into Boss Rumia
        boss.setMovementPattern(new TargetPointMovement(200f, 420f, 150f));
        boss.setShootingPattern(new SpreadShot(220f, 5, 25f, 10, player, "bullet_danmaku_red"));
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input != null && Gdx.input.isKeyJustPressed(Input.Keys.R)
            && (stage.isGameOver() || stage.isStageClear())) {
            stage.restart();
            gameHUD.clearScoreboard();
        }

        stage.update(delta);
        gameHUD.setStageClear(stage.isStageClear());

        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);
        batch.begin();
        stage.render(batch);
        gameHUD.render(batch);
        batch.end();

        gameHUD.renderBackground();
        stage.renderOverlay(gameHUD.getShapeRenderer());

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
        player.renderFocusIndicator(gameHUD.getShapeRenderer());
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (gameHUD != null) gameHUD.dispose();
        AssetManager.getInstance().dispose();
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

    private void updateEnemyScheduler(float delta) {
        // 1. Update fairy wave
        updateFairyWave(delta);
        fairy.removeIf(f -> f.isDestroyed() || f.isOffScreen(416, 560));

        // 2. Periodic Boss Rumia Shooting via Method Injection (Every 1.5 seconds)
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f && boss != null && !boss.isDestroyed()) {
            // TODO (4 points): Command boss to shoot!
            enemyShootTimer = 0f;
        }

        // 3. Dynamic Strategy Swapping for Rumia Every 4.0 Seconds
        phaseTimer += delta;
        if (phaseTimer >= 4.0f && boss != null && !boss.isDestroyed()) {
            phaseTimer = 0f;
            currentPhase = (currentPhase + 1) % 2;

            if (currentPhase == 0) {
                // Phase 1: Move to Upper Left (120, 420) & Aimed SpreadShot pattern (Red Fan)
                // TODO (1 point): Inject Movement Pattern into boss as TargetPointMovement to coordinates (120f, 420f) with speed 150f
                // TODO (1 point): Inject Shooting Pattern into boss as SpreadShot with speed 220f, 5 bullets, angle 25f, DMG 10, target player, and sprite "bullet_danmaku_red"
                boss.setMovementPattern(new TargetPointMovement(120f, 420f, 150f));
                boss.setShootingPattern(new SpreadShot(220f, 5, 25f, 10, player, "bullet_danmaku_red"));
                System.out.println("[Boss Strategy] Rumia shifted to Upper Left with Aimed SpreadShot!");
            } else {
                // Phase 2: Move to Upper Right (280, 420) & AlternatingRingShot pattern (Ring Nova)
                // TODO (1 point): Inject Movement Pattern into boss as TargetPointMovement to coordinates (280f, 420f) with speed 150f
                boss.setMovementPattern(new TargetPointMovement(280f, 420f, 150f));
                // TODO (1 point): Inject Shooting Pattern into boss as AlternatingRingShot with baseSpeed 180f, 16 bullets per ring, and DMG 10
                boss.setShootingPattern(new SpreadShot(180f, 16, 25f, 10, null, null));
                System.out.println("[Boss Strategy] Rumia shifted to Upper Right with AlternatingRingShot!");
            }
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
        boss = EntityFactory.createBoss(200, 420, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);

        bulletManager.setEntities(entities);
        // TODO (3 points): Inject initial strategies into Boss Rumia
        boss.setMovementPattern(new TargetPointMovement(200f, 420f, 150f));
        boss.setShootingPattern(new SpreadShot(220f, 5, 25f, 10, player, "bullet_danmaku_red"));

        resetFairyWave();

        currentPhase = 0;
        phaseTimer = 0f;
        enemyShootTimer = 0f;

    }

    // Stage 1: Wave 1 Fairy Stream
    private float waveTimer = 0f;
    private int wave1SpawnIndex = 0;

    private void updateFairyWave(float delta) {
        float[] spawnX = {60f, 68f, 76f, 84f, 92f};
        waveTimer += delta;
        if (wave1SpawnIndex < spawnX.length && waveTimer >= 1.0f + wave1SpawnIndex * 0.267f) {
            float x = spawnX[wave1SpawnIndex++];
            Fairy f = EntityFactory.createFairy(x, 480f, "Left S-Curve Fairy", 8, "fairy_idle_blue");

            // TODO (2 points): Set Movement Pattern on f as SCurveMovement with speed 130f, initialAngle (float) Math.toRadians(270), and mirrored false
            f.setMovementPattern(new SCurveMovement(130f, (float) Math.toRadians(270), false));
            // TODO (1 point): Set Shooting Pattern on f as null
            f.setShootingPattern(null);

            fairy.add(f);
            entities.add(f);
        }
    }

    private void resetFairyWave() {
        waveTimer = 0f;
        wave1SpawnIndex = 0;
    }


}

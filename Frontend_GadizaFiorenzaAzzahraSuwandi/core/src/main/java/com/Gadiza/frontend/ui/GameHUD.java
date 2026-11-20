package com.Gadiza.frontend.ui;

import com.Gadiza.frontend.network.ScoreEntry;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.Gadiza.frontend.observers.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class GameHUD implements GameObserver {
    private long score;
    private int hp;
    private int spellCards;
    private int power;
    private int grazeCount;
    private int lives = 3;

    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    // ...
// [ADD] New fields inside the GameHUD class:
    private static final float PLAYFIELD_CENTER_X = 32f + 384f / 2f;

    private boolean stageClear = false;
    private String scoreboardStatus = null;
    private final List<String> leaderboardLines = new ArrayList<>();
    private GlyphLayout glyphLayout;
// ...


    public GameHUD() {
        // Lazily initialized during rendering so it can run safely in headless CLI tests
    }

    // TODO: Implement the methods from the GameObserver interface:
    // 1. onScoreChanged(long newScore) -> update this.score
    // 2. onHpChanged(int currentHp) -> update this.hp
    // 3. onSpellCardsChanged(int currentSpellCards) -> update this.spellCards
    // 4. onPowerChanged(int currentPower) -> update this.power
    // 5. onGrazeChanged(int currentGraze) -> update this.grazeCount
    // 6. onLivesChanged(int currentLives) -> update this.lives (Touhou Life System)
    @Override
    public void onScoreChanged(long newScore) {
        this.score = newScore;
    }

    @Override
    public void onHpChanged(int currentHp) {
        this.hp = currentHp;
    }

    @Override
    public void onSpellCardsChanged(int currentSpellCards) {
        this.spellCards = currentSpellCards;
    }

    @Override
    public void onPowerChanged(int currentPower) {
        this.power = currentPower;
    }

    @Override
    public void onGrazeChanged(int currentGraze) {
        this.grazeCount = currentGraze;
    }

    @Override
    public void onLivesChanged(int currentLives) {
        this.lives = currentLives;
    }

    public void renderBackground() {
        if (Gdx.graphics != null && Gdx.gl != null) {
            if (shapeRenderer == null) {
                shapeRenderer = new ShapeRenderer();
            }
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.LIGHT_GRAY);
            // Playfield boundary box: X: 32..416, Y: 16..464 (Width 384, Height 448)
            shapeRenderer.rect(32, 16, 384, 448);

            // Sidebar boundary box: X: 432..SidebarRight, Y: 16..464 (Height 448)
            float sidebarWidth = (Gdx.graphics != null && Gdx.graphics.getWidth() == 640) ? 192 : 336;
            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.rect(432, 16, sidebarWidth, 448);

            // [UPDATE] Inside renderBackground(), adjust box dimensions for the 640x480 window:
        }
    }

    public void render(SpriteBatch batch) {
        if (batch != null) {
            if (font == null && Gdx.files != null) {
                font = new BitmapFont();
                font.setColor(Color.WHITE);
            }

            if (font != null) {
                if (glyphLayout == null) {
                    glyphLayout = new GlyphLayout();
                }

                font.draw(batch, "=== TOUHOU PRACTICUM ===", 448, 540);
                font.draw(batch, "HighScore: " + String.format("%09d", Math.max(score, 999990L)), 448, 500);
                font.draw(batch, "Score:     " + String.format("%09d", score), 448, 470);
                font.draw(batch, "Player:     " + (lives > 0 ? "★ ".repeat(lives) : "GAME OVER (Press R)"), 448, 430);
                font.draw(batch, "SpellCards: " + "★ ".repeat(Math.max(0, spellCards)), 448, 390);
                font.draw(batch, "Power:     " + power + " / 128", 448, 350);
                font.draw(batch, "Graze:     " + grazeCount, 448, 310);

                font.draw(batch, "--- CONTROLS ---", 448, 230);
                font.draw(batch, "Arrow / WASD : Move", 448, 200);
                font.draw(batch, "Shift        : Focus Mode", 448, 170);
                font.draw(batch, "Z            : Shoot", 448, 140);
                font.draw(batch, "X            : SpellCard (Bomb)", 448, 110);
                font.draw(batch, "R            : Restart (on Game Over)", 448, 80);

                if (stageClear || lives <= 0) {
                    renderRunEndLeaderboard(batch);
                }
            }
        }
    }


    // ...

    // [ADD] Helper & scoreboard control methods inside GameHUD:
    private void renderRunEndLeaderboard(SpriteBatch batch) {
        font.setColor(stageClear ? Color.GOLD : Color.SALMON);
        drawCenteredOnPlayfield(batch, stageClear ? "=== STAGE 1 CLEAR! ===" : "=== GAME OVER ===", 360f);
        font.setColor(Color.WHITE);

        drawCenteredOnPlayfield(batch, "--- LEADERBOARD ---", 320f);
        if (scoreboardStatus != null) {
            drawCenteredOnPlayfield(batch, scoreboardStatus, 295f);
        }
        for (int i = 0; i < leaderboardLines.size(); i++) {
            drawCenteredOnPlayfield(batch, leaderboardLines.get(i), 265f - i * 24f);
        }
        drawCenteredOnPlayfield(batch, "Press R to Restart", 120f);
    }

    private void drawCenteredOnPlayfield(SpriteBatch batch, String text, float y) {
        if (text == null) return;
        glyphLayout.setText(font, text);
        font.draw(batch, glyphLayout, PLAYFIELD_CENTER_X - glyphLayout.width / 2f, y);
    }

    public void showLeaderboard(List<ScoreEntry> entries) {
        leaderboardLines.clear();
        for (int i = 0; i < entries.size(); i++) {
            ScoreEntry entry = entries.get(i);
            leaderboardLines.add((i + 1) + ". " + entry.getPlayerName() + "  " + String.format("%09d", entry.getPoint()));
        }
    }

    public void clearScoreboard() {
        stageClear = false;
        scoreboardStatus = null;
        leaderboardLines.clear();
    }


    public void dispose() {
        if (font != null) font.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    // TODO: Create getters for score, hp, lives, spellCards, power, and grazeCount
    public long getScore() {
        return score;
    }

    public int getHp() {
        return hp;
    }

    public int getLives() {
        return lives;
    }

    public int getSpellCards() {
        return spellCards;
    }

    public int getPower() {
        return power;
    }

    public int getGrazeCount() {
        return grazeCount;
    }

    public String getScoreboardStatus() {
        return scoreboardStatus;
    }

    public void setScoreboardStatus(String scoreboardStatus) {
        this.scoreboardStatus = scoreboardStatus;
    }

    public boolean isStageClear() {
        return stageClear;
    }

    public void setStageClear(boolean stageClear) {
        this.stageClear = stageClear;
    }
}

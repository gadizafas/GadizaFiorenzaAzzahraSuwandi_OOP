package com.Gadiza.frontend.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    // 1. Single instance for the Singleton pattern
    private static AssetManager instance;

    // 2. Caches for the Flyweight pattern
    private Map<String, TextureRegion> textureRegionMap;
    private Map<String, Animation<TextureRegion>> animationMap;
    private Map<String, Texture> textureMap;

    // The constructor is private so that outside classes cannot instantiate AssetManager directly (enforces Singleton)
    private AssetManager() {
        // Initialize the three Maps as empty HashMaps
        this.textureRegionMap = new HashMap<>();
        this.animationMap = new HashMap<>();
        this.textureMap = new HashMap<>();
    }

    // Global access point for the single instance (Lazy Initialization)
    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    public Texture loadTexture(String filename) {
        // 1. Check whether filename is already in textureMap
        if (!textureMap.containsKey(filename)) {
            // 2. Check if Gdx.files is ready and the file exists
            if (Gdx.files != null && Gdx.files.internal(filename).exists()) {
                // Create new Texture and store it in textureMap
                Texture texture = new Texture(Gdx.files.internal(filename));
                textureMap.put(filename, texture);
            } else {
                // If the file does not exist / Gdx.files is not ready, return null
                return null;
            }
        }
        // 3. Return the stored Texture
        return textureMap.get(filename);
    }

    // ========================================================================
// Register the Region Textures
// ========================================================================

    // Register a single region
    public void registerRegion(String key, TextureRegion region) {
        textureRegionMap.put(key, region);
    }

    // Extract a specific cell from the sprite sheet at [row][col]
    public void registerRegionFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int col) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            // Store the region grid[row][col] in textureRegionMap under this key
            textureRegionMap.put(key, grid[row][col]);
        }
    }

    // Convenience overload: register an animation starting at column 0 with PlayMode.LOOP
    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration) {
        registerAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, Animation.PlayMode.LOOP);
    }

    // Register a sequence of horizontal frames as an Animation object
    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);

            // 1. Create a TextureRegion[] array with numFrames elements
            TextureRegion[] frames = new TextureRegion[numFrames];

            // 2. Fill the array with grid[row][startCol + i]
            for (int i = 0; i < numFrames; i++) {
                frames[i] = grid[row][startCol + i];
            }

            // 3. Create Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);

            // 4. Set the animation's play mode
            anim.setPlayMode(playMode);

            // 5. Store anim in animationMap under key
            animationMap.put(key, anim);

            // 6. Store the first frame (frames[0]) in textureRegionMap under the same key (as the default sprite)
            textureRegionMap.put(key, frames[0]);
        }
    }

    // Overload for flipped animations (e.g., facing left using a horizontal flip)
    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration, boolean flipX, boolean flipY) {
        registerFlippedAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, Animation.PlayMode.LOOP, flipX, flipY);
    }

    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode, boolean flipX, boolean flipY) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);

            TextureRegion[] frames = new TextureRegion[numFrames];
            for (int i = 0; i < numFrames; i++) {
                // Copy each frame using new TextureRegion(...) then flip it
                TextureRegion frame = new TextureRegion(grid[row][startCol + i]);
                frame.flip(flipX, flipY);
                frames[i] = frame;
            }

            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            anim.setPlayMode(playMode);

            animationMap.put(key, anim);
            textureRegionMap.put(key, frames[0]);
        }
    }

// ========================================================================
// Getting the Region Textures
// ========================================================================

    // Retrieve a region by key
    public TextureRegion getTextureRegion(String key) {
        return textureRegionMap.get(key);
    }

    public TextureRegion getRegion(String key) {
        return getTextureRegion(key);
    }

    // Retrieve an animation by key
    public Animation<TextureRegion> getAnimation(String key) {
        return animationMap.get(key);
    }

    public void init() {
        // TODO:
        // Add any missing animation registrations according to the table.
        // Use the registerAnimationFromSheet() overload
        registerAnimationFromSheet("player_idle", "player.png", 32, 48, 0, 0, 8, 0.125f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("player_left", "player.png", 32, 48, 1, 0, 4, 0.12f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("player_right", "player.png", 32, 48, 2, 0, 4, 0.12f, Animation.PlayMode.LOOP);

        registerAnimationFromSheet("boss_idle", "rumia.png", 64, 64, 0, 0, 4, 0.2f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("boss_left", "rumia.png", 64, 64, 1, 0, 4, 0.15f, Animation.PlayMode.REVERSED);
        registerAnimationFromSheet("boss_right", "rumia.png", 64, 64, 2, 0, 4, 0.15f, Animation.PlayMode.NORMAL);

        registerAnimationFromSheet("fairy_idle_red", "fairy.png", 32, 32, 1, 0, 8, 0.125f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("fairy_idle_blue", "fairy.png", 32, 32, 0, 0, 8, 0.125f, Animation.PlayMode.LOOP);
        registerRegionFromSheet("bullet_danmaku", "bullets_small.png", 16, 16, 2, 3);

        registerRegionFromSheet("item_power", "items.png", 16, 16, 0, 0);
        registerRegionFromSheet("item_point", "items.png", 16, 16, 0, 1);
        registerRegionFromSheet("item_bomb", "items.png", 16, 16, 0, 3);
        registerRegionFromSheet("item_life", "items.png", 16, 16, 0, 5);

        registerRegionFromSheet("bullet_amulet", "amulet_reimu.png", 16, 16, 0, 0);
        registerRegionFromSheet("bullet_amulet_homing", "amulet_reimu.png", 16, 16, 1, 0);
    }


    public void dispose() {

        // TODO: Release the VRAM resources of all loaded Textures, then clear all Maps
        for (Texture texture : textureMap.values()) {
            texture.dispose();
        }

        textureMap.clear();
        textureRegionMap.clear();
        animationMap.clear();
    }
}


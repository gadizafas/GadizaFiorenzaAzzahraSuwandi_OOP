package com.Gadiza.frontend.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;

public class AudioSystem {
    private static AudioSystem instance;

    private Map<String, Sound> soundMap;
    private Map<String, Music> musicMap;
    private Music currentMusic;

    private AudioSystem() {
        soundMap = new HashMap<>();
        musicMap = new HashMap<>();
    }

    public static AudioSystem getInstance() {
        if (instance == null) {
            instance = new AudioSystem();
        }
        return instance;
    }

    // Load SFX
    public void loadSound(String key, String filepath) {
        if (!soundMap.containsKey(key)) {
            if (Gdx.files != null && Gdx.files.internal(filepath).exists()) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal(filepath));
                soundMap.put(key, sound);
            }
        }
    }

    // Load BGM
    public void loadMusic(String key, String filepath) {
        if (!musicMap.containsKey(key)) {
            if (Gdx.files != null && Gdx.files.internal(filepath).exists()) {
                Music music = Gdx.audio.newMusic(Gdx.files.internal(filepath));
                musicMap.put(key, music);
            }
        }
    }

    public void playSound(String key) {
        Sound sound = soundMap.get(key);
        if (sound != null) {
            // Volume set to 50%
            sound.play(0.5f);
        }
    }

    public void playMusic(String key, boolean looping) {
        // Stop currently playing music track before switching
        if (currentMusic != null) {
            currentMusic.stop();
        }
        Music music = musicMap.get(key);
        if (music != null) {
            currentMusic = music;
            currentMusic.setLooping(looping);
            // Volume set to 40%
            currentMusic.setVolume(0.4f);
            currentMusic.play();
        }
    }

}

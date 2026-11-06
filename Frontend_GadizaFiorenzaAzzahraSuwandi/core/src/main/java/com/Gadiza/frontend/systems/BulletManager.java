package com.Gadiza.frontend.systems;

import com.Gadiza.frontend.objects.GameObject;
import com.Gadiza.frontend.objects.items.Item;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.Gadiza.frontend.objects.bullets.Bullet;
import com.Gadiza.frontend.objects.bullets.BulletType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BulletManager {
    private List<Bullet> activePlayerBullets;
    private List<Bullet> activeEnemyBullets;

    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;

    public BulletManager() {
        activePlayerBullets = new ArrayList<>();
        activeEnemyBullets = new ArrayList<>();
        playerBulletPool = new LinkedList<>();
        enemyBulletPool = new LinkedList<>();
    }

    // Object Pool Spawning for Player Bullets via EntityFactory
    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        if (!playerBulletPool.isEmpty()) {
            bullet = playerBulletPool.poll(); // Recycles pooled object
            bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
        } else {
            bullet = EntityFactory.createPlayerBullet(x, y, damage); // Factory Pattern
            bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
        }
        activePlayerBullets.add(bullet);
        return bullet;
    }

    // Object Pool Spawning for Enemy Bullets via EntityFactory
    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        if (!enemyBulletPool.isEmpty()) {
            bullet = enemyBulletPool.poll(); // Recycles pooled object
            bullet.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
        } else {
            bullet = EntityFactory.createEnemyBullet(x, y, damage); // Factory Pattern
            bullet.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
        }
        activeEnemyBullets.add(bullet);
        return bullet;
    }

    public void update(float delta, float screenWidth, float screenHeight) {
        // Safe update & recycling of player bullets using Iterator
        Iterator<Bullet> pIter = activePlayerBullets.iterator();
        while (pIter.hasNext()) {
            Bullet bullet = pIter.next();
            bullet.update(delta);

            if (bullet.isOffScreen(screenWidth, screenHeight) || bullet.isDestroyed()) {
                bullet.destroy();
                pIter.remove();
                playerBulletPool.offer(bullet); // Return to pool!
            }
        }

        // Safe update & recycling of enemy bullets using Iterator
        Iterator<Bullet> eIter = activeEnemyBullets.iterator();
        while (eIter.hasNext()) {
            Bullet bullet = eIter.next();
            bullet.update(delta);

            if (bullet.isOffScreen(screenWidth, screenHeight) || bullet.isDestroyed()) {
                bullet.destroy();
                eIter.remove();
                enemyBulletPool.offer(bullet); // Return to pool!
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Bullet b : activePlayerBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
        for (Bullet b : activeEnemyBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
    }

    public void freePlayerBullet(Bullet bullet) {
        if (activePlayerBullets.remove(bullet)) {
            bullet.destroy();
            playerBulletPool.offer(bullet);
        }
    }

    public void freeEnemyBullet(Bullet bullet) {
        if (activeEnemyBullets.remove(bullet)) {
            bullet.destroy();
            enemyBulletPool.offer(bullet);
        }
    }

    public List<Bullet> getActivePlayerBullets() { return activePlayerBullets; }
    public List<Bullet> getActiveEnemyBullets() { return activeEnemyBullets; }
    public int getPlayerPoolSize() { return playerBulletPool.size(); }
    public int getEnemyPoolSize() { return enemyBulletPool.size(); }

    // Spell Card (Bomb) Execution: Clear all active enemy bullets and convert them into POINT item drops!
    public void clearEnemyBullets(List<GameObject> entities) {
        // TODO:
        // 1. Create an Iterator<Bullet> to safely traverse activeEnemyBullets.
        Iterator<Bullet> iterator = activeEnemyBullets.iterator();
        // 2. While the iterator has a next element:
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
        }
        //    - Retrieve the current Bullet object.
        //    - If the entities parameter is not null:
        //      * Create an Item object of type ItemType.POINT using EntityFactory.createItem(...)
        //        at position (bullet.getX(), bullet.getY()), then add it to the entities list.
        if (entities != null) {
            Item pointItem = EntityFactory.createItem(bullet.getX(), bullet.getY(), itemType.POINT);
            entities.add(pointItem);;
        }
        //    - Mark the bullet as destroyed (bullet.destroy()).
        bullet.destroy();
        //    - Remove the bullet from activeEnemyBullets using remove() on the iterator from step 1.
        iterator.remove();
        //    - Return the bullet to the enemyBulletPool queue using offer().
        return enemyBulletPool.offer();
        // TIP: See the rough outline in `clearPlayerBullets()` below
    }

    public void clearEnemyBullets() {
        clearEnemyBullets(null);
    }

    // Clear all player bullets when restarting the game (Copy-Paste):
    public void clearPlayerBullets() {
        Iterator<Bullet> pIter = activePlayerBullets.iterator();
        while (pIter.hasNext()) {
            Bullet bullet = pIter.next();
            bullet.destroy();
            pIter.remove();
            playerBulletPool.offer(bullet);
        }
    }

}

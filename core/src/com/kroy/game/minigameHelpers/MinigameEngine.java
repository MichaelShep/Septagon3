package com.kroy.game.minigameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.game.Constants;
import com.kroy.game.FireEngine;

public class MinigameEngine extends Sprite {

    private MinigameBullet[] bullets;
    private FireEngine passedEngine;
    private int fireCooldown = 30;

    public MinigameEngine(FireEngine passedEngine){
        super(passedEngine.getTexture());
        this.passedEngine = passedEngine;
    }

    public void init(){
        //Sets up the engine to be the correct size and position
        this.setTexture(passedEngine.getTexture());
        this.setSize(Constants.getTileSize(), Constants.getTileSize());
        this.setPosition(-this.getWidth() / 2, -(Gdx.graphics.getHeight() / 2) + 20);

        bullets = new MinigameBullet[Constants.getNumEngineBullets()];

        for(int i = 0; i < bullets.length; i++){
            bullets[i] = new MinigameBullet(this, true);
        }
    }

    public void update(){
        fireCooldown++;
        for(MinigameBullet bullet: bullets){
            bullet.update();
        }
    }

    public void fireBullet(){
        if(fireCooldown >= 30) {
            for (MinigameBullet bullet : bullets) {
                if (!bullet.isHasFired()) {
                    bullet.fire();
                    break;
                }
            }
            fireCooldown = 0;
        }
    }


    public void renderBullets(OrthographicCamera cam){
        for(MinigameBullet bullet: bullets) {
            if (bullet.isHasFired()) {
                bullet.render(cam);
            }
        }
    }

    public MinigameBullet[] getBullets() { return bullets; }
}

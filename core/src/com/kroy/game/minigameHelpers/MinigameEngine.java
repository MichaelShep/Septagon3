package com.kroy.game.minigameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.game.Constants;
import com.kroy.game.FireEngine;

/***
 * Class created by Team Septagon
 * Used to handle the engine that is used for the minigame
 */
public class MinigameEngine extends Sprite {

    private MinigameBullet[] bullets;
    private FireEngine passedEngine;
    private int fireCooldown = 10;

    public MinigameEngine(FireEngine passedEngine){
        super(passedEngine.getTexture());
        this.passedEngine = passedEngine;
    }

    /**
     * Sets up all features of the engine
     */
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

    /***
     * Updates the engine
     */
    public void update(){
        fireCooldown++;
        for(MinigameBullet bullet: bullets){
            bullet.update();
        }
    }

    /***
     * Fires a bullet from the list of bullets
     */
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


    /***
     * Renders all the bullets that have been fired
     * @param cam The games Orthographic Camera
     */
    public void renderBullets(OrthographicCamera cam){
        for(MinigameBullet bullet: bullets) {
            if (bullet.isHasFired()) {
                bullet.render(cam);
            }
        }
    }

    //Getters
    public MinigameBullet[] getBullets() { return bullets; }
}

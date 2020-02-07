package com.kroy.game.minigameHelpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kroy.game.Character;
import com.kroy.game.Constants;

public class MinigameBullet {

    private ShapeRenderer bulletRenderer;
    private float x, y;
    private float velY;
    private Sprite sprite;
    private boolean up;
    private boolean hasFired = false;

    public MinigameBullet(Sprite sprite, boolean up){
        this.sprite = sprite;
        this.up = up;
        bulletRenderer = new ShapeRenderer();
    }

    public void fire(){
        x = sprite.getX() + sprite.getWidth() / 2;
        y = sprite.getY() + sprite.getHeight() / 2;

        if(up){
            velY = Constants.getMinigameBulletSpeed();
        }else{
            velY = -Constants.getMinigameBulletSpeed();
        }

        hasFired = true;
    }

    public void update(){
        if(hasFired){
            y += velY;
        }else{
            x = sprite.getX();
            y = sprite.getY();
        }

        if(y + Constants.getMinigameBulletHeight() >= Constants.getResolutionHeight() / 2){
            hasFired = false;
            this.x = sprite.getX() + sprite.getWidth() / 2;
            this.y = sprite.getY() + sprite.getHeight() / 2;
        }else if(y <= -Constants.getResolutionHeight() / 2){
            hasFired = false;
            System.out.println("Should remove bullet");
            this.x = sprite.getX() + sprite.getWidth() / 2;
            this.y = sprite.getY() + sprite.getHeight() / 2;
        }
    }

    public void render(OrthographicCamera cam){
        if(hasFired)
        {
            bulletRenderer.setProjectionMatrix(cam.combined);
            bulletRenderer.begin(ShapeRenderer.ShapeType.Filled);

            if(up)
                bulletRenderer.setColor(Color.BLUE);
            else
                bulletRenderer.setColor(Color.RED);
            bulletRenderer.rect(x, y, Constants.getMinigameBulletWidth(), Constants.getMinigameBulletHeight());

            bulletRenderer.end();
        }
    }

    public boolean isHasFired() { return hasFired; }
    public float getX() { return x; }
    public float getY() { return y; }

    public void setHasFired(boolean hasFired) { this.hasFired = hasFired; }
}

package com.kroy.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kroy.game.Constants;
import com.kroy.game.FireEngine;
import com.kroy.game.Patrol;
import com.kroy.game.SceneType;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class created by Team Septagon
 * This is used to handle all of the features and events of the minigame
 */

public class MinigameScene extends Scene {

    private FireEngine passedEngine;
    private Patrol passedPatrol;

    private Sprite minigameEngine;
    private Rectangle playableArea;

    private ShapeRenderer boundsRenderer;
    private Texture[] aliensTextures;
    private ArrayList<Sprite> aliens;

    private Random random;

    protected MinigameScene(BitmapFont font, OrthographicCamera cam, FireEngine passedEngine, Patrol passedPatrol) {
        super(font, cam);
        this.passedEngine = passedEngine;
        this.passedPatrol = passedPatrol;
    }

    @Override
    public void initScene() {
        sceneType = SceneType.SCENE_TYPE_MINIGAME;

        boundsRenderer = new ShapeRenderer();
        random = new Random();

        minigameEngine = new Sprite(passedEngine.getTexture());
        minigameEngine.setSize(Constants.getTileSize(), Constants.getTileSize());
        minigameEngine.setPosition(-minigameEngine.getWidth() / 2, -(Gdx.graphics.getHeight() / 2) + 20);

        playableArea = new Rectangle();
        playableArea.setSize(Constants.getResolutionHeight(), Constants.getResolutionHeight());
        playableArea.setPosition( -playableArea.getWidth() / 2, -playableArea.getHeight() / 2);

        aliensTextures = new Texture[Constants.getMinigameTextures().length];
        for(int i = 0; i < Constants.getMinigameTextures().length; i++){
            aliensTextures[i] = Constants.getManager().get(Constants.getResourceRoot() + Constants.getMinigameTextures()[i], Texture.class);
        }

        aliens = new ArrayList<Sprite>();

        float startX = -playableArea.width / 2;
        float startY = playableArea.height / 2 - Constants.getTileSize();
        for(int i = 0; i < 5; i++){
            Sprite newAlien = new Sprite();
            newAlien.setBounds(startX + (i * Constants.getTileSize()), startY, Constants.getTileSize(), Constants.getTileSize());
            newAlien.setTexture(aliensTextures[random.nextInt(aliensTextures.length)]);
            aliens.add(newAlien);
        }
    }

    @Override
    public void resolveScene() {
    }

    @Override
    public void renderScene(Batch batch) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        boundsRenderer.setProjectionMatrix(cam.combined);
        boundsRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //Draw background for the playableArea
        boundsRenderer.setColor(Color.DARK_GRAY);
        boundsRenderer.rect(playableArea.x, playableArea.y, playableArea.width, playableArea.height);

        boundsRenderer.setColor(Color.BLACK);
        //Draw the 2 boundary lines of the playableArea
        boundsRenderer.rectLine(new Vector2(playableArea.x, playableArea.y), new Vector2(playableArea.x, playableArea.y + playableArea.height), 5);
        boundsRenderer.rectLine(new Vector2(playableArea.x + playableArea.width, playableArea.y), new Vector2(playableArea.x + playableArea.width, playableArea.y + playableArea.height), 5);
        boundsRenderer.rectLine(new Vector2(playableArea.x, playableArea.y), new Vector2(playableArea.x + playableArea.width, playableArea.y),5);
        boundsRenderer.rectLine(new Vector2(playableArea.x, playableArea.y + playableArea.height), new Vector2(playableArea.x + playableArea.width, playableArea.y + playableArea.height),5);
        boundsRenderer.end();

        batch.begin();
        minigameEngine.draw(batch);
        for(Sprite alien: aliens){
            alien.draw(batch);
        }
        batch.end();
    }

    /**
     * Moves the engine sprite by changing the x position of the sprite
     * @param increment The amount the position should be changed by
     */
    public void moveEngine(float increment){
        minigameEngine.setX(minigameEngine.getX() + increment);

        //Checks that the engine stays within the bounds of the playable area
        if(minigameEngine.getX() <= playableArea.getX()){
            minigameEngine.setX(playableArea.getX());
        }

        if(minigameEngine.getX() >= playableArea.getX() + playableArea.getWidth() - minigameEngine.getWidth()){
            minigameEngine.setX(playableArea.getX() + playableArea.getWidth() - minigameEngine.getWidth());
        }
    }
}

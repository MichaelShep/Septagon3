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
import com.kroy.game.minigameHelpers.Alien;
import com.sun.org.apache.bcel.internal.Const;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class created by Team Septagon
 * This is used to handle all of the features and events of the minigame
 */

public class MinigameScene extends Scene {

    //Stores the engine and patrol that triggered the minigame
    private FireEngine passedEngine;
    private Patrol passedPatrol;

    //Holds the engine that is used for the minigame
    private Sprite minigameEngine;

    //Stores the area of the screen which the minigame can be played in
    private Rectangle playableArea;
    private ShapeRenderer boundsRenderer;

    //Stores all the aliens and their textures
    private Texture[] aliensTextures;
    private ArrayList<Alien> aliens;

    //Used to generate random values
    private Random random;

    //Variables to handle the countdown before the minigame actually starts when it first loads up
    private int countdownTimer = 180;
    private boolean started = false;
    private int secondsValue = 3;
    private GlyphLayout countdownText;

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

        //Sets up the engine to be the correct size and position
        minigameEngine = new Sprite(passedEngine.getTexture());
        minigameEngine.setSize(Constants.getTileSize(), Constants.getTileSize());
        minigameEngine.setPosition(-minigameEngine.getWidth() / 2, -(Gdx.graphics.getHeight() / 2) + 20);

        //Sets up the area of the screen that the minigame can take place in
        playableArea = new Rectangle();
        playableArea.setSize(Constants.getResolutionHeight(), Constants.getResolutionHeight());
        playableArea.setPosition( -playableArea.getWidth() / 2, -playableArea.getHeight() / 2);

        //Loads in all the different alien textures for the game
        aliensTextures = new Texture[Constants.getMinigameTextures().length];
        for(int i = 0; i < Constants.getMinigameTextures().length; i++){
            aliensTextures[i] = new Texture(Gdx.files.internal(Constants.getMinigameTextures()[i]));
        }

        aliens = new ArrayList<Alien>();

        float startX = -playableArea.width / 2 + Constants.getMinigameEdgeBuffer();
        float startY = playableArea.height / 2 - Constants.getTileSize() - Constants.getMinigameEdgeBuffer();
        float bufferBetweenAliens = 3;
        for(int i = 0; i < 5; i++){
            Alien newAlien = new Alien(0, passedPatrol.getHealth(), playableArea);
            newAlien.setBounds(startX + (i * Constants.getTileSize()) + bufferBetweenAliens, startY, Constants.getTileSize(), Constants.getTileSize());
            newAlien.setTexture(aliensTextures[random.nextInt(aliensTextures.length)]);
            aliens.add(newAlien);
        }

        //Sets up the countdown text to start
        font.setColor(Color.RED);
        countdownText = new GlyphLayout();
        countdownText.setText(font, "Starting in " + secondsValue);
    }

    @Override
    public void resolveScene() {
        if(started) {
            //Moves all the aliens
            for (Alien alien : aliens) {
                alien.move();
            }
        }else{
            countdownTimer -= 1;

            //Works out whether the minigame should be started or if the amount of seconds left should decrease
            if(countdownTimer <= 0){
                started = true;
            }else if(countdownTimer <= 120 && secondsValue == 3){
                secondsValue = 2;
                font.setColor(Color.RED);
                countdownText.setText(font, "Starting in " + secondsValue);
            }else if(countdownTimer <= 60 && secondsValue == 2){
                secondsValue = 1;
                font.setColor(Color.RED);
                countdownText.setText(font, "Starting in " + secondsValue);
            }
        }
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
        //Renders the engine and all aliens
        minigameEngine.draw(batch);
        for(Sprite alien: aliens){
            alien.draw(batch);
        }

        //Draws the countdown text if the game has not yet started
        if(!started){
            font.draw(batch, countdownText, -countdownText.width / 2, countdownText.height / 2);
        }

        batch.end();
    }

    /**
     * Moves the engine sprite by changing the x position of the sprite
     * @param increment The amount the position should be changed by
     */
    public void moveEngine(float increment) {
        minigameEngine.setX(minigameEngine.getX() + increment);

        //Checks that the engine stays within the bounds of the playable area
        if (minigameEngine.getX() <= playableArea.getX()) {
            minigameEngine.setX(playableArea.getX());
        }

        if (minigameEngine.getX() >= playableArea.getX() + playableArea.getWidth() - minigameEngine.getWidth()) {
            minigameEngine.setX(playableArea.getX() + playableArea.getWidth() - minigameEngine.getWidth());
        }
    }

    public boolean isStarted() { return started; }
}

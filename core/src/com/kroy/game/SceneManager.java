package com.kroy.game;

// ---------- clean up ----------

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kroy.game.scenes.GameScene;
import com.kroy.game.scenes.MainMenuScene;
import com.kroy.game.scenes.Scene;

import java.util.ArrayList;

//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;


public class SceneManager {

    private SceneType scene;
    private BitmapFont font;

    private OrthographicCamera cam;

    //assets

    private Scene currentScene;

    /**
     * Constructs a Class to manage and render scenes
     *
     * @param font the font you want to use in the game
     * @param cam the camera for the game
     */
    SceneManager(BitmapFont font, OrthographicCamera cam) {

        this.font = font;
        this.cam = cam;

        currentScene = new MainMenuScene(font, cam);
        currentScene.initScene();
    }

    /**
     * Initialises features for the current scene of the game
     */
    public void initCurrentScene()
    {
        currentScene.initScene();
    }

    /**
     * Updates elements for the current scene of the game
     */
    public void resolveCurrentScene()
    {
        currentScene.resolveScene();
    }

    /**
     * Renders elements for the current scene of the game
     * @param batch
     */
    public void renderCurrentScene(Batch batch)
    {
        currentScene.renderScene(batch);
    }

    public void changeScene(Scene newScene)
    {
        currentScene = newScene;
        currentScene.initScene();
    }


    // ------------ LOSE SCREEN ------------------------

    /**
     * Initialise the enemy win screen
     */
    public void initEnemyWinScreen() {
        //set humanWinScreen Components;
        scene = SceneType.SCENE_TYPE_ENEMYWIN;
        font.setColor(1f, 1f, 1f, 1f);
        cam.zoom = 2f;
        cam.viewportWidth = Constants.getResolutionWidth();
        cam.viewportHeight = Constants.getResolutionHeight();


    }

    /**
     * Calculates actions during enemy win scene
     */
    public void resolveEnemyWinScreen() {
        //resolve actions during this scene
    }

    /**
     * render the enemy the win screen
     *
     * @param batch the batch to render the scene through
     */
    public void renderEnemyWinScreen(Batch batch) {
        batch.begin();
        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "menuBackground.jpeg", Texture.class), -Constants.getResolutionWidth(), -Constants.getResolutionHeight(), Constants.getResolutionWidth() * 2, Constants.getResolutionHeight() * 2, 0, 0, 1880, 1058, false, false);

        font.draw(batch, "YOU LOSE", -(Constants.getResolutionWidth() / 6f), 100);
        font.draw(batch, "OUR HEROES HAVE FALLEN!", -(Constants.getResolutionWidth() / 2.65f), 0);
        font.draw(batch, "Press -SPACE- To RETURN", -(Constants.getResolutionWidth() / 2.9f), -100);
        batch.end();

    }


    // ------------ COMPONENTS ------------------------


    /**
     * any operations the camera needs to do every frame
     *
     * @param batch the batch the camera is rendering
     */
    public void cameraFrameOperation(Batch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
    }


    public OrthographicCamera getCam() {
        return cam;
    }

    public BitmapFont getFont() {
        return font;
    }

    public SceneType getScene() {
        return currentScene.getSceneType();
    }

    public Tile getSelectedTile() {
        if(getScene() == SceneType.SCENE_TYPE_GAME){
            GameScene gameScene = (GameScene) currentScene;
            return gameScene.getSelectedTile();
        }
        return null;
    }

    public void setSelectedTile(Tile selectedTile) {
        if(getScene() == SceneType.SCENE_TYPE_GAME){
            GameScene gameScene = (GameScene) currentScene;
            gameScene.setSelectedTile(selectedTile);
        }
    }

    public HighlightMap getHighlightMap() {
        if(getScene() == SceneType.SCENE_TYPE_GAME){
            GameScene gameScene = (GameScene) currentScene;
            return gameScene.getHighlightMap();
        }
        return null;
    }

    public Map getMap() {
        if(getScene() == SceneType.SCENE_TYPE_GAME){
            GameScene gameScene = (GameScene) currentScene;
            return gameScene.getMap();
        }

        return null;
    }

    public Tooltip getEnemyToolTip() {
        if(getScene() == SceneType.SCENE_TYPE_GAME){
            GameScene gameScene = (GameScene) currentScene;
            return gameScene.getEnemyToolTip();
        }

        return null;
    }

    public Tooltip getHumanToolTip() {
        if(getScene() == SceneType.SCENE_TYPE_GAME){
            GameScene gameScene = (GameScene) currentScene;
            return gameScene.getHumanToolTip();
        }

        return null;
    }

    public Human getHumanData(){
        if(getScene() == SceneType.SCENE_TYPE_GAME){
            GameScene gameScene = (GameScene) currentScene;
            return gameScene.getHumanData();
        }

        return null;
    }

    public Enemy getEnemyData(){
        if(getScene() == SceneType.SCENE_TYPE_GAME){
            GameScene gameScene = (GameScene) currentScene;
            return gameScene.getEnemyData();
        }

        return null;
    }
}

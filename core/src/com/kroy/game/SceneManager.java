package com.kroy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kroy.game.minigameHelpers.MinigameBullet;
import com.kroy.game.scenes.GameScene;
import com.kroy.game.scenes.MainMenuScene;
import com.kroy.game.scenes.MinigameScene;
import com.kroy.game.scenes.Scene;

import java.util.ArrayList;

/**
 * Used to handle all the scenes in the game and to allow you to swap between different scenes at any time
 */

public class SceneManager {

    private SceneType scene;
    private BitmapFont font;

    private OrthographicCamera cam;


    private Scene currentScene;
    //Variable that will allow you to get back to the GameScene from the MinigameScene
    private Scene previousScene;

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

    /**
     * Used to move to a different scene from the current one
     * @param newScene The new scene to be moved to
     */
    public void changeScene(Scene newScene)
    {
        if(currentScene != null){
            previousScene = currentScene;
        }
        currentScene = newScene;
        currentScene.initScene();
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

    /***
     * Method used to return to the GameScene after finishing the minigame
     * @param didWin Whether the player won the minigame or not
     */
    public void returnToPreviousScene(boolean didWin){
        GameScene gameScene = (GameScene) previousScene;
        MinigameScene minigameScene = (MinigameScene) currentScene;
        gameScene.returnFromMinigame(didWin, minigameScene.getPassedPatrol(), minigameScene.getPassedEngine());
        currentScene = gameScene;
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

    public Scene getCurrentScene() { return currentScene; }
}

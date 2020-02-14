package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.kroy.game.input.InputManager;
import com.kroy.game.scenes.GameScene;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class to bring all elements of the game together
 */

public class MainClass extends ApplicationAdapter {


    private SpriteBatch batch;
    private SceneManager sceneHelper;
    private BitmapFont font;
    private OrthographicCamera cam;
    private InputManager inputManager;

    /**
     * Remap a value from one range to another range
     *
     * @param value the value to scale
     * @param from1 range1 start
     * @param to1   range1 end
     * @param from2 range2 start
     * @param to2   range2 end
     * @return returns scaled value
     */
    private static float Remap(float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }

    /**
     * Instantiates all objects needed to run the game
     * Loads assets and resources
     */
    @Override
    public void create() {

        initSetting();

        loadFont();


        if (Constants.isFULLSCREEN()) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

        batch = new SpriteBatch();

        //define camera
        cam = new OrthographicCamera();
        cam.position.set(0, 0, 0);
        cam.zoom = 0.5f;
        cam.update();

        sceneHelper = new SceneManager(font, cam);
        sceneHelper.initCurrentScene();

        inputManager = new InputManager(sceneHelper, font, cam);
    }

    /**
     * Managers which render calls to trigger
     */
    @Override
    public void render() {
        //every frame actions
        sceneHelper.cameraFrameOperation(batch);
        inputManager.handleInput();

        sceneHelper.resolveCurrentScene();
        sceneHelper.renderCurrentScene(batch);

    }

    /**
     * Discard Objects sitting in memory at the end of the game
     */
    @Override
    public void dispose() {
        batch.dispose();
        
    }

    /**
     * Assign any setting you want to set before the game starts
     */
    private void initSetting() {
        Gdx.graphics.setWindowedMode(Constants.getResolutionWidth(), Constants.getResolutionHeight());
    }

    //helper functions

    /**
     * Loads the font needed for the game
     */
    private void loadFont() {

        //FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("desktop/build/resources/main/Fonts/RedHatDisplay.ttf"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/RedHatDisplay.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        parameter.color = Color.ORANGE;
        parameter.shadowColor = Color.BROWN;
        parameter.shadowOffsetX = 5;
        parameter.shadowOffsetY = 5;
        font = generator.generateFont(parameter);
        generator.dispose();
    }


}

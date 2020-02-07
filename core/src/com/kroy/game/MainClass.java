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

        //LOADING ASSETS
        System.out.println("BASE ROOT: " + Constants.getResourceRoot());
        loadTextures("");
        loadFont();


        if (Constants.isFULLSCREEN()) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

        while (!Constants.getManager().update()) {
            //LOOP WAITS UNTIL ALL ASSETS ARE LOADED
        }

        batch = new SpriteBatch();

        Array<Texture> allAssetsLoaded = new Array<Texture>();
        Constants.getManager().getAll(Texture.class, allAssetsLoaded);

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
     * Load in all texture into the assetManager with the suffix of an image
     * Leave root at an empty string to load in all assets in resource root
     *
     * @param root the root you want to start searching in
     */
    private void loadTextures(String root) {
        //all loading is auto, place all assets somewhere in the asset Root. Use Constants.Manager to access.
        System.out.println("---------------------------------------");
        File dir = new File(Constants.getResourceRoot() + root);
        System.out.println("Trying to load: " + Constants.getResourceRoot() + root);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isDirectory()) {
                    loadTextures(child.getName() + "/");
                } else {
                    if (child.getName().contains(".png") || child.getName().contains(".jpeg")) {
                        //System.out.println("Asset Found: "+ Constants.getResourceRoot() + root + child.getName());
                        Constants.getManager().load(Constants.getResourceRoot() + root + child.getName(), Texture.class);
                    } else {
                        System.out.println("Asset not an image: " + child.getName());
                    }
                }
            }
        } else {
            System.out.println(root + " was an empty location\n-------------------------");
        }


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

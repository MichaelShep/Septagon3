package com.kroy.game;

// ---------- clean up ----------
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;


public class SceneManager {

    SceneType scene;
    Camera cam;
    BitmapFont font;



    //assets
    Sprite titleSprite;


    SceneManager()
    {
        scene = SceneType.SCENE_TYPE_MAINMENU;


        //define camera
        cam = new OrthographicCamera();
        cam.position.set(0, 0, 0);
        cam.zoom = 0.5f;
        cam.update();



    }




    // ------------ MAIN MENU ------------------------


    public void initMainMenuScreen() {
        scene = SceneType.SCENE_TYPE_MAINMENU;
        titleSprite = new Sprite(Constants.getManager().get(Constants.getResourceRoot() + "title.png", Texture.class), 0, 0, 621, 168);
        titleSprite.setScale(2 * Constants.getResolutionWidth() / 1280f);
        titleSprite.setCenterX(0);
        titleSprite.setCenterY(Constants.getResolutionHeight() / 4);


        cam.viewportWidth = Constants.getResolutionWidth();
        cam.viewportHeight = Constants.getResolutionHeight();
        cam.zoom = 2f;


    }

    public void resolveMainMenuScreen(float runTime) {
        //titleSprite.setCenterX();
        titleSprite.setX((titleSprite.getX() - (float) Math.sin(2 * runTime)));
        titleSprite.setY((titleSprite.getY() + (float) Math.sin(4 * runTime)));


        font.setColor(1f, 1f, 1f, (float) Math.pow(Math.sin(0.5 * runTime), 2));
        cam.zoom = (float) (1.9 - Math.sin(0.5 * runTime) / 10f);

    }

    public void renderMainMenuScreen(Batch batch) {
        batch.begin();

        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "menuBackground.jpeg", Texture.class), -Constants.getResolutionWidth(), -Constants.getResolutionHeight(), Constants.getResolutionWidth() * 2, Constants.getResolutionHeight() * 2, 0, 0, 1880, 1058, false, false);
        titleSprite.draw(batch);
        font.draw(batch, "Press -SPACE- To Start", -(Constants.getResolutionWidth() / 2.8f), 0);
        font.draw(batch, "Press -ESC- To Exit", -(Constants.getResolutionWidth() / 3.3f), -150);


        batch.end();
    }











}

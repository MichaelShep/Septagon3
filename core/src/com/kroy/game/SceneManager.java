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


    Tooltip humanToolTip;
    Tooltip enemyToolTip;

    Tile selectedTile;

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

    // ------------ GAME SCREEN ------------------------

    public void initGameScreen(Map map, HighlightMap highLightMap, Human humanData, Enemy enemyData) {
        scene = SceneType.SCENE_TYPE_GAME;

        /*

        cam = new OrthographicCamera();
        cam.position.set(0, 0, 0);
        cam.update();
         */


        map = new Map(Constants.getResourceRoot()+ Constants.getMapFileName());
        map.setShiftX(-1024);
        map.setShiftY(-1728);

        highLightMap = new HighlightMap(map.getMapWidth(), map.getMapHeight());
        selectedTile = null;

        humanData = new Human("humanName", true, Constants.getFireengineCount());
        enemyData = new Enemy("EnemyName", false, Constants.getFortressCount());

        humanToolTip = new Tooltip("",0, 0, (int) (Constants.getTileSize() * (Constants.getResolutionWidth() / 1280f)), 312 * (int) (Constants.getResolutionWidth() / 1280f));
        humanToolTip.addValue("Icons/healthIcon.png", 0);
        humanToolTip.addValue( "Icons/damageIcon.png", 0);
        humanToolTip.addValue("Icons/rangeIcon.png", 0);
        humanToolTip.addValue("Icons/speedIcon.png", 0);
        humanToolTip.addValue("Icons/waterIcon.png", 0);
        humanToolTip.setX((int) (-((humanToolTip.getIconSize() + humanToolTip.getFontSpacing()) * humanToolTip.getValues().size()) / 2f));
        //humanToolTip.setY(100*(int)(Constants.getResolutionWidth()/1280f));
        humanToolTip.setY(Constants.getResolutionHeight() / 2 - (int) (200 * (Constants.getResolutionWidth() / 1280f - 1)) * 2);


        enemyToolTip = new Tooltip("", 0, 0, (int) (Constants.getTileSize() * (Constants.getResolutionWidth() / 1280f)), 312 * (int) (Constants.getResolutionWidth() / 1280f));
        enemyToolTip.addValue("Icons/healthIcon.png", 0);
        enemyToolTip.addValue("Icons/damageIcon.png", 0);
        enemyToolTip.addValue("Icons/rangeIcon.png", 0);
        enemyToolTip.setX((int) (-((enemyToolTip.getIconSize() + enemyToolTip.getFontSpacing()) * enemyToolTip.getValues().size()) / 2f));
        enemyToolTip.setY(Constants.getResolutionHeight() / 2 - (int) (200 * (Constants.getResolutionWidth() / 1280f - 1)) * 2);


        humanData.distributeTeamLocation(map.getNClosest(Constants.getFireengineCount(), map.getStationPosition(), TileType.TILE_TYPES_ROAD));
        enemyData.distributeTeamLocation(map.getFortressTiles());

        font.setColor(1f, 1f, 1f, 1f);

        cam.viewportWidth = map.getMapWidth() * Constants.getTileSize();
        cam.viewportHeight = map.getMapHeight() * Constants.getTileSize();
        cam.zoom = 0.5f;

    }

    public void resolveGameScreen(Map map, HighlightMap highlightMap, Human humanData, Enemy enemyData) {
        //in gameplay actions
        map.setShiftX(map.getShiftX() - (map.getShiftX() % Constants.getTileSize()));
        map.setShiftY(map.getShiftY() - (map.getShiftY() % Constants.getTileSize()));

        highLightMap.setShiftX(map.getShiftX() - (map.getShiftX() % Constants.getTileSize()));
        highLightMap.setShiftY(map.getShiftY() - (map.getShiftY() % Constants.getTileSize()));

        if (isEnemyWon()) {
            initEnemyWinScreen();
        }

        if (isHumanWon()) {
            initHumanWinScreen();
        }

        //enemy turn
        if (enemyData.isMyTurn()) {
            //check for deaths

            enemyData.decideTarget(enemyData.calculateTargets(map));

            enemyData.setMyTurn(false);
            humanData.setMyTurn(true);

            System.out.println("Enemy took their turn!");

            //Station heals and repairs its surroundings
            for (Tile surroundingTile: map.getWithRangeOfHub(map.getStationPosition(),Constants.getStationRange()))
            {
                if (surroundingTile.getInhabitant() instanceof FireEngine)
                {
                    surroundingTile.getInhabitant().setHealth(Math.min(surroundingTile.getInhabitant().getHealth()+Constants.getStationRepairAmount(),surroundingTile.getInhabitant().getMaxHealth()));
                    ((FireEngine) surroundingTile.getInhabitant()).setWaterAmount(Math.min(((FireEngine) surroundingTile.getInhabitant()).getWaterAmount()+Constants.getStationRefillAmount(),((FireEngine) surroundingTile.getInhabitant()).getWaterCapacity()));
                    System.out.println("Station Healed!");
                }
            }
        }
    }











}

package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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


public class MainClass extends ApplicationAdapter {


    SpriteBatch batch;

    Human humanData;
    Enemy enemyData;

    float runTime;
    SceneManager sceneHelper;

    BitmapFont font;
    //public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";




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
            //System.out.println("--LOADING-- " + Constants.getManager().getProgress() + " --LOADING--");
        }
        runTime = 0;
        batch = new SpriteBatch();

        Array<Texture> allAssetsLoaded = new Array<Texture>();
        Constants.getManager().getAll(Texture.class,allAssetsLoaded);
        for (Texture t: allAssetsLoaded)
        {
            System.out.println("TEXTURE LOADED: " + t.toString());
        }

        humanData = new Human("humanName", true, Constants.getFireengineCount());
        enemyData = new Enemy("EnemyName", false, Constants.getFortressCount());

        sceneHelper = new SceneManager(font);
        sceneHelper.initMainMenuScreen();
        //initGameScreen();
        //initHumanWinScreen();
        //initEnemyWinScreen();





    }

    @Override
    public void render() {
        if (Constants.getManager().update()) {
            //every frame actions
            sceneHelper.cameraFrameOperation(batch);
            runTime += Gdx.graphics.getDeltaTime();
            handleInput();

            if (sceneHelper.getScene() == SceneType.SCENE_TYPE_MAINMENU) {
                //main menu actions
                sceneHelper.resolveMainMenuScreen(runTime);
                sceneHelper.renderMainMenuScreen(batch);
            } else if (sceneHelper.getScene() == SceneType.SCENE_TYPE_GAME) {
                //game actions
                sceneHelper.resolveGameScreen(humanData,enemyData);
                sceneHelper.renderGameScreen(batch,enemyData,humanData);
            } else if (sceneHelper.getScene() == SceneType.SCENE_TYPE_HUMANWIN) {
                //humanWinScreen actions
                sceneHelper.resolveHumanWinScreen();
                sceneHelper.renderHumanWinScreen(batch);
            } else if (sceneHelper.getScene() == SceneType.SCENE_TYPE_ENEMYWIN) {
                //humanWinScreen actions
                sceneHelper.resolveEnemyWinScreen();
                sceneHelper.renderEnemyWinScreen(batch);
            }
        }
        //assets still loading
        else {
            // do something while waiting for assets to load

        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void loadTextures(String root) {
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

    public void initSetting() {
        Gdx.graphics.setWindowedMode(Constants.getResolutionWidth(), Constants.getResolutionHeight());
    }

    public void handleInput() {
        if (sceneHelper.getScene() == SceneType.SCENE_TYPE_MAINMENU) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                sceneHelper.initGameScreen(humanData,enemyData);
            }

        } else if (sceneHelper.getScene() == SceneType.SCENE_TYPE_GAME) {


            int moveSpeed = Constants.getTileSize();


            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                sceneHelper.getMap().setShiftX(sceneHelper.getMap().getShiftX() + moveSpeed);
                sceneHelper.getHighlightMap().setShiftX(sceneHelper.getHighlightMap().getShiftX() + moveSpeed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                sceneHelper.getMap().setShiftX(sceneHelper.getMap().getShiftX() - moveSpeed);
                sceneHelper.getHighlightMap().setShiftX(sceneHelper.getHighlightMap().getShiftX() - moveSpeed);

            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                sceneHelper.getMap().setShiftY(sceneHelper.getMap().getShiftY() + moveSpeed);
                sceneHelper.getHighlightMap().setShiftY(sceneHelper.getHighlightMap().getShiftY() + moveSpeed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                sceneHelper.getMap().setShiftY(sceneHelper.getMap().getShiftY() - moveSpeed);
                sceneHelper.getHighlightMap().setShiftY(sceneHelper.getHighlightMap().getShiftY() - moveSpeed);
            }


            sceneHelper.getMap().setShiftX(MathUtils.clamp(sceneHelper.getMap().getShiftX(), -3072, -1024));
            sceneHelper.getMap().setShiftY(MathUtils.clamp(sceneHelper.getMap().getShiftY(), -1728, -576));

            sceneHelper.getHighlightMap().setShiftX(MathUtils.clamp(sceneHelper.getHighlightMap().getShiftX(), -3072, -1024));
            sceneHelper.getHighlightMap().setShiftY(MathUtils.clamp(sceneHelper.getHighlightMap().getShiftY(), -1728, -576));


            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {


                double remapedShiftX = Remap(sceneHelper.getMap().getShiftX(), -3072, -1024, 0, sceneHelper.getMap().getMapWidth() * Constants.getTileSize() *sceneHelper.getCam().zoom);
                double remapedShiftY = Remap(sceneHelper.getMap().getShiftY(), -1728, -576, 0, sceneHelper.getMap().getMapHeight() * Constants.getTileSize() * sceneHelper.getCam().zoom);


                System.out.println("MOUSE PRESSED!");
                //int tileX = (int)Math.floor((Gdx.input.getX()/(float)Constants.getResolutionWidth())* sceneHelper.getMap().getMapWidth());
                //int tileY = (int)Math.floor((Gdx.input.getY()/(float)Constants.getResolutionHeight())* sceneHelper.getMap().getMapHeight());

                //double tileX = (Gdx.input.getX()/(float)Constants.getResolutionWidth())* sceneHelper.getMap().getMapWidth();
                //double tileY = (Gdx.input.getY()/(float)Constants.getResolutionHeight())* sceneHelper.getMap().getMapHeight();

                //int tileX = (int)Math.floor(Gdx.input.getX()/((float)Constants.getTileSize()*cam.zoom));
                //int tileY = sceneHelper.getMap().getMapHeight() - (int)Math.floor(Gdx.input.getY()/((float)Constants.getTileSize()*cam.zoom)) ;

                int tileX = (int) Math.floor(((Gdx.input.getX()) / (float) Constants.getResolutionWidth()) * sceneHelper.getMap().getMapWidth() * sceneHelper.getCam().zoom) + (int) (sceneHelper.getMap().getMapWidth() * sceneHelper.getCam().zoom - (remapedShiftX / Constants.getTileSize()));
                int tileY = sceneHelper.getMap().getMapHeight() - ((int) Math.floor(((Gdx.input.getY()) / (float) Constants.getResolutionHeight()) * sceneHelper.getMap().getMapHeight() * sceneHelper.getCam().zoom) + (int) ((remapedShiftY / Constants.getTileSize()))) - 1;

                System.out.println("[" + Gdx.input.getX() + "]X tile: " + tileX + " [" + Gdx.input.getY() + "] Y tile: " + tileY);
                System.out.println("X shift: " + remapedShiftX + " Y shift: " + remapedShiftY);


                //int tileX = (int)Math.floor((Gdx.input.getX()/(Constants.getResolutionWidth()/(float)sceneHelper.getMap().getMapWidth())));
                //int tileY = (int)Math.floor((Gdx.input.getY()/(Constants.getResolutionHeight()/(float)sceneHelper.getMap().getMapHeight())));


                tileClicked(tileX, tileY);

            }

            if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {


                double remapedShiftX = Remap(sceneHelper.getMap().getShiftX(), -3072, -1024, 0, sceneHelper.getMap().getMapWidth() * Constants.getTileSize() * sceneHelper.getCam().zoom);
                double remapedShiftY = Remap(sceneHelper.getMap().getShiftY(), -1728, -576, 0, sceneHelper.getMap().getMapHeight() * Constants.getTileSize() * sceneHelper.getCam().zoom);


                System.out.println("RIGHT MOUSE PRESSED!");
                //int tileX = (int)Math.floor((Gdx.input.getX()/(float)Constants.getResolutionWidth())* sceneHelper.getMap().getMapWidth());
                //int tileY = (int)Math.floor((Gdx.input.getY()/(float)Constants.getResolutionHeight())* sceneHelper.getMap().getMapHeight());

                //double tileX = (Gdx.input.getX()/(float)Constants.getResolutionWidth())* sceneHelper.getMap().getMapWidth();
                //double tileY = (Gdx.input.getY()/(float)Constants.getResolutionHeight())* sceneHelper.getMap().getMapHeight();

                //int tileX = (int)Math.floor(Gdx.input.getX()/((float)Constants.getTileSize()*cam.zoom));
                //int tileY = sceneHelper.getMap().getMapHeight() - (int)Math.floor(Gdx.input.getY()/((float)Constants.getTileSize()*cam.zoom)) ;

                int tileX = (int) Math.floor(((Gdx.input.getX()) / (float) Constants.getResolutionWidth()) * sceneHelper.getMap().getMapWidth() * sceneHelper.getCam().zoom) + (int) (sceneHelper.getMap().getMapWidth() * sceneHelper.getCam().zoom - (remapedShiftX / Constants.getTileSize()));
                int tileY = sceneHelper.getMap().getMapHeight() - ((int) Math.floor(((Gdx.input.getY()) / (float) Constants.getResolutionHeight()) * sceneHelper.getMap().getMapHeight() * sceneHelper.getCam().zoom) + (int) ((remapedShiftY / Constants.getTileSize()))) - 1;

                System.out.println("[" + Gdx.input.getX() + "]X tile: " + tileX + " [" + Gdx.input.getY() + "] Y tile: " + tileY);
                System.out.println("X shift: " + remapedShiftX + " Y shift: " + remapedShiftY);


                //int tileX = (int)Math.floor((Gdx.input.getX()/(Constants.getResolutionWidth()/(float)sceneHelper.getMap().getMapWidth())));
                //int tileY = (int)Math.floor((Gdx.input.getY()/(Constants.getResolutionHeight()/(float)sceneHelper.getMap().getMapHeight())));


                tileRightClicked(tileX, tileY);

            }

            if (!Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                sceneHelper.getHumanToolTip().setRender(false);
                sceneHelper.getEnemyToolTip().setRender(false);
            }


        } else if (sceneHelper.getScene() == SceneType.SCENE_TYPE_HUMANWIN || sceneHelper.getScene() == SceneType.SCENE_TYPE_ENEMYWIN) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                sceneHelper.initMainMenuScreen();
            }

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
            System.exit(0);
        }


    }

    public void tileClicked(int x, int y) {
        //clicking tiles can only be done on your turn
        Tile queryTile = sceneHelper.getMap().getMapData()[y][x];

        if (humanData.isMyTurn()) {
            if (sceneHelper.getSelectedTile() == null) {
                if (queryTile.getInhabitant() instanceof FireEngine) {

                    sceneHelper.setSelectedTile(queryTile);
                    sceneHelper.getHighlightMap().getMapData()[y][x].setTexName( "HighlightTexture/selected.png");

                    //place green moves
                    ArrayList<Tile> moveSpaces = sceneHelper.getMap().getWithRangeOfHub(queryTile, ((FireEngine) queryTile.getInhabitant()).getSpeed(), TileType.TILE_TYPES_ROAD);
                    for (Tile tile : moveSpaces) {
                        if (tile.getInhabitant() == null) {
                            sceneHelper.getHighlightMap().setTile(tile.getMapX(), tile.getMapY(), TileType.TILE_TYPES_ROAD);

                        }
                    }

                    ArrayList<Tile> attackSpaces = sceneHelper.getMap().getWithRangeOfHub(queryTile, queryTile.getInhabitant().getRange());
                    for (Tile tile : attackSpaces) {
                        if (tile.getInhabitant() instanceof Fortress) {
                            sceneHelper.getHighlightMap().setTile(tile.getMapX(), tile.getMapY(), TileType.TILE_TYPES_FORTRESS);
                        }
                    }


                    sceneHelper.getHighlightMap().setRender(true);

                }

            }

            //a tile is already selected
            else {
                System.out.println("---- " + sceneHelper.getHighlightMap().getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName());
                if (sceneHelper.getHighlightMap().getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName() == "HighlightTexture/move.png") {
                    System.out.println("MOVE");
                    sceneHelper.getSelectedTile().getInhabitant().transferTo(queryTile);

                    sceneHelper.setSelectedTile(null);
                    sceneHelper.getHighlightMap().resetMap();
                    sceneHelper.getHighlightMap().setRender(false);
                    humanData.setMyTurn(false);
                    enemyData.setMyTurn(true);
                } else if (sceneHelper.getHighlightMap().getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName() =="HighlightTexture/attack.png") {
                    System.out.println("ATTACK");
                    if (((FireEngine) sceneHelper.getSelectedTile().getInhabitant()).getWaterAmount() > 0) {
                        sceneHelper.getSelectedTile().getInhabitant().shootTarget(queryTile.getInhabitant());
                        sceneHelper.setSelectedTile(null);
                        sceneHelper.getHighlightMap().resetMap();
                        sceneHelper.getHighlightMap().setRender(false);
                        humanData.setMyTurn(false);
                        enemyData.setMyTurn(true);
                    } else {
                        //has no water routine


                    }


                } else if (sceneHelper.getHighlightMap().getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName() == "HighlightTexture/selected.png") {
                    sceneHelper.setSelectedTile(null);
                    sceneHelper.getHighlightMap().resetMap();
                    sceneHelper.getHighlightMap().setRender(false);
                }
            }


        }


    }

    public void tileRightClicked(int x, int y) {
        Tile queryTile = sceneHelper.getMap().getMapData()[y][x];
        Character queryInhabitant = queryTile.getInhabitant();

        if (!(queryInhabitant == null)) {
            if (queryInhabitant instanceof FireEngine) {
                sceneHelper.getHumanToolTip().updateValue( "Icons/healthIcon.png", queryInhabitant.getHealth() + "/" + queryInhabitant.getMaxHealth());
                sceneHelper.getHumanToolTip().updateValue( "Icons/damageIcon.png", queryInhabitant.getDamage());
                sceneHelper.getHumanToolTip().updateValue( "Icons/rangeIcon.png", queryInhabitant.getRange());
                sceneHelper.getHumanToolTip().updateValue( "Icons/speedIcon.png", ((FireEngine) queryInhabitant).getSpeed());
                sceneHelper.getHumanToolTip().updateValue( "Icons/waterIcon.png", ((FireEngine) queryInhabitant).getWaterAmount() + "/" + ((FireEngine) queryInhabitant).getWaterCapacity());
                sceneHelper.getHumanToolTip().setRender(true);
            } else if (queryInhabitant instanceof Fortress) {
                sceneHelper.getEnemyToolTip().setName(((Fortress)queryInhabitant).getName());
                sceneHelper.getEnemyToolTip().updateValue( "Icons/healthIcon.png", queryInhabitant.getHealth());
                sceneHelper.getEnemyToolTip().updateValue( "Icons/damageIcon.png", queryInhabitant.getDamage());
                sceneHelper.getEnemyToolTip().updateValue( "Icons/rangeIcon.png", queryInhabitant.getRange());
                sceneHelper.getEnemyToolTip().setRender(true);
            }

        }


    }




    public void loadFont() {

        //FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("desktop/build/resources/main/Fonts/RedHatDisplay.ttf"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/RedHatDisplay.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(64 * (Constants.getResolutionWidth()/1280f));
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        parameter.color = Color.ORANGE;
        parameter.shadowColor = Color.BROWN;
        parameter.shadowOffsetX= 5;
        parameter.shadowOffsetY= 5;
        font = generator.generateFont(parameter);
        generator.dispose();



        //font = new BitmapFont();
    }


    //helper function
    public static float Remap(float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }

    @Override
    public void resize(int width, int height) {
        if (sceneHelper.getScene() == SceneType.SCENE_TYPE_GAME) {

            sceneHelper.getCam().viewportWidth = sceneHelper.getMap().getMapWidth() * Constants.getTileSize();
            sceneHelper.getCam().viewportHeight = sceneHelper.getMap().getMapHeight() * Constants.getTileSize();
        } else {
            sceneHelper.getCam().viewportWidth = Constants.getResolutionWidth();
            sceneHelper.getCam().viewportHeight = Constants.getResolutionHeight();
        }


        sceneHelper.getCam().update();
    }



}

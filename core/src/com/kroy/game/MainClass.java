package com.kroy.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.freetype.*;

import java.io.File;
import java.util.ArrayList;


public class MainClass extends ApplicationAdapter {


    Map map;
    HighlightMap highLightMap;
    Tile selectedTile;
    SceneType scene;


    SpriteBatch batch;
    OrthographicCamera cam;

    Human humanData;
    Enemy enemyData;

    Sprite titleSprite;

    float startTime;
    float runTime;

    BitmapFont font;
    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";


    @Override
    public void create() {

        initSetting();
        loadTextures("");
        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        while (!Constants.getManager().update()) {
            //System.out.println("--LOADING-- " + Constants.getManager().getProgress() + " --LOADING--");
        }
        runTime = 0;
        initMainMenuScreen();
        batch = new SpriteBatch();


        cam = new OrthographicCamera();
        cam.position.set(0, 0, 0);
        cam.zoom = 0.5f;
        cam.update();

    }

    @Override
    public void render() {
        if (Constants.getManager().update()) {
            //every frame actions
            Gdx.gl.glClearColor(0, 0, 0, 1);
            cam.update();
            batch.setProjectionMatrix(cam.combined);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            handleInput();
            runTime += Gdx.graphics.getDeltaTime();

            if (scene == SceneType.SCENE_TYPE_MAINMENU) {
                //main menu actions
                resolveMainMenuScreen();
                renderMainMenuScreen();
            } else if (scene == SceneType.SCENE_TYPE_GAME) {
                //game actions
                resolveGameScreen();
                renderGameScreen();
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

        File dir = new File(Constants.getResourceRoot() + root);
        System.out.println("Searching in: " + root);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isDirectory()) {
                    loadTextures(child.getName() + "/");
                } else {
                    if (child.getName().contains("Thumbs.db")) {
                        System.out.println("Thumbs was found.");
                    } else {
                        System.out.println("Asset Found: " + root + child.getName());
                        Constants.getManager().load(root + child.getName(), Texture.class);
                    }
                }
            }
        } else {
            System.out.println(root + " cannot be Found");
        }


    }

    public void initSetting() {
        Gdx.graphics.setWindowedMode(Constants.getResolutionWidth(), Constants.getResolutionHeight());
    }


    public void renderMap() {
        for (int height = 0; height < map.getMapHeight(); height++) {
            for (int width = 0; width < map.getMapWidth(); width++) {
                batch.draw(Constants.getManager().get(map.getMapData()[height][width].getTexName(), Texture.class), (width * Constants.getTileSize()) + map.getShiftX(), (height * Constants.getTileSize()) + map.getShiftY(), Constants.getTileSize(), Constants.getTileSize(), 0, 0, Constants.getTileSize(), Constants.getTileSize(), false, false);
            }
        }
    }

    public void renderHighLightMap() {
        for (int height = 0; height < highLightMap.getMapHeight(); height++) {
            for (int width = 0; width < highLightMap.getMapWidth(); width++) {
                batch.draw(Constants.getManager().get(highLightMap.getMapData()[height][width].getTexName(), Texture.class), (width * Constants.getTileSize()) + highLightMap.getShiftX(), (height * Constants.getTileSize()) + highLightMap.getShiftY(), Constants.getTileSize(), Constants.getTileSize(), 0, 0, Constants.getTileSize(), Constants.getTileSize(), false, false);
            }
        }
    }

    public void renderFireEngines() {
        Character[] humanCharacters = humanData.getTeam();
        for (Character fe : humanCharacters) {
            if (!(fe == null)) {
                map.placeOnMap(fe);
                fe.draw(batch);
            }
        }

    }

    public void renderUI() {
        //come back fix for varying map sizes
        batch.draw(Constants.getManager().get("borderArt.png", Texture.class), -1024, -576, 2048, 1152, 0, 0, 1280, 720, false, false);
    }

    public void renderFortresses() {
        Character[] enemyCharacters = enemyData.getTeam();

        for (Character fort : enemyCharacters) {
            if (!(fort == null)) {
                map.placeOnMap(fort);
                fort.draw(batch);
            }
        }

    }


    private void handleInput() {
        if (scene == SceneType.SCENE_TYPE_MAINMENU)
        {
            if (Gdx.input.isKeyJustPressed(Input.Keys.A))
            {
                initGameScreen();
            }

        }
        else if (scene == SceneType.SCENE_TYPE_GAME) {


            int moveSpeed = Constants.getTileSize();



            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                //cam.translate(-moveSpeed, 0, 0);
                map.setShiftX(map.getShiftX() + moveSpeed);
                highLightMap.setShiftX(highLightMap.getShiftX() + moveSpeed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                //cam.translate(moveSpeed, 0, 0);
                map.setShiftX(map.getShiftX() - moveSpeed);
                highLightMap.setShiftX(highLightMap.getShiftX() - moveSpeed);

            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                //cam.translate(0, -moveSpeed, 0);
                map.setShiftY(map.getShiftY() + moveSpeed);
                highLightMap.setShiftY(highLightMap.getShiftY() + moveSpeed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                //cam.translate(0, moveSpeed, 0);
                map.setShiftY(map.getShiftY() - moveSpeed);
                highLightMap.setShiftY(highLightMap.getShiftY() - moveSpeed);
            }


            map.setShiftX(MathUtils.clamp(map.getShiftX(), -3072, -1024));
            map.setShiftY(MathUtils.clamp(map.getShiftY(), -1728, -576));

            highLightMap.setShiftX(MathUtils.clamp(highLightMap.getShiftX(), -3072, -1024));
            highLightMap.setShiftY(MathUtils.clamp(highLightMap.getShiftY(), -1728, -576));


            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {


                double remapedShiftX = Remap(map.getShiftX(), -3072, -1024, 0, map.getMapWidth() * Constants.getTileSize() * cam.zoom);
                double remapedShiftY = Remap(map.getShiftY(), -1728, -576, 0, map.getMapHeight() * Constants.getTileSize() * cam.zoom);


                System.out.println("MOUSE PRESSED!");
                //int tileX = (int)Math.floor((Gdx.input.getX()/(float)Constants.getResolutionWidth())* map.getMapWidth());
                //int tileY = (int)Math.floor((Gdx.input.getY()/(float)Constants.getResolutionHeight())* map.getMapHeight());

                //double tileX = (Gdx.input.getX()/(float)Constants.getResolutionWidth())* map.getMapWidth();
                //double tileY = (Gdx.input.getY()/(float)Constants.getResolutionHeight())* map.getMapHeight();

                //int tileX = (int)Math.floor(Gdx.input.getX()/((float)Constants.getTileSize()*cam.zoom));
                //int tileY = map.getMapHeight() - (int)Math.floor(Gdx.input.getY()/((float)Constants.getTileSize()*cam.zoom)) ;

                int tileX = (int) Math.floor(((Gdx.input.getX()) / (float) Constants.getResolutionWidth()) * map.getMapWidth() * cam.zoom) + (int) (map.getMapWidth() * cam.zoom - (remapedShiftX / Constants.getTileSize()));
                int tileY = map.getMapHeight() - ((int) Math.floor(((Gdx.input.getY()) / (float) Constants.getResolutionHeight()) * map.getMapHeight() * cam.zoom) + (int) ((remapedShiftY / Constants.getTileSize()))) - 1;

                System.out.println("[" + Gdx.input.getX() + "]X tile: " + tileX + " [" + Gdx.input.getY() + "] Y tile: " + tileY);
                System.out.println("X shift: " + remapedShiftX + " Y shift: " + remapedShiftY);


                //int tileX = (int)Math.floor((Gdx.input.getX()/(Constants.getResolutionWidth()/(float)map.getMapWidth())));
                //int tileY = (int)Math.floor((Gdx.input.getY()/(Constants.getResolutionHeight()/(float)map.getMapHeight())));


                tileClicked(tileX, tileY);

            }

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
            System.exit(0);
        }


    }


    public void tileClicked(int x, int y) {
        //clicking tiles can only be done on your turn
        Tile queryTile = map.getMapData()[y][x];

        if (humanData.isMyTurn()) {
            if (selectedTile == null) {
                if (queryTile.getInhabitant() instanceof FireEngine) {

                    selectedTile = queryTile;
                    highLightMap.getMapData()[y][x].setTexName("HighlightTexture/selected.png");

                    //place green moves
                    ArrayList<Tile> moveSpaces = map.getWithRangeOfHub(queryTile, ((FireEngine) queryTile.getInhabitant()).getSpeed(), TileType.TILE_TYPES_ROAD);
                    for (Tile tile : moveSpaces) {
                        if (tile.getInhabitant() == null) {
                            highLightMap.setTile(tile.getMapX(), tile.getMapY(), TileType.TILE_TYPES_ROAD);

                        }
                    }

                    ArrayList<Tile> attackSpaces = map.getWithRangeOfHub(queryTile, queryTile.getInhabitant().getRange());
                    for (Tile tile : attackSpaces) {
                        if (tile.getInhabitant() instanceof Fortress) {
                            highLightMap.setTile(tile.getMapX(), tile.getMapY(), TileType.TILE_TYPES_FORTRESS);
                        }
                    }


                    highLightMap.setRender(true);

                }

            }

            //a tile is already selected
            else {
                System.out.println("---- " + highLightMap.getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName());
                if (highLightMap.getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName() == "HighlightTexture/move.png") {
                    System.out.println("MOVE");
                    selectedTile.getInhabitant().transferTo(queryTile);

                    selectedTile = null;
                    highLightMap.resetMap();
                    highLightMap.setRender(false);
                    humanData.setMyTurn(false);
                    enemyData.setMyTurn(true);
                } else if (highLightMap.getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName() == "HighlightTexture/attack.png") {
                    System.out.println("ATTACK");
                    selectedTile.getInhabitant().shootTarget(queryTile.getInhabitant());

                    selectedTile = null;
                    highLightMap.resetMap();
                    highLightMap.setRender(false);
                    humanData.setMyTurn(false);
                    enemyData.setMyTurn(true);


                } else if (highLightMap.getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName() == "HighlightTexture/selected.png") {
                    selectedTile = null;
                    highLightMap.resetMap();
                    highLightMap.setRender(false);
                }
            }


        }


    }


    @Override
    public void resize(int width, int height) {
        if (scene == SceneType.SCENE_TYPE_GAME) {

            cam.viewportWidth = map.getMapWidth() * Constants.getTileSize();
            cam.viewportHeight = map.getMapHeight() * Constants.getTileSize();
            cam.update();
        }
        else
        {
            cam.viewportWidth = Constants.getResolutionWidth();
            cam.viewportHeight = Constants.getResolutionHeight();
            cam.update();
        }
    }


    //helper function
    public static float Remap(float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }


    public boolean isEnemyWon() {
        humanData.resolveDeaths();
        if (humanData.getAliveCharacters() == 0) {
            return true;
        }

        return false;

    }

    public boolean isHumanWon() {
        enemyData.resolveDeaths();
        if (enemyData.getAliveCharacters() == 0) {
            return true;
        }

        return false;

    }


    public void resolveGameScreen() {
        //in gameplay actions
        map.setShiftX(map.getShiftX() - (map.getShiftX() % Constants.getTileSize()));
        map.setShiftY(map.getShiftY() - (map.getShiftY() % Constants.getTileSize()));

        highLightMap.setShiftX(map.getShiftX() - (map.getShiftX() % Constants.getTileSize()));
        highLightMap.setShiftY(map.getShiftY() - (map.getShiftY() % Constants.getTileSize()));

        if (isEnemyWon()) {
            System.out.println("ENEMY HAS WON!");
            System.exit(0);

        }

        if (isHumanWon()) {
            System.out.println("HUMAN HAS WON!");
            System.exit(0);
        }

        //enemy turn
        if (enemyData.isMyTurn()) {
            //check for deaths

            enemyData.decideTarget(enemyData.calculateTargets(map));

            enemyData.setMyTurn(false);
            humanData.setMyTurn(true);

            System.out.println("Enemy took their turn!");

        }

    }

    public void renderGameScreen() {
        //renders the game screen
        batch.begin();
        renderMap();
        renderFortresses();
        renderFireEngines();
        if (highLightMap.isRender()) {
            renderHighLightMap();
        }

        renderUI();
        batch.end();
    }

    public void resolveMainMenuScreen()
    {
        //titleSprite.setCenterX();
        titleSprite.setX((titleSprite.getX() - (float)Math.sin(2*runTime)));
        titleSprite.setY((titleSprite.getY() + (float)Math.sin(4*runTime)));
    }

    public void renderMainMenuScreen() {
        batch.begin();

        batch.draw(Constants.getManager().get("menuBackground.jpeg", Texture.class),-Constants.getResolutionWidth(),-Constants.getResolutionHeight(),Constants.getResolutionWidth()*2,Constants.getResolutionHeight()*2,0,0,1880,1058,false,false);
        titleSprite.draw(batch);
        font.draw(batch, "Press -SPACE- To Start",-390,0);
        font.draw(batch, "Press -ESC- To Exit",-340,-100);

        batch.end();
    }

    public void initMainMenuScreen() {
        scene = SceneType.SCENE_TYPE_MAINMENU;
        titleSprite = new Sprite(Constants.getManager().get("title.png", Texture.class),0,0,621,168);
        //titleSprite.setBounds(0,0, Constants.getResolutionWidth()/2,Constants.getResolutionHeight()/2);
        titleSprite.setScale(2);
        titleSprite.setCenterX(0);
        titleSprite.setCenterY(Constants.getResolutionHeight() / 4);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(System.getProperty("user.dir") + "/core/src/Data/"+"RedHatDisplay.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(64 * (Constants.getResolutionWidth()/1280));
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        parameter.color = Color.ORANGE;
        parameter.shadowColor = Color.BROWN;
        parameter.shadowOffsetX= 5;
        parameter.shadowOffsetY= 5;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!



    }

    public void initGameScreen() {
        scene = SceneType.SCENE_TYPE_GAME;

        map = new Map(Constants.getMapFileName());
        highLightMap = new HighlightMap(map.getMapWidth(), map.getMapHeight());
        selectedTile = null;

        humanData = new Human("humanName", true, Constants.getFireengineCount());
        enemyData = new Enemy("EnemyName", false, Constants.getFortressCount());

        humanData.distributeTeamLocation(map.getNClosest(Constants.getFireengineCount(), map.getStationPosition(), TileType.TILE_TYPES_ROAD));
        enemyData.distributeTeamLocation(map.getFortressTiles());
        

    }
}

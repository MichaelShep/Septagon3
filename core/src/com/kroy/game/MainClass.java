package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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


    Map map;
    HighlightMap highLightMap;
    Tile selectedTile;
    SceneType scene;

    SceneManager


    SpriteBatch batch;
    OrthographicCamera cam;

    Human humanData;
    Tooltip humanToolTip;

    Enemy enemyData;
    Tooltip enemyToolTip;

    Sprite titleSprite;

    float startTime;
    float runTime;

    BitmapFont font;
    //public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

    //helper function
    public static float Remap(float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }

    @Override
    public void create() {

        initSetting();

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







        initMainMenuScreen();
        //initGameScreen();
        //initHumanWinScreen();
        //initEnemyWinScreen();
    }

    @Override
    public void render() {
        if (Constants.getManager().update()) {
            //every frame actions
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            cam.update();
            batch.setProjectionMatrix(cam.combined);
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
            } else if (scene == SceneType.SCENE_TYPE_HUMANWIN) {
                //humanWinScreen actions
                resolveHumanWinScreen();
                renderHumanWinScreen();
            } else if (scene == SceneType.SCENE_TYPE_ENEMYWIN) {
                //humanWinScreen actions
                resolveEnemyWinScreen();
                renderEnemyWinScreen();
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

    public void renderMap() {
        for (int height = 0; height < map.getMapHeight(); height++) {
            for (int width = 0; width < map.getMapWidth(); width++) {
                batch.draw(Constants.getManager().get(Constants.getResourceRoot() + map.getMapData()[height][width].getTexName(), Texture.class), (width * Constants.getTileSize()) + map.getShiftX(), (height * Constants.getTileSize()) + map.getShiftY(), Constants.getTileSize(), Constants.getTileSize(), 0, 0, Constants.getTileSize(), Constants.getTileSize(), false, false);
            }
        }
    }

    public void renderHighLightMap() {
        for (int height = 0; height < highLightMap.getMapHeight(); height++) {
            for (int width = 0; width < highLightMap.getMapWidth(); width++) {
                batch.draw(Constants.getManager().get(Constants.getResourceRoot() + highLightMap.getMapData()[height][width].getTexName(), Texture.class), (width * Constants.getTileSize()) + highLightMap.getShiftX(), (height * Constants.getTileSize()) + highLightMap.getShiftY(), Constants.getTileSize(), Constants.getTileSize(), 0, 0, Constants.getTileSize(), Constants.getTileSize(), false, false);
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
        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "borderArt.png", Texture.class), -1024, -576, 2048, 1152, 0, 0, 1280, 720, false, false);


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

    public void handleInput() {
        if (scene == SceneType.SCENE_TYPE_MAINMENU) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                initGameScreen();
            }

        } else if (scene == SceneType.SCENE_TYPE_GAME) {


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

            if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {


                double remapedShiftX = Remap(map.getShiftX(), -3072, -1024, 0, map.getMapWidth() * Constants.getTileSize() * cam.zoom);
                double remapedShiftY = Remap(map.getShiftY(), -1728, -576, 0, map.getMapHeight() * Constants.getTileSize() * cam.zoom);


                System.out.println("RIGHT MOUSE PRESSED!");
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


                tileRightClicked(tileX, tileY);

            }

            if (!Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                humanToolTip.setRender(false);
                enemyToolTip.setRender(false);
            }


        } else if (scene == SceneType.SCENE_TYPE_HUMANWIN || scene == SceneType.SCENE_TYPE_ENEMYWIN) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                initMainMenuScreen();
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
                    highLightMap.getMapData()[y][x].setTexName( "HighlightTexture/selected.png");

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
                } else if (highLightMap.getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName() =="HighlightTexture/attack.png") {
                    System.out.println("ATTACK");
                    if (((FireEngine) selectedTile.getInhabitant()).getWaterAmount() > 0) {
                        selectedTile.getInhabitant().shootTarget(queryTile.getInhabitant());
                        selectedTile = null;
                        highLightMap.resetMap();
                        highLightMap.setRender(false);
                        humanData.setMyTurn(false);
                        enemyData.setMyTurn(true);
                    } else {
                        //has no water routine


                    }


                } else if (highLightMap.getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName() == "HighlightTexture/selected.png") {
                    selectedTile = null;
                    highLightMap.resetMap();
                    highLightMap.setRender(false);
                }
            }


        }


    }

    public void tileRightClicked(int x, int y) {
        Tile queryTile = map.getMapData()[y][x];
        Character queryInhabitant = queryTile.getInhabitant();

        if (!(queryInhabitant == null)) {
            if (queryInhabitant instanceof FireEngine) {
                humanToolTip.updateValue( "Icons/healthIcon.png", queryInhabitant.getHealth() + "/" + queryInhabitant.getMaxHealth());
                humanToolTip.updateValue( "Icons/damageIcon.png", queryInhabitant.getDamage());
                humanToolTip.updateValue( "Icons/rangeIcon.png", queryInhabitant.getRange());
                humanToolTip.updateValue( "Icons/speedIcon.png", ((FireEngine) queryInhabitant).getSpeed());
                humanToolTip.updateValue( "Icons/waterIcon.png", ((FireEngine) queryInhabitant).getWaterAmount() + "/" + ((FireEngine) queryInhabitant).getWaterCapacity());
                humanToolTip.setRender(true);
            } else if (queryInhabitant instanceof Fortress) {
                enemyToolTip.setName(((Fortress)queryInhabitant).getName());
                enemyToolTip.updateValue( "Icons/healthIcon.png", queryInhabitant.getHealth());
                enemyToolTip.updateValue( "Icons/damageIcon.png", queryInhabitant.getDamage());
                enemyToolTip.updateValue( "Icons/rangeIcon.png", queryInhabitant.getRange());
                enemyToolTip.setRender(true);
            }

        }


    }

    @Override
    public void resize(int width, int height) {
        if (scene == SceneType.SCENE_TYPE_GAME) {

            cam.viewportWidth = map.getMapWidth() * Constants.getTileSize();
            cam.viewportHeight = map.getMapHeight() * Constants.getTileSize();
        } else {
            cam.viewportWidth = Constants.getResolutionWidth();
            cam.viewportHeight = Constants.getResolutionHeight();
        }


        cam.update();
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


    public void renderGameScreen() {
        //renders the game screen
        batch.begin();
        renderMap();
        renderFortresses();
        renderFireEngines();
        if (highLightMap.isRender()) {
            renderHighLightMap();
        }

        if (humanToolTip.isRender()) {
            renderTooltip(humanToolTip);
        }
        if (enemyToolTip.isRender()) {
            renderTooltip(enemyToolTip);
        }


        renderUI();
        batch.end();
    }




    public void initMainMenuScreen() {
        scene = SceneType.SCENE_TYPE_MAINMENU;


        titleSprite = new Sprite(Constants.getManager().get(Constants.getResourceRoot() + "title.png", Texture.class), 0, 0, 621, 168);
        //titleSprite.setBounds(0,0, Constants.getResolutionWidth()/2,Constants.getResolutionHeight()/2);
        titleSprite.setScale(2 * Constants.getResolutionWidth() / 1280f);
        titleSprite.setCenterX(0);
        titleSprite.setCenterY(Constants.getResolutionHeight() / 4);


        cam.viewportWidth = Constants.getResolutionWidth();
        cam.viewportHeight = Constants.getResolutionHeight();
        cam.zoom = 2f;


    }



    public void initHumanWinScreen() {
        //set humanWinScreen Components;
        scene = SceneType.SCENE_TYPE_HUMANWIN;
        font.setColor(1f, 1f, 1f, 1f);
        cam.zoom = 2f;
        cam.viewportWidth = Constants.getResolutionWidth();
        cam.viewportHeight = Constants.getResolutionHeight();


    }

    public void initEnemyWinScreen() {
        //set humanWinScreen Components;
        scene = SceneType.SCENE_TYPE_ENEMYWIN;
        font.setColor(1f, 1f, 1f, 1f);
        cam.zoom = 2f;
        cam.viewportWidth = Constants.getResolutionWidth();
        cam.viewportHeight = Constants.getResolutionHeight();


    }

    public void resolveHumanWinScreen() {
        //resolve actions during this scene
    }

    public void resolveEnemyWinScreen() {
        //resolve actions during this scene
    }


    public void renderHumanWinScreen() {
        batch.begin();
        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "menuBackground.jpeg", Texture.class), -Constants.getResolutionWidth(), -Constants.getResolutionHeight(), Constants.getResolutionWidth() * 2, Constants.getResolutionHeight() * 2, 0, 0, 1880, 1058, false, false);

        font.draw(batch, "YOU WIN", -(Constants.getResolutionWidth() / 6f), 100);
        font.draw(batch, "THE KROY ARE NO MORE!", -(Constants.getResolutionWidth() / 2.8f), 0);
        font.draw(batch, "Press -SPACE- To RETURN", -(Constants.getResolutionWidth() / 2.75f), -100);
        batch.end();

    }

    public void renderEnemyWinScreen() {
        batch.begin();
        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "menuBackground.jpeg", Texture.class), -Constants.getResolutionWidth(), -Constants.getResolutionHeight(), Constants.getResolutionWidth() * 2, Constants.getResolutionHeight() * 2, 0, 0, 1880, 1058, false, false);

        font.draw(batch, "YOU LOSE", -(Constants.getResolutionWidth() / 6f), 100);
        font.draw(batch, "OUR HEROES HAVE FALLEN!", -(Constants.getResolutionWidth() / 2.65f), 0);
        font.draw(batch, "Press -SPACE- To RETURN", -(Constants.getResolutionWidth() / 2.9f), -100);
        batch.end();
        
    }



    public void renderTooltip(Tooltip data) {
        int baseIconSize = data.getIconSize();
        int textSize = data.getFontSpacing();

        //int baseWidth = (int)(data.getValues().size() * (baseIconSize + textSize) / ((float) Constants.getResolutionWidth() / 1280f));
        int baseWidth = data.getValues().size() * (baseIconSize + textSize);
        int baseHeight = (int) (200 * (Constants.getResolutionWidth() / 1280f));

        int xPos = data.getX();
        int yPos = data.getY();


        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "HighlightTexture/blank.png", Texture.class), xPos, yPos, baseWidth, baseHeight, 0, 0, 64, 64, false, false);
        font.draw(batch, data.getName(), -(float)Constants.getResolutionWidth()/2f, -Constants.getResolutionHeight()/2.5f);

        xPos += data.getIconSize() / 2;

        ArrayList<String> keys = new ArrayList<String>(data.getValues().keySet());

        for (int keyIndex = 0; keyIndex < keys.size(); keyIndex++) {
            batch.draw(Constants.getManager().get(Constants.getResourceRoot() + keys.get(keyIndex), Texture.class), xPos + (keyIndex * (baseIconSize + textSize)), yPos + 100 * (int) (Constants.getResolutionWidth() / 1280), baseIconSize, baseIconSize, 0, 0, 64, 64, false, false);
            font.draw(batch, " : " + (data.getValues().get(keys.get(keyIndex))).toString(), (int) (xPos + (keyIndex * (baseIconSize + textSize)) + baseIconSize), (int) yPos + baseIconSize + 100 * (int) (Constants.getResolutionWidth() / 1280));

        }

        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "controlInfo.png", Texture.class), Constants.getResolutionWidth() / 3f, -Constants.getResolutionHeight() / 2f, 272, 720, 0, 0, 272, 720, false, false);

    }



}

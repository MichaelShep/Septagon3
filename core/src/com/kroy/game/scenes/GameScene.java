package com.kroy.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kroy.game.*;
import com.kroy.game.Character;
import com.kroy.game.rendering.Renderer;

import javax.swing.text.Highlighter;
import java.util.ArrayList;

/**
 * Class made as a result of refractoring by Septagon
 * Used to handle the program when it is in the main game section
 */

public class GameScene extends Scene
{
    private Tooltip humanToolTip;
    private Tooltip enemyToolTip;

    private Map map;
    private HighlightMap highlightMap;
    private SceneManager sceneManager;
    private Tile selectedTile;

    private Human humanData;
    private Enemy enemyData;
    private BarManager barManager;
    private Renderer renderer;

    private Integer turnCounter = 0;

    //Creates an array of bullets
    public static ArrayList<Bullet> bullets;

    /***
     * Constructor to pass values to the GameScene
     * @param font The games font
     * @param cam The camera for the game
     * @param sceneManager SceneManager used to change states of the game
     */
    public GameScene(BitmapFont font, OrthographicCamera cam, SceneManager sceneManager)
    {
        super(font, cam);
        this.sceneManager = sceneManager;
    }

    @Override
    /**
     * Initialise data needed for game screen
     *
     * @param humanData the data of the human player
     * @param enemyData the data of the enemy player
     */
    public void initScene() {
        sceneType = SceneType.SCENE_TYPE_GAME;

        humanData = new Human( true, Constants.getFireengineCount());
        enemyData = new Enemy( false, Constants.getFortressCount(), Constants.getPatrolCount());

        map = new Map(Constants.getResourceRoot() + Constants.getMapFileName());
        map.setShiftX(-1024);
        map.setShiftY(-1728);

        highlightMap = new HighlightMap(map.getMapWidth(), map.getMapHeight());
        selectedTile = null;

        barManager = new BarManager(humanData, enemyData, map);
        renderer = new Renderer(map, highlightMap);

        humanToolTip = new Tooltip("", -900, 400, 75, 200);
        humanToolTip.addValue("Icons/healthIcon.png", 0);
        humanToolTip.addValue("Icons/damageIcon.png", 0);
        humanToolTip.addValue("Icons/rangeIcon.png", 0);
        humanToolTip.addValue("Icons/speedIcon.png", 0);
        humanToolTip.addValue("Icons/waterIcon.png", 0);


        enemyToolTip = new Tooltip("", -900, 400, 75, 200);
        enemyToolTip.addValue("Icons/healthIcon.png", 0);
        enemyToolTip.addValue("Icons/damageIcon.png", 0);
        enemyToolTip.addValue("Icons/rangeIcon.png", 0);

        humanData.distributeTeamLocation(map.getNClosest(Constants.getFireengineCount(), map.getStationPosition(), TileType.TILE_TYPES_ROAD));
        enemyData.distributePatrols(map.getNClosest(Constants.getPatrolCount(), map.getFortressTiles()[0], TileType.TILE_TYPES_ROAD));
        enemyData.distributeTeamLocation(map.getFortressTiles());

        font.setColor(1f, 1f, 1f, 1f);

        cam.viewportWidth = map.getMapWidth() * Constants.getTileSize();
        cam.viewportHeight = map.getMapHeight() * Constants.getTileSize();
        cam.zoom = 0.5f;

        //empty arraylist of bullet
        bullets = new ArrayList<Bullet>();
    }

    @Override
    /**
     * Calculates actions during game scene
     *
     * @param humanData the data of the human player
     * @param enemyData the data of the enemy player
     */
    public void resolveScene() {

        //Bullet update
        ArrayList<Bullet> bulletToRemove = new ArrayList<Bullet>();
        for (Bullet bullet : bullets)
        {
            System.out.print("bullet 123");
            float deltaTime = 1 / 60f;
            bullet.update(deltaTime);
            if (bullet.remove)
                bulletToRemove.add(bullet);
        }
        bullets.removeAll(bulletToRemove);

        //in gameplay actions
        map.setShiftX(map.getShiftX() - (map.getShiftX() % Constants.getTileSize()));
        map.setShiftY(map.getShiftY() - (map.getShiftY() % Constants.getTileSize()));
        barManager.setShiftX(map.getShiftX());
        barManager.setShiftY(map.getShiftY());

        highlightMap.setShiftX(map.getShiftX() - (map.getShiftX() % Constants.getTileSize()));
        highlightMap.setShiftY(map.getShiftY() - (map.getShiftY() % Constants.getTileSize()));

        if (isEnemyWon()) {
            sceneManager.changeScene(new EnemyWinScene(font, cam));
        }

        if (isHumanWon()) {
            sceneManager.changeScene(new HumanWinScene(font, cam));
        }

        //enemy turn
        if (enemyData.isMyTurn()) {
            //check for deaths

            enemyData.decideTarget(enemyData.calculateTargets(map));

            enemyData.setMyTurn(false);
            humanData.setMyTurn(true);
            turnCounter++;

            //Station heals and repairs its surroundings
            ((Station) map.getStationPosition()).refillTiles(map.getWithRangeOfHub(map.getStationPosition(), Constants.getStationRange()));
            ((Station) map.getStationPosition()).repairTiles(map.getWithRangeOfHub(map.getStationPosition(), Constants.getStationRange()));

            if (turnCounter % 15 == 0) { //Added by Septagon
                enemyData.improveFortresses();
            }
        }

        this.minigameTrigger();
    }

    @Override
    /**
     * render the game screen
     *
     * @param batch     the batch to render the scene through
     * @param humanData the data of the human player
     * @param enemyData the data of the enemy player
     */
    public void renderScene(Batch batch) {
        //renders the game screen
        batch.begin();
        renderer.renderMap(batch);
        renderer.renderEnemies(batch, enemyData);
        renderer.renderFireEngines(batch, humanData);
        renderer.renderBullet(batch);
        if (highlightMap.isRender()) {
            renderer.renderHighLightMap(batch);
        }

        if (humanToolTip.isRender()) {
            renderer.renderTooltip(humanToolTip, batch, font);
        }
        if (enemyToolTip.isRender()) {
            renderer.renderTooltip(enemyToolTip, batch, font);
        }

        renderer.renderUI(batch);
        batch.end();
        barManager.renderBars(cam);
    }

    // ------------ Scene Utilities ------------------------

    /**
     * test if the enemy has won in this scene
     *
     * @return the win state of the enemy
     */
    public boolean isEnemyWon() {
        humanData.resolveDeaths();
        return humanData.getAliveCharacters() == 0;

    }

    /**
     * test if the human has won in this scene
     *
     * @return the win state of the human
     */
    public boolean isHumanWon() {
        enemyData.resolveDeaths();
        return enemyData.getAliveCharacters() == 0;

    }

    /**
     * Added by Septagon
     * Checks whether the minigame should be triggered
     * If the minigame should be triggered, will switch to the minigame state
     */
    public void minigameTrigger(){
        for(Character c: humanData.getTeam()){
            int xPos = c.getLocation().getMapX();
            int yPos = c.getLocation().getMapY();

            Tile[] adjTiles = map.getNClosest(10, c.getLocation());

            for(Tile t: adjTiles){
                if(t.getInhabitant() != null){
                    if(t.getInhabitant().getType() == Character.Type.PATROL){
                        sceneManager.changeScene(new MinigameScene(font, cam, (FireEngine)c, (Patrol)t.getInhabitant(), sceneManager));
                    }
                }
            }
        }
    }

    /***
     * Called by the SceneManager to change the game accordingly when the user is returning from the Minigame
     * @param didWin Whether the user won the minigame
     * @param patrol The patrol that triggered the minigame
     * @param engine The engine that triggered the minigame
     */
    public void returnFromMinigame(boolean didWin, Patrol patrol, FireEngine engine){
        if(didWin){
            enemyData.getPatrols().remove(patrol);
            patrol.getLocation().setInhabitant(null);
            engine.setWaterAmount(engine.getWaterCapacity());
            engine.setHealth(engine.getMaxHealth());
        }else{
            humanData.getTeam().remove(engine);
        }
    }

    //Getters and Setters
    public HighlightMap getHighlightMap()
    {
        return highlightMap;
    }

    public Map getMap()
    {
        return map;
    }

    public Tooltip getHumanToolTip(){
        return humanToolTip;
    }

    public Tooltip getEnemyToolTip(){
        return enemyToolTip;
    }

    public Tile getSelectedTile()
    {
        return selectedTile;
    }

    public Human getHumanData()
    {
        return humanData;
    }

    public Enemy getEnemyData()
    {
        return enemyData;
    }

    public void setSelectedTile(Tile selectedTile)
    {
        this.selectedTile = selectedTile;
    }
}

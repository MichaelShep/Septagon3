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
        //humanToolTip.setX((int) (-((humanToolTip.getIconSize() + humanToolTip.getFontSpacing()) * humanToolTip.getValues().size()) / 2f));
        //humanToolTip.setY(100*(int)(Constants.getResolutionWidth()/1280f));
        //humanToolTip.setY(Constants.getResolutionHeight() / 2 - (int) (200 * (Constants.getResolutionWidth() / 1280f - 1)) * 2);


        enemyToolTip = new Tooltip("", -900, 400, 75, 200);
        enemyToolTip.addValue("Icons/healthIcon.png", 0);
        enemyToolTip.addValue("Icons/damageIcon.png", 0);
        enemyToolTip.addValue("Icons/rangeIcon.png", 0);
        //enemyToolTip.setX((int) (-((enemyToolTip.getIconSize() + enemyToolTip.getFontSpacing()) * enemyToolTip.getValues().size()) / 2f));
        //enemyToolTip.setY(Constants.getResolutionHeight() / 2 - (int) (200 * (Constants.getResolutionWidth() / 1280f - 1)) * 2);


        humanData.distributeTeamLocation(map.getNClosest(Constants.getFireengineCount(), map.getStationPosition(), TileType.TILE_TYPES_ROAD));
        enemyData.distributePatrols(map.getNClosest(Constants.getPatrolCount(), map.getFortressTiles()[0], TileType.TILE_TYPES_ROAD));
        enemyData.distributeTeamLocation(map.getFortressTiles());

        font.setColor(1f, 1f, 1f, 1f);

        cam.viewportWidth = map.getMapWidth() * Constants.getTileSize();
        cam.viewportHeight = map.getMapHeight() * Constants.getTileSize();
        cam.zoom = 0.5f;
    }

    @Override
    /**
     * Calculates actions during game scene
     *
     * @param humanData the data of the human player
     * @param enemyData the data of the enemy player
     */
    public void resolveScene() {
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

        //Test code - should be removed when minigame implementation is done
        sceneManager.changeScene(new MinigameScene(font, cam, (FireEngine)humanData.getTeam()[0], (Patrol)enemyData.getPatrols()[0]));
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

    // ------------ Scene Utility ------------------------

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
                        sceneManager.changeScene(new MinigameScene(font, cam, (FireEngine)c, (Patrol)t.getInhabitant()));
                    }
                }
            }
        }
    }

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

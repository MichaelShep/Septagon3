package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;

import java.io.File;


public class MainClass extends ApplicationAdapter {


	Map map;
	HighlightMap highLightMap;
	Tile selectedTile;

	SpriteBatch batch;
	OrthographicCamera cam;

	Human humanData;
	Enemy enemyData;





	@Override
	public void create () {

		initSetting();
		loadTextures("");

		while(!Constants.getManager().update())
		{
			//System.out.println("--LOADING-- " + Constants.getManager().getProgress() + " --LOADING--");
		}
		System.out.println("Finished Loading!");


		map = new Map(Constants.getMapFileName());
		highLightMap = new HighlightMap(map.getMapWidth(),map.getMapHeight());
		selectedTile = null;


		humanData = new Human("humanName",true, Constants.getFireengineCount());
		enemyData = new Enemy("EnemyName",false, Constants.getFortressCount());

		humanData.distributeTeamLocation(map.getNClosest(Constants.getFireengineCount(), map.getStationPosition(),TileType.TILE_TYPES_ROAD));
		enemyData.distributeTeamLocation(map.getFortressTiles());




		batch = new SpriteBatch();



		cam = new OrthographicCamera();
		cam.position.set(0,0,0);
		cam.zoom = 0.5f;
		cam.update();

	}

	@Override
	public void render () {
		if (Constants.getManager().update()){
			Gdx.gl.glClearColor(0, 0, 0, 1);

			handleInput();
			cam.update();
			batch.setProjectionMatrix(cam.combined);


			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			//render loop
			batch.begin();
			renderMap();
			renderFortresses();
			renderFireEngines();
			if (highLightMap.isRender())
			{
				renderHighLightMap();
			}

			//renderUI();
            batch.end();

		}
		else {
		    // do something while waiting for assets to load

		}


	}



	@Override
	public void dispose () {
		batch.dispose();
	}

	public void loadTextures(String root){

		File dir = new File(Constants.getResourceRoot()+ root);
		System.out.println("Searching in: " + root);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.isDirectory())
				{
					loadTextures( child.getName()+"/");
				}
				else {
				    if (child.getName().contains("Thumbs.db"))
                    {
                        System.out.println("Thumbs was found.");
                    }
				    else
                    {
                        System.out.println("Asset Found: " + root+child.getName());
                        Constants.getManager().load(root+child.getName(), Texture.class);
                    }
				}
			}
		}
		else
		{
			System.out.println(root + " cannot be Found");
		}




	}

	public void initSetting(){
		Gdx.graphics.setWindowedMode(Constants.getResolutionWidth(),Constants.getResolutionHeight());
	}




	public void renderMap()
	{
		for(int height = 0; height < map.getMapHeight(); height++)
		{
			for(int width = 0; width < map.getMapWidth(); width++)
			{
				batch.draw(Constants.getManager().get(map.getMapData()[height][width].getTexName(), Texture.class),(width * Constants.getTileSize()) + map.getShiftX(),(height*Constants.getTileSize())+ map.getShiftY(), Constants.getTileSize(),Constants.getTileSize(),0,0,Constants.getTileSize(),Constants.getTileSize(),false,false);
			}
		}
	}

	public void renderHighLightMap()
	{
		for(int height = 0; height < highLightMap.getMapHeight(); height++)
		{
			for(int width = 0; width < highLightMap.getMapWidth(); width++)
			{
				batch.draw(Constants.getManager().get(highLightMap.getMapData()[height][width].getTexName(), Texture.class),(width * Constants.getTileSize()) + highLightMap.getShiftX(),(height*Constants.getTileSize())+ highLightMap.getShiftY(), Constants.getTileSize(),Constants.getTileSize(),0,0,Constants.getTileSize(),Constants.getTileSize(),false,false);
			}
		}
	}

	public void renderFireEngines()
	{
		Character[] humanCharacters = humanData.getTeam();
		for(Character fe: humanCharacters)
		{
			map.placeOnMap(fe);
		}

		for (int feIndex = 0; feIndex < humanCharacters.length; feIndex++)
		{
			humanCharacters[feIndex].draw(batch);
		}
	}

	public void renderUI()
	{
		//come back fix for varying map sizes
		batch.draw(Constants.getManager().get("borderArt.png", Texture.class),-1024 , -576, 2048, 1152,0,0,1280,720,false,false);
	}

	public void renderFortresses()
	{
		Character[] enemyCharacters = enemyData.getTeam();

		for(Character fort: enemyCharacters)
		{
			map.placeOnMap(fort);
		}

		for (int fortIndex = 0; fortIndex < enemyCharacters.length; fortIndex++)
		{
			enemyCharacters[fortIndex].draw(batch);
		}
	}


	private void handleInput() {
		int moveSpeed = 32;

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

		map.setShiftX(MathUtils.clamp(map.getShiftX(),-3072,-1024));
		map.setShiftY(MathUtils.clamp(map.getShiftY(),-1728,-576));

		highLightMap.setShiftX(MathUtils.clamp(highLightMap.getShiftX(),-3072,-1024));
		highLightMap.setShiftY(MathUtils.clamp(highLightMap.getShiftY(),-1728,-576));


		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
		{
			System.out.println("MOUSE PRESSED!");
			int tileX = (int)Math.floor((Gdx.input.getX()/(float)Constants.getResolutionWidth())* map.getMapWidth());
			int tileY = (int)Math.floor((Gdx.input.getY()/(float)Constants.getResolutionHeight())* map.getMapHeight());
			System.out.println("X tile: " + tileX + " Y tile: " + tileY);
			//tileClicked(tileX, tileY);

		}



	}


	public void tileClicked(int x, int y)
	{
		//clicking tiles can only be done on your turn
		if (humanData.isMyTurn())
		{
			if (selectedTile == null)
			{
				selectedTile = map.getMapData()[y][x];
				//selectedTile.setTexName("HighlightTexture/attack.png");
				highLightMap.getMapData()[y][x].setTexName("HighlightTexture/selected.png");
				highLightMap.setRender(true);

			}


		}



	}




	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = map.getMapWidth()*Constants.getTileSize();
		cam.viewportHeight = map.getMapHeight()*Constants.getTileSize();
		cam.update();
	}







}

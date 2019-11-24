package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class MainClass extends ApplicationAdapter {


	Map mapData;
	SpriteBatch batch;





	@Override
	public void create () {

		initSetting();
		loadTextures();

		mapData = new Map();
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		if (Constants.getManager().update()){
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glClearColor(0, 0, 0, 1);

			batch.begin();

			mapData.renderMap(batch);
            batch.draw(Constants.getManager().get("borderArt.png", Texture.class), 0, 0, Constants.getResolutionWidth(), Constants.getResolutionHeight(),0,0,1280,720,false,false);
			batch.end();

		}
		else{
			System.out.println("--LOADING-- " + Constants.getManager().getProgress() + " --LOADING--");
		}


	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void loadTextures(){
		Constants.getManager().load("grassTile.png", Texture.class);
		Constants.getManager().load("stationTile.png", Texture.class);
		Constants.getManager().load("horiRoadTile.png", Texture.class);
		Constants.getManager().load("vertRoadTile.png", Texture.class);
		Constants.getManager().load("crossRoadTile.png", Texture.class);
		Constants.getManager().load("fortressTile.png", Texture.class);
		Constants.getManager().load("borderArt.png", Texture.class);
	}

	public void initSetting(){
		Gdx.graphics.setWindowedMode(Constants.getResolutionWidth(),Constants.getResolutionHeight());
	}









}

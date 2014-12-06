package com.paranoidalien.game.dules.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.paranoidalien.game.dules.DULES;
import com.paranoidalien.game.dules.Entities.Player;
import com.paranoidalien.game.dules.Input.GameInputProcessor;
import com.paranoidalien.game.dules.Utils.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by Adam on 11/24/2014.
 */


public class GameScreen implements Screen {
    final DULES game;
    private OrthographicCamera cam;
    private SpriteBatch batch, playerBatch;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Player player;
    private int tempZoom = 38;

    public GameScreen(final DULES game){
        this.game = game;
        batch = new SpriteBatch();

        // Get actual screen resolution in pixels
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Create tile map from Tiled .tmx file
        tiledMap = new TmxMapLoader().load("adam_test1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);

        // Create camera and only project 35 world units wide
        // The 35 * (h / w) keeps the height sized so as to maintain the proper aspect ratio
        // Work in world units from this point onward

        //cam = new OrthographicCamera(15, 15 * (h / w));
        cam = new OrthographicCamera(tempZoom, tempZoom * (h / w));
        cam.position.set(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f, 0);
        cam.update();

        // Create characters-----------------------------------------
        // Create Player
        player = new Player(batch);
        player.setLocation(new Vector2(15f, 15f));

        // Create input processor
        Gdx.input.setInputProcessor(new GameInputProcessor(cam, player));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);  // need to research this a bit...saw this online
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);

        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();

        // Update entities
        player.update();

        // Center camera on player
        //cam.position.set(player.getLocation(),0);


        // Test code - REMOVE -----------------------
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            cam.translate(0, 3 * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            cam.translate(0, -3 * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            cam.translate(-3 * delta, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            cam.translate(3 * delta, 0);
        }
        // ------------------------------------------

        cam.update();

        // Draw sprites
        batch.begin();
        player.draw();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        tiledMapRenderer.dispose();
        tiledMap.dispose();
        batch.dispose();
    }
}

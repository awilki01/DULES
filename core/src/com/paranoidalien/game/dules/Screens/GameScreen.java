package com.paranoidalien.game.dules.Screens;

import com.badlogic.gdx.Gdx;
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
    private SpriteBatch batch;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Player player;
    private int tempZoom = 38;
    private BigDecimal bd;
    private float roundedDelta;

    public GameScreen(final DULES game){
        this.game = game;
        batch = new SpriteBatch();

        // Get actual screen resolution in pixels
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Create tile map from Tiled .tmx file
        tiledMap = new TmxMapLoader().load("map01.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);

        // Create camera and only project 35 world units wide
        // The 35 * (h / w) keeps the height sized so as to maintain the proper aspect ratio
        // Work in world units from this point onward

        //cam = new OrthographicCamera(15, 15 * (h / w));
        cam = new OrthographicCamera(tempZoom, tempZoom * (h / w));
        cam.position.set(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f, 0);
        cam.update();

        // Round Delta time to be used for player movement
        // We tried to use delta time, but there is an apparent rounding
        // error with openGL and that many significant digits that caused
        // artifacts with the tilemap (occasional white vertical lines)
        // We could not do this during the game loop (player move method)
        // as every iteration caused a new BigDecimal and new MathContext
        // to be created thus memory leak.

        bd = new BigDecimal(Gdx.graphics.getDeltaTime());
        bd = bd.round(new MathContext(2));
        roundedDelta = bd.floatValue();

        // Create characters-----------------------------------------
        // Create Player
        player = new Player(batch, roundedDelta);
        player.setLocation(new Vector2(15f, 15f));

        System.out.println(roundedDelta);

        // Create input processor
        Gdx.input.setInputProcessor(new GameInputProcessor(cam, player));





    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);  // need to research this a bit...saw this online
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Center camera on player
        cam.position.set(player.getLocation(),0);
        cam.update();

        batch.setProjectionMatrix(cam.combined);

        // Update entities
        player.update();

        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();


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

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

/**
 * Created by Adam on 11/24/2014.
 */


public class GameScreen implements Screen {
    final DULES game;
    private OrthographicCamera mainCam, playerCam;
    private SpriteBatch batch, playerBatch;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Player player;

    public GameScreen(final DULES game){
        this.game = game;
        batch = new SpriteBatch();
        playerBatch = new SpriteBatch();

        // Get actual screen resolution in pixels
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Create tile map from Tiled .tmx file
        tiledMap = new TmxMapLoader().load("map01.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);

        // Create camera and only project 35 world units wide
        // The 35 * (h / w) keeps the height sized so as to maintain the proper aspect ratio
        // Work in world units from this point onward

        //tileCam will be used for entities other than the HUD and Player.
        mainCam = new OrthographicCamera(38, 38 * (h / w));
        mainCam.position.set(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f, 0);
        mainCam.update();

        // Needed a separate playerCam because whenever we tried to move the player sprite,
        // there would be an ever so slight synchronization issue between the sprite and camera.
        playerCam = new OrthographicCamera(38, 38 * (h / w));
        playerCam.position.set(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f, 0);
        playerCam.update();

        // Create characters-----------------------------------------
        // Create Player
        player = new Player(playerBatch);
        player.setLocation(new Vector2(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2));

        // Create input processor
        Gdx.input.setInputProcessor(new GameInputProcessor(mainCam, playerCam, player));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.3f, 1);
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);  // need to research this a bit...saw this online
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(mainCam.combined);
        playerBatch.setProjectionMatrix(playerCam.combined);

        tiledMapRenderer.setView(mainCam);
        tiledMapRenderer.render();

        // Update entities here - will create an update entities class later
        // with array of Characters (see Character class)
        player.update();

        // Center camera on player
        mainCam.position.set(player.getLocation(),0);

        // This input will need to be moved to it's own class later
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            player.move(false, false, true, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            player.move(false, false, false, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            player.move(true, false, false, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            player.move(false, true, false, false);
        }
        // ---------------------------------------------------------


        mainCam.update();
        playerCam.update(); // Only needed for zoom at the moment
        //hudCam.update(); // will create this much later

        // Draw sprites
        playerBatch.begin();
        player.draw();
        playerBatch.end();

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

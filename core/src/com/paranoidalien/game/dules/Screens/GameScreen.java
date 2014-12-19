package com.paranoidalien.game.dules.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.paranoidalien.game.dules.DULES;
import com.paranoidalien.game.dules.Entities.BadGuy;
import com.paranoidalien.game.dules.Entities.Player;
import com.paranoidalien.game.dules.HUD.Inventory;
import com.paranoidalien.game.dules.HUD.InventoryActor;
import com.paranoidalien.game.dules.Input.GameInputProcessor;
import com.paranoidalien.game.dules.Utils.CollisionCheck;
import com.paranoidalien.game.dules.Utils.Constants;
import com.paranoidalien.game.dules.Utils.MyOrthogonalTiledMapRenderer;


/**
 * Created by Adam on 11/24/2014.
 */


public class GameScreen implements Screen {
    final DULES game;
    private OrthographicCamera mainCam, playerCam, hudCam;
    private SpriteBatch batch, playerBatch, hudBatch;
    private TiledMap tiledMap;
    private TiledMapTileLayer tiledMapLayer;
    private MyOrthogonalTiledMapRenderer tiledMapRenderer;
    private Player player;
    private BadGuy badGuy;
    private CollisionCheck collisionCheck;
    private Inventory inventory;
    private InventoryActor inventoryActor;
    private Stage uiStage;

    public GameScreen(final DULES game){
        this.game = game;
        batch = new SpriteBatch();
        playerBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();

        // Get actual screen resolution in pixels
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Main tiled map
        tiledMap = new TmxMapLoader().load("Blue_Map.tmx");
        tiledMapRenderer = new MyOrthogonalTiledMapRenderer(tiledMap, 1/64f);
        tiledMapLayer = (TiledMapTileLayer)tiledMap.getLayers().get("Tile Layer 1");

        // Create camera and only project 35 world units wide
        // The 35 * (h / w) keeps the height sized so as to maintain the proper aspect ratio
        // Work in world units from this point onward

        //mainCam will be used for entities other than the HUD and Player.
        mainCam = new OrthographicCamera(38, 38 * (h / w));
        mainCam.position.set(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f, 0);
        mainCam.update();

        // Needed a separate playerCam because whenever we tried to move the player sprite,
        // there would be an ever so slight synchronization issue between the sprite and camera.
        playerCam = new OrthographicCamera(38, 38 * (h / w));
        playerCam.position.set(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f, 0);
        playerCam.update();

        //HUD cam initial position (could technically use playerCam but this gives more options in future)
        hudCam = new OrthographicCamera(w, h);
        hudCam.position.set(w / 2, h / 2, 0);
        hudCam.update();

        // Instantiate collision check
        collisionCheck = new CollisionCheck(tiledMap, tiledMapLayer);

        // Create characters-----------------------------------------
        // Create Player
        player = new Player(playerBatch, collisionCheck);
        player.setLocation(new Vector2(17, 88)); // The "+ 1" is for the test map onlyddddddddd
        // Create BadGuy
        badGuy = new BadGuy(batch, collisionCheck);
        badGuy.setLocation(new Vector2(15, 89));

        // Create HUD ----------------------------------------
        inventory = new Inventory(hudBatch);
        //inventory.setPosition(new Vector2(0, 0));

        // Create input processor - used for Zoom only now....
        Gdx.input.setInputProcessor(new GameInputProcessor(mainCam, playerCam, hudCam, player, inventory));


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.3f, 1);
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);  // need to research this a bit...saw this online
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(mainCam.combined);
        playerBatch.setProjectionMatrix(playerCam.combined);
        hudBatch.setProjectionMatrix(hudCam.combined);

        tiledMapRenderer.setView(mainCam);
        tiledMapRenderer.render();

        // This input will need to be moved to it's own class later
        if (Gdx.input.isKeyPressed(Input.Keys.W)){ // up
            player.move(false, false, true, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){ // down
            player.move(false, false, false, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){ // left
            player.move(true, false, false, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){ // right
            player.move(false, true, false, false);
        }
        // ---------------------------------------------------------

        // Test input for badGuy -----------------------------------
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){ // up
            badGuy.move(false, false, true, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){ // down
            badGuy.move(false, false, false, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){ // left
            badGuy.move(true, false, false, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){ // right
            badGuy.move(false, true, false, false);
        }
        // ---------------------------------------------------------

        // Update entities here - will create an update entities class later
        // with array of Characters (see Character class)
        player.update();
        badGuy.update();
        inventory.update();

        // Center camera on player (actually centers on shadow sprite)
        mainCam.position.set(player.getLocation(),0);

        mainCam.update();
        playerCam.update(); // Only needed for zoom at the moment
        hudCam.update();

        // Draw player sprite
        playerBatch.begin();
        player.draw();
        playerBatch.end();

        // Draw everything else
        batch.begin();
        badGuy.draw();
        batch.end();

        // Draw HUD
        hudBatch.begin();
        inventory.draw();
        hudBatch.end();


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
        uiStage.dispose();
    }
}

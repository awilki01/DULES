package com.paranoidalien.game.dules.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.paranoidalien.game.dules.DULES;

/**
 * Created by Adam on 11/24/2014.
 */

public class MainMenuScreen implements Screen {

    final DULES game;
    private OrthographicCamera cam;


    public MainMenuScreen(final DULES game) {
        this.game = game;

        // Get screen resolution
        float screenWidthPixels = Gdx.graphics.getWidth();
        float screenHeightPixels = Gdx.graphics.getHeight();

        // Set up camera and place in middle of screen
        cam = new OrthographicCamera(screenWidthPixels, screenHeightPixels);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Hit any key to begin...", 100, 100);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            game.setScreen(new GameScreen(game));
        }

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

    }
}

package com.paranoidalien.game.dules.Input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.paranoidalien.game.dules.Entities.Player;

/**
 * Created by Adam on 11/24/2014.
 */
public class GameInputProcessor implements InputProcessor{
    private OrthographicCamera tileCam, playerCam;
    private Player player;

    public GameInputProcessor(OrthographicCamera tileCam, OrthographicCamera playerCam, Player player){
        this.tileCam = tileCam;
        this.playerCam = playerCam;
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            // boolean order: left, right, up, down
            case Input.Keys.DOWN:   player.move(false, false, false, true);
                                    break;
            case Input.Keys.UP:     player.move(false, false, true, false);
                                    break;
            case Input.Keys.RIGHT:  player.move(false, true, false, false);
                                    break;
            case Input.Keys.LEFT:   player.move(true, false, false, false);
                                    break;
            default:                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        tileCam.zoom = playerCam.zoom += amount * 0.1f;

        return false;
    }
}

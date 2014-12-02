package com.paranoidalien.game.dules.Input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Adam on 11/24/2014.
 */
public class MyInputProcessor implements InputProcessor{
    private OrthographicCamera cam;

    public MyInputProcessor(OrthographicCamera cam){
        this.cam = cam;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.DOWN:   cam.translate(0, -1);
                                    break;
            case Input.Keys.UP:     cam.translate(0, 1);
                                    break;
            case Input.Keys.RIGHT:  cam.translate(1, 0);
                                    break;
            case Input.Keys.LEFT:   cam.translate(-1, 0);
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
        cam.zoom += amount * 0.5f;

        return false;
    }
}

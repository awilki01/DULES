package com.paranoidalien.game.dules.Input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.paranoidalien.game.dules.Entities.Player;
import com.paranoidalien.game.dules.HUD.Inventory;

/**
 * Created by Adam on 11/24/2014.
 */
public class GameInputProcessor implements InputProcessor{
    private OrthographicCamera tileCam, playerCam, hudCam;
    private Player player;
    private Inventory inventory;
    private Vector3 vec3;
    private Vector2 vec;

    public GameInputProcessor(OrthographicCamera tileCam, OrthographicCamera playerCam, OrthographicCamera hudCam, Player player, Inventory inventory){
        this.tileCam = tileCam;
        this.playerCam = playerCam;
        this.hudCam = hudCam;
        this.player = player;
        this.inventory = inventory;

        vec = new Vector2();
        vec3 = new Vector3();
    }

    @Override
    public boolean keyDown(int keycode) {

        switch(keycode){
            //case Input.Keys.DOWN:   player.move(false, false, false, true);
            //                        break;
            //case Input.Keys.UP:     player.move(false, false, true, false);
            //                        break;
            //case Input.Keys.RIGHT:  player.move(false, true, false, false);
            //                        break;
            //case Input.Keys.LEFT:   player.move(true, false, false, false);
             //                       break;
            case Input.Keys.I:        inventory.toggle();
                                      break;
            default:                  break;
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
        vec3.x = screenX;
        vec3.y = screenY;
        vec3.z = 0;
        hudCam.unproject(vec3);
        vec.x = vec3.x;
        vec.y = vec3.y;
        inventory.mouseClick(vec);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        inventory.mouseUp();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        vec3.x = screenX;
        vec3.y = screenY;
        vec3.z = 0;
        hudCam.unproject(vec3);
        vec.x = vec3.x;
        vec.y = vec3.y;
        inventory.mouseDrag(vec);

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

package com.paranoidalien.game.dules.Utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.paranoidalien.game.dules.Entities.*;
import com.paranoidalien.game.dules.Entities.Character;

/**
 * Created by wilkdavi on 12/6/2014.
 */
public class CollisionCheck {
    private TiledMap map;
    private TiledMapTileLayer mapLayer;
    private Vector2 charLoc, nextMoveVec;
    private boolean collide;

    public CollisionCheck (TiledMap map, TiledMapTileLayer mapLayer){
        this.map = map;
        this.mapLayer = mapLayer;

        // To be used to determine what next coordinates are when player moves
        nextMoveVec = new Vector2();
    }

    public boolean willCollide (Character character, String direction) {
        charLoc = character.getLocation();

        // Set up next move vector
        if (direction == "left"){
            nextMoveVec.x = character.getLocation().x;
            nextMoveVec.y = character.getLocation().y;
        }

        if (direction == "right"){
            nextMoveVec.x = character.getLocation().x  + 1;
            nextMoveVec.y = character.getLocation().y;
        }

        if (direction == "up"){
            nextMoveVec.x = character.getLocation().x;
            nextMoveVec.y = character.getLocation().y + 1;
        }

        if (direction == "down"){
            nextMoveVec.x = character.getLocation().x;

            // I had to set it to y with no manipulation because the checks were being done during the transition phase
            // to the lower cell which is a float value and rounds to the lower whole int value.
            // Example: when the player is at position y = 15.9998 during the transition, the int is 15 so the
            // next move y value is 14 and thus a collision occurs.
            nextMoveVec.y = character.getLocation().y;
        }
        collide = mapLayer.getCell((int)nextMoveVec.x, (int) nextMoveVec.y).getTile().getProperties().containsKey("solid");
        return collide;
    }
}

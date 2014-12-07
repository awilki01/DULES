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
        charLoc = new Vector2();
    }

    public boolean willCollide (Character character, String direction) {
        collide = false;
        charLoc.x = (int)character.getLocation().x;
        charLoc.y = (int)character.getLocation().y;

        // Set up next move vector
        if (direction == "left"){
            nextMoveVec.x = charLoc.x - 1;
            nextMoveVec.y = charLoc.y;
        }

        if (direction == "right"){
            nextMoveVec.x = charLoc.x  + 1;
            nextMoveVec.y = charLoc.y;
        }

        if (direction == "up"){
            nextMoveVec.x = charLoc.x;
            nextMoveVec.y = charLoc.y + 1;
        }

        if (direction == "down"){
            nextMoveVec.x = charLoc.x;
            nextMoveVec.y = charLoc.y - 1;
        }
        collide = mapLayer.getCell((int)nextMoveVec.x, (int) nextMoveVec.y).getTile().getProperties().containsKey("solid");
        return collide;
    }
}

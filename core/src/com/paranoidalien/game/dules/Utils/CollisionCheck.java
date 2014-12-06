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
    private Vector2 charLoc;
    private boolean collide;

    public CollisionCheck (TiledMap map, TiledMapTileLayer mapLayer){
        this.map = map;
        this.mapLayer = mapLayer;
    }

    public boolean collision (Character character){
        charLoc = character.getLocation();
        System.out.println("Char loc:" + charLoc);
        collide = mapLayer.getCell((int)charLoc.x, (int) charLoc.y).getTile().getProperties().containsKey("solid");


        return false;
    }
}

package com.paranoidalien.game.dules.Entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Project: DULES
 * Created by Adam on 02 December 2014 at 7:37 PM.
 */
public abstract class Character {

    public Vector2 getLocation(){
        return null;
    };

    public void  setLocation(Vector2 vecSet){};

    public void draw(){};

    public void move(boolean leftMove, boolean rightMove, boolean upMove, boolean downMove){};

    public void update(){};


}

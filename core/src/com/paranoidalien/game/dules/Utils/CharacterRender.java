package com.paranoidalien.game.dules.Utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.paranoidalien.game.dules.Entities.*;
import com.paranoidalien.game.dules.Entities.Character;

/**
 * Project: DULES
 * Created by Adam on 02 December 2014 at 11:27 PM.
 */
public class CharacterRender {

    private com.paranoidalien.game.dules.Entities.Character[] characters;
    private SpriteBatch batch;

    public CharacterRender (Character[] characters, SpriteBatch batch){
        this.characters = characters;
        this.batch = batch;

    }

    public void render(){

    }
}

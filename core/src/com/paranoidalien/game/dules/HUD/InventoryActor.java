package com.paranoidalien.game.dules.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Project: DULES
 * Created by Adam on 13 December 2014 at 9:30 AM.
 */
public class InventoryActor extends Actor{
    //private TextureRegion region;
    private Texture texture;
    float actorX = 400, actorY = 0;
    public boolean started = false;


    public InventoryActor() {
        texture = new Texture(Gdx.files.internal("InventorySlots.png"));
        //region = new TextureRegion(texture);
        setBounds(actorX, actorY, texture.getWidth(), texture.getHeight());
        addListener(new InputListener(){
            @Override
           public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
               ((InventoryActor)event.getTarget()).started = true;
               System.out.println("screenX: " + x + "  screenY: " + y + "  pointer: " + pointer + "  button: " + button);
               return true;
           }
            @Override
            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                System.out.println("screenX: " + x + "  screenY: " + y + "  pointer: " + pointer);
            }

        });


    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        //Color color = getColor();
        //batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, actorX, actorY);
    }

    @Override
    public void act(float delta){
        if(started){
           // System.out.println("Touched");
        }
    }



}

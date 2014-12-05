package com.paranoidalien.game.dules.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.paranoidalien.game.dules.Utils.Constants;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Project: DULES
 * Created by Adam on 02 December 2014 at 7:38 PM.
 */
public class Player extends Character {

    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private Vector2 originalLoc, vec;
    private boolean leftMove, rightMove, upMove, downMove;
    private BigDecimal bd;
    private MathContext mc;

    public Player(SpriteBatch batch){
        this.batch = batch;

        texture = new Texture(Gdx.files.internal("smiley.png"));
        sprite = new Sprite(texture);

        // One world unit high and wide
        sprite.setSize(1, 1);

        // Initialze vector variables
        originalLoc = new Vector2();
        vec = new Vector2();

        //Initialize move booleans to false
        leftMove = rightMove = upMove = downMove = false;

        mc = new MathContext(2);
    }

    @Override
    public void draw(){
        sprite.draw(batch);
    }

    @Override
    public Vector2 getLocation() {
        vec.x = sprite.getX();
        vec.y = sprite.getY();
        return vec;
    }

    @Override
    public void setLocation(Vector2 vecSet) {
        sprite.setPosition(vecSet.x, vecSet.y);
    }

    @Override
    public void move(boolean leftMove, boolean rightMove, boolean upMove, boolean downMove){
        this.leftMove = leftMove;
        this.rightMove = rightMove;
        this.upMove = upMove;
        this.downMove = downMove;
    }

    @Override
    public void update(){
        // Round Delta time to be used for player movement
        // We tried to use delta time, but there is an apparent rounding
        // error with openGL and that many significant digits that caused
        // artifacts with the tilemap (occasional white vertical lines)
        // We could not do this during the game loop (player move method)
        // as every iteration caused a new BigDecimal and new MathContext
        // to be created thus memory leak.

        if (!leftMove && !rightMove && !upMove && !downMove){
            originalLoc.x = getLocation().x;
            originalLoc.y = getLocation().y;
        }
        if (leftMove){
            // Add logic to move one tile to left with smooth transition
            bd = new BigDecimal(Gdx.graphics.getDeltaTime());
            bd = bd.round(mc);
            float roundedDelta = bd.floatValue();
            System.out.println(roundedDelta);
            sprite.translate(-(Constants.ENTITY_SPEED) * roundedDelta  , 0.0f);
            if ((originalLoc.x - getLocation().x) >= 1){
                originalLoc.x -= 1;
                // Snap into position
                setLocation(originalLoc);
                leftMove = false;
            }
            bd = null;

        }
        if (rightMove){
           // Add logic to move one tile to right with smooth transition
            bd = new BigDecimal(Gdx.graphics.getDeltaTime());
            bd = bd.round(mc);
            float roundedDelta = bd.floatValue();
            System.out.println(roundedDelta);
            sprite.translate(Constants.ENTITY_SPEED * roundedDelta , 0.0f);
            if ((getLocation().x - originalLoc.x) >= 1){
                originalLoc.x += 1;
                // Snap into position
                setLocation(originalLoc);
                rightMove = false;
            }
            bd = null;
        }
        if (upMove){
            // Add logic to move one tile up with smooth transition
            bd = new BigDecimal(Gdx.graphics.getDeltaTime());
            bd = bd.round(mc);
            float roundedDelta = bd.floatValue();
            System.out.println(roundedDelta);
            sprite.translate(0, Constants.ENTITY_SPEED * roundedDelta);
            if ((getLocation().y - originalLoc.y) >= 1){
                originalLoc.y += 1;
                // Snap into position
                setLocation(originalLoc);
                upMove = false;
            }
            bd = null;
        }
        if (downMove){
            // Add logic to move one tile down with smooth transition
            bd = new BigDecimal(Gdx.graphics.getDeltaTime());
            bd = bd.round(mc);
            float roundedDelta = bd.floatValue();
            System.out.println(roundedDelta);
            sprite.translate(0, -(Constants.ENTITY_SPEED) * roundedDelta);
            if ((originalLoc.y - getLocation().y) >= 1){
                originalLoc.y -= 1;
                // Snap into position
                setLocation(originalLoc);
                downMove = false;
            }
            bd = null;

        }

    }

}

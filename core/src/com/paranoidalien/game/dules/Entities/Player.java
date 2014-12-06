package com.paranoidalien.game.dules.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.paranoidalien.game.dules.Screens.GameScreen;
import com.paranoidalien.game.dules.Utils.Constants;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Project: DULES
 * Created by Adam on 02 December 2014 at 7:38 PM.
 */

/* NOTES:
        - Could not move player sprite and have camera follow.  It worked, but
          there was a slight synchronization issue where the sprite would move
          a pixel or two before or after the camera.  It would snap back in place just fine,
          but it did not look and/or feel right.  As a result, I created a shadow sprite (no texture)
          that was used to represent the position of the player and was used by the main camera to follow.
          The actual player sprite never moves, the main cam moves underneath it.  Playersprite uses it's own
          camera.........
*/

public class Player extends Character {

    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite, shadowSprite;
    private Vector2 originalLoc, vec;
    private boolean leftMove, rightMove, upMove, downMove;
    private boolean currentlyMoving;

    public Player(SpriteBatch batch){
        this.batch = batch;

        texture = new Texture(Gdx.files.internal("smiley.png"));
        sprite = new Sprite(texture);

        // used not for display but to represent position of sprite
        shadowSprite = new Sprite();

        // One world unit high and wide
        sprite.setSize(1, 1);

        // Initialze vector variables
        originalLoc = new Vector2();
        vec = new Vector2();

        //Initialize move booleans to false
        leftMove = rightMove = upMove = downMove = false;

        sprite.setPosition(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2);
    }

    @Override
    public void draw(){
        sprite.draw(batch);
    }

    @Override
    public Vector2 getLocation() {
        vec.x = shadowSprite.getX();
        vec.y = shadowSprite.getY();
        return vec;
    }

    @Override
    public void setLocation(Vector2 vecSet) {
        shadowSprite.setPosition(vecSet.x, vecSet.y);
        //sprite.setPosition(vecSet.x, vecSet.y);
    }

    @Override
    public void move(boolean leftMove, boolean rightMove, boolean upMove, boolean downMove){
        if (!currentlyMoving) {
            this.leftMove = leftMove;
            this.rightMove = rightMove;
            this.upMove = upMove;
            this.downMove = downMove;
        }
    }

    @Override
    public void update(){
        // test code
        System.out.println("Player position: " + getLocation());

        if (!leftMove && !rightMove && !upMove && !downMove){
            currentlyMoving = false;
            originalLoc.x = getLocation().x;
            originalLoc.y = getLocation().y;
        } else {
            currentlyMoving = true;
        }

        if (leftMove){
            // Add logic to move one tile to left with smooth transition - the playerCam follows this
            shadowSprite.translate(-(Constants.ENTITY_SPEED) * Gdx.graphics.getDeltaTime(), 0.0f);
            if ((originalLoc.x - getLocation().x) >= 1){
                originalLoc.x -= 1;
                // Snap into position
                setLocation(originalLoc);
                leftMove = false;
            }
        }
        if (rightMove){
           // Add logic to move one tile to right with smooth transition - the playerCam follows this
            shadowSprite.translate(Constants.ENTITY_SPEED * Gdx.graphics.getDeltaTime(), 0.0f);
            if ((getLocation().x - originalLoc.x) >= 1){
                originalLoc.x += 1;
                // Snap into position
                setLocation(originalLoc);
                rightMove = false;
            }
        }
        if (upMove){
            // Add logic to move one tile to right with smooth transition - the playerCam follows this
            shadowSprite.translate(0.0f, Constants.ENTITY_SPEED * Gdx.graphics.getDeltaTime());
            if ((getLocation().y - originalLoc.y) >= 1){
                originalLoc.y += 1;
                // Snap into position
                setLocation(originalLoc);
                upMove = false;
            }
        }
        if (downMove){
            // Add logic to move one tile to right with smooth transition - the playerCam follows this
            shadowSprite.translate(0.0f, -(Constants.ENTITY_SPEED) * Gdx.graphics.getDeltaTime());
            if ((originalLoc.y - getLocation().y) >= 1){
                originalLoc.y -= 1;
                // Snap into position
                setLocation(originalLoc);
                downMove = false;
            }
        }
    }
}

package com.paranoidalien.game.dules.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.paranoidalien.game.dules.Screens.GameScreen;
import com.paranoidalien.game.dules.Utils.CollisionCheck;
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
    private boolean currentlyMoving, collision;
    private CollisionCheck collisionCheck;

    public Player(SpriteBatch batch, CollisionCheck collisionCheck){
        this.batch = batch;
        this.collisionCheck = collisionCheck;

        texture = new Texture(Gdx.files.internal("green.png"));
        sprite = new Sprite(texture);

        // used not for display but to represent position of sprite
        shadowSprite = new Sprite();

        // One world unit high and wide
        sprite.setSize(1, 1.5f);

        // Initialze vector variables
        originalLoc = new Vector2();
        vec = new Vector2();

        //Initialize move booleans to false
        leftMove = rightMove = upMove = downMove = false;

        sprite.setPosition(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2 +
            Constants.ENTITY_TILE_OFFSET);
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
            // Perform collision checks
            if (leftMove) {
                if (collisionCheck.willCollide(this, "left")){
                    this.leftMove = false;
                    collision = true;
                } else {
                    this.leftMove = true;
                    // Had to add the following to prevent corner movement
                    this.rightMove = false;
                    this.upMove = false;
                    this.downMove = false;
                }
            }
            if (rightMove) {
                if (collisionCheck.willCollide(this, "right")){
                    this.rightMove = false;
                    collision = true;
                } else {
                    this.rightMove = true;
                    // Had to add the following to prevent corner movement
                    this.leftMove = false;
                    this.upMove = false;
                    this.downMove = false;
                }
            }
            if (upMove) {
                if (collisionCheck.willCollide(this, "up")){
                    this.upMove = false;
                    collision = true;
                } else {
                    this.upMove = true;
                    // Had to add the following to prevent corner movement
                    this.downMove = false;
                    this.rightMove = false;
                    this.leftMove = false;
                }
            }
            if (downMove) {
                if (collisionCheck.willCollide(this, "down")){
                    this.downMove = false;
                    collision = true;
                } else {
                    this.downMove = true;
                    // Had to add the following to prevent corner movement
                    this.upMove = false;
                    this.rightMove = false;
                    this.leftMove = false;
                }
            }
        }
    }

    @Override
    public void update(){
        if (!leftMove && !rightMove && !upMove && !downMove){
            currentlyMoving = false;
            originalLoc.x = getLocation().x;
            originalLoc.y = getLocation().y;
        } else {
           currentlyMoving = true;
        }

        if (leftMove){
                // Add logic to move one tile to right with smooth transition - the playerCam follows this
                shadowSprite.translate(-(Constants.ENTITY_SPEED) * Gdx.graphics.getDeltaTime(), 0.0f);
                if ((originalLoc.x - getLocation().x) >= 1) {
                    originalLoc.x -= 1;
                    // Snap into position
                    setLocation(originalLoc);
                    leftMove = false;
                }

        }
        if (rightMove){
                // Add logic to move one tile to right with smooth transition - the playerCam follows this
                shadowSprite.translate(Constants.ENTITY_SPEED * Gdx.graphics.getDeltaTime(), 0.0f);
                if ((getLocation().x - originalLoc.x) >= 1) {
                    originalLoc.x += 1;
                    // Snap into position
                    setLocation(originalLoc);
                    rightMove = false;
                }

        }
        if (upMove){
                // Add logic to move one tile to right with smooth transition - the playerCam follows this
                shadowSprite.translate(0.0f, Constants.ENTITY_SPEED * Gdx.graphics.getDeltaTime());
                if ((getLocation().y - originalLoc.y) >= 1) {
                    originalLoc.y += 1;
                    // Snap into position
                    setLocation(originalLoc);
                    upMove = false;
                }

        }
        if (downMove){
                // Add logic to move one tile to right with smooth transition - the playerCam follows this
                shadowSprite.translate(0.0f, -(Constants.ENTITY_SPEED) * Gdx.graphics.getDeltaTime());
                if ((originalLoc.y - getLocation().y) >= 1) {
                    originalLoc.y -= 1;
                    // Snap into position
                    setLocation(originalLoc);
                    downMove = false;
                }

        }
    }
}

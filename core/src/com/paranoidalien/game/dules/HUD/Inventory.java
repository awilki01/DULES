package com.paranoidalien.game.dules.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Project: DULES
 * Created by Adam on 13 December 2014 at 9:30 AM.
 */
public class Inventory {
    private SpriteBatch batch;
    private boolean isVisible = false;
    private Texture texture;
    private Sprite sprite;
    private Vector2 vec, mousePos;
    private Rectangle bindingBox;
    private boolean beingMoved = false, mouseClickInWindow;
    float windowStartMoveX, windowStartMoveY;


    public Inventory (SpriteBatch batch) {
        this.batch = batch;

        texture = new Texture(Gdx.files.internal("48tileGrid.png"));
        sprite = new Sprite(texture);

        vec = new Vector2(850, 500);
        mousePos = new Vector2();

        // Set sprite size in HUD coord system
        //sprite.setSize(300, 150);

        //Set initial location
        this.setPosition(vec);

        bindingBox = new Rectangle(sprite.getBoundingRectangle());
    }

    public Vector2 getLocation() {
        vec.x = sprite.getX();
        vec.y = sprite.getY();
        return vec;
    }

    public void setPosition(Vector2 vec) {
        sprite.setPosition(vec.x, vec.y);
    }

    public void draw(){
        if (isVisible) {
            sprite.draw(batch);
        }
    }

    public void update() {
        bindingBox = sprite.getBoundingRectangle();
        if (beingMoved && isVisible){
            vec.x = mousePos.x - windowStartMoveX;
            vec.y = mousePos.y - windowStartMoveY;
            setPosition(vec);
        }
    }

    public void mouseClick(Vector2 mouseClickPos) {
        // Used to give information for proper window movement when being
        // dragged by the mouse. Without this, the window/sprite would put
        // it's lower left corner on the mouse position.
        windowStartMoveX = mouseClickPos.x - sprite.getBoundingRectangle().x;
        windowStartMoveY = mouseClickPos.y - sprite.getBoundingRectangle().y;

        // Return boolean if mouse was clicked in window
        if (bindingBox.contains(mouseClickPos.x, mouseClickPos.y)){
            mouseClickInWindow = true;
        } else mouseClickInWindow = false;
    }

    public void mouseDrag(Vector2 mousePos){
        this.mousePos = mousePos;
        // I am not setting the window position here because I found
        // when clicking and dragging outside of box and the mouse was
        // dragged over window while mouse button was pressed, the window
        // was being dragged.  This method will only be called when the
        // mouse button is pressed and the mouse dragged anyway.
        if (mouseClickInWindow){
            beingMoved = true;
        } else beingMoved = false;
    }

    public void mouseUp(){
        beingMoved = false;
    }

    public void toggle() {
        isVisible = !isVisible;
    }

}

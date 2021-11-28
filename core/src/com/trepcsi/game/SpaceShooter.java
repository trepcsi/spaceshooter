package com.trepcsi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trepcsi.game.screens.PlayScreen;

public class SpaceShooter extends Game {
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 600;
    public static final float PPM = 100f; //pixels per meter

    public SpriteBatch batch;  //only 1 batch allowed in the whole game

    public static final short PLAYER_BIT = 1;
    public static final short BULLET_BIT = 2;
    public static final short METEOR_BIT = 4;

    @Override
    public void create() {
        batch = new SpriteBatch();

        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}

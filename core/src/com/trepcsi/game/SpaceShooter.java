package com.trepcsi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trepcsi.game.screens.PlayScreen;

public class SpaceShooter extends Game {

    public SpriteBatch batch;  //only 1 batch allowed in the whole game

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

package main.java.com.trepcsi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.java.com.trepcsi.game.screens.PlayScreen;


public class SpaceShooter extends Game {
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 600;
    public static final float PPM = 100f; //pixels per meter

    public static final float BIG_METEOR_R = 60 / PPM;
    public static final String BIG_METEOR_TEXTURE_PATH = "meteorBrown_big4.png";
    public static final float SMALL_METEOR_R = 30 / PPM;
    public static final String SMALL_METEOR_TEXTURE_PATH = "meteorBrown_med3.png";

    public static final short PLAYER_BIT = 1;
    public static final short BULLET_BIT = 2;
    public static final short METEOR_BIT = 4;
    public static final short WALL_BIT = 8;

    public SpriteBatch batch;  //only 1 batch allowed in the whole game
    public AssetManager manager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("sounds/sfx_laser2.ogg", Sound.class);
        manager.finishLoading();
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
        manager.dispose();
    }
}

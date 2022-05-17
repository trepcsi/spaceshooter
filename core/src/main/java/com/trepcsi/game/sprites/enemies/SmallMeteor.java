package main.java.com.trepcsi.game.sprites.enemies;

import com.badlogic.gdx.math.Vector2;
import main.java.com.trepcsi.game.SpaceShooter;
import main.java.com.trepcsi.game.screens.PlayScreen;


public class SmallMeteor extends Meteor {
    public SmallMeteor(PlayScreen screen, Vector2 position, Vector2 velocity) {
        super(screen, position, velocity, SpaceShooter.SMALL_METEOR_R, SpaceShooter.SMALL_METEOR_TEXTURE_PATH);
    }

    @Override
    public void onBulletHit() {
        setToDestroy = true;
    }
}

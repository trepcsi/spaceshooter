package com.trepcsi.game.sprites.enemies;

import com.badlogic.gdx.math.Vector2;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

public class BigMeteor extends Meteor {
    public BigMeteor(PlayScreen screen, Vector2 position, Vector2 velocity) {
        super(screen, position, velocity, SpaceShooter.BIG_METEOR_R, SpaceShooter.BIG_METEOR_TEXTURE_PATH);
    }

    @Override
    public void onBulletHit() {
        screen.addExplosion(new Vector2(getX() + getWidth() / 2, getY() + getWidth() / 2));
        setToDestroy = true;
    }
}

package com.trepcsi.game.sprites.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

public class SmallMeteor extends Meteor {
    public SmallMeteor(PlayScreen screen, Vector2 position, Vector2 velocity) {
        super(screen, position, velocity, SpaceShooter.SMALL_METEOR_R, SpaceShooter.SMALL_METEOR_TEXTURE_PATH);
    }

    @Override
    public void onBulletHit() {
        setToDestroy = true;
    }
}

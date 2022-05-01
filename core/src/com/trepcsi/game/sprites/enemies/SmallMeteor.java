package com.trepcsi.game.sprites.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

public class SmallMeteor extends Meteor{
    public SmallMeteor(PlayScreen screen, Vector2 position, Vector2 velocity) {
        super(screen, position, velocity, "meteorBrown_med3.png");
        setBounds(getX(), getY(), 60 / SpaceShooter.PPM, 60 / SpaceShooter.PPM);
    }

    @Override
    public void defineMeteor() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;

        body = screen.getWorld().createBody(bdef);
        MassData massData = new MassData();
        massData.mass = 100000.f;
        body.setMassData(massData);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30 / SpaceShooter.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = SpaceShooter.METEOR_BIT;
        fdef.filter.maskBits = SpaceShooter.PLAYER_BIT | SpaceShooter.BULLET_BIT | SpaceShooter.WALL_BIT;
        body.createFixture(fdef).setUserData(this);

        body.setLinearVelocity(velocity);
    }

    @Override
    public void onBulletHit() {
        setToDestroy = true;
    }
}

package com.trepcsi.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

public class Meteor extends Sprite {

    private World world;
    private Body body;


    public Meteor(PlayScreen screen) {
        this.world = screen.getWorld();

        defineMeteor();
    }

    private void defineMeteor() {

        BodyDef bdef = new BodyDef();
        bdef.position.set((float) (SpaceShooter.V_WIDTH - 100) / SpaceShooter.PPM, (float) (SpaceShooter.V_HEIGHT - 100) / SpaceShooter.PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(60 / SpaceShooter.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = SpaceShooter.METEOR_BIT;
        fdef.filter.maskBits = SpaceShooter.PLAYER_BIT | SpaceShooter.BULLET_BIT;
        body.createFixture(fdef).setUserData(this);

        body.setLinearVelocity(-0.1f, 0);
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}

package com.trepcsi.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

public class Meteor extends Sprite {

    private World world;
    private Body body;

    private Vector2 position;
    private Vector2 velocity;

    public Meteor(PlayScreen screen, Vector2 position, Vector2 velocity) {
        this.world = screen.getWorld();
        this.position = position;
        this.velocity = velocity;
        defineMeteor();
    }

    private void defineMeteor() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(60 / SpaceShooter.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = SpaceShooter.METEOR_BIT;
        fdef.filter.maskBits = SpaceShooter.PLAYER_BIT | SpaceShooter.BULLET_BIT;
        body.createFixture(fdef).setUserData(this);

        body.setLinearVelocity(velocity);
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}

package com.trepcsi.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

public class SpaceShip extends Sprite {

    private SpaceShooter game;

    //Box2d variables
    private World world;
    private Body body;

    public SpaceShip(PlayScreen screen) {
        this.world = screen.getWorld();
        defineSpaceShip();
    }

    private void defineSpaceShip() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(200 / SpaceShooter.PPM, 500 / SpaceShooter.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(40 / SpaceShooter.PPM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}

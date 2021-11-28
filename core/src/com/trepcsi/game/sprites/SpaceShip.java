package com.trepcsi.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

import static java.lang.Math.sqrt;

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
        bdef.position.set((float) SpaceShooter.V_WIDTH / 2 / SpaceShooter.PPM, (float) SpaceShooter.V_HEIGHT / 5 / SpaceShooter.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
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

    public void moveForward() {
        float alpha = body.getAngle();
        float velX = MathUtils.cos(alpha) * 0.8f;
        float velY = MathUtils.sin(alpha) * 0.8f;
        Vector2 linearVelocity = body.getLinearVelocity();
        if (sqrt(linearVelocity.x * linearVelocity.x + linearVelocity.y * linearVelocity.y) <= 1.2f) {
            body.applyLinearImpulse(new Vector2(velX, velY), body.getWorldCenter(), true);
        }
    }

    public void turn(boolean toLeft) {
        float alpha = body.getAngle();

        if (toLeft) {
            body.setTransform(body.getWorldCenter(), alpha + (2 * MathUtils.degreesToRadians));
        } else {
            body.setTransform(body.getWorldCenter(), alpha + (-2 * MathUtils.degreesToRadians));
        }
        if (body.getLinearVelocity().isZero()) {
            return;
        }
        body.setLinearVelocity(new Vector2(0, 0));
        moveForward();
    }
}

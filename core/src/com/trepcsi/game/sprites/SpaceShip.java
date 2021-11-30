package com.trepcsi.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

import static java.lang.Math.sqrt;

public class SpaceShip extends Sprite {

    private final float POSITION_X = (float) SpaceShooter.V_WIDTH / 2 / SpaceShooter.PPM;
    private final float POSITION_Y = (float) SpaceShooter.V_HEIGHT / 5 / SpaceShooter.PPM;
    private final float R = 40 / SpaceShooter.PPM;

    private PlayScreen screen;

    //Box2d variables
    private World world;
    private Body body;

    public SpaceShip(PlayScreen screen) {
        this.world = screen.getWorld();
        this.screen = screen;
        defineSpaceShip();
    }

    private void defineSpaceShip() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(POSITION_X, POSITION_Y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(R);
        fdef.shape = shape;
        fdef.filter.categoryBits = SpaceShooter.PLAYER_BIT;
        fdef.filter.maskBits = SpaceShooter.METEOR_BIT;
        body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public void moveForward() {
        float velocity_size = 0.8f;
        float max_velocity_size = 1.2f;

        float alpha = body.getAngle();
        float velX = MathUtils.cos(alpha) * velocity_size;
        float velY = MathUtils.sin(alpha) * velocity_size;
        Vector2 linearVelocity = body.getLinearVelocity();
        if (sqrt(linearVelocity.x * linearVelocity.x + linearVelocity.y * linearVelocity.y) <= max_velocity_size) {
            body.applyLinearImpulse(new Vector2(velX, velY), body.getWorldCenter(), true);
        }
    }

    public void turn(boolean toLeft) {
        int turnAngleDeg = 2;

        float alpha = body.getAngle();
        if (toLeft) {
            body.setTransform(body.getWorldCenter(), alpha + (turnAngleDeg * MathUtils.degreesToRadians));
        } else {
            body.setTransform(body.getWorldCenter(), alpha + (-turnAngleDeg * MathUtils.degreesToRadians));
        }
        if (body.getLinearVelocity().isZero()) {
            return;
        }
        body.setLinearVelocity(new Vector2(0, 0));
        moveForward();
    }

    public void shoot() {
        float alpha = body.getAngle();
        float velX = MathUtils.cos(alpha);
        float velY = MathUtils.sin(alpha);
        Vector2 dir = new Vector2(velX, velY);

        new Bullet(screen, body.getPosition(), dir, true);
        new Bullet(screen, body.getPosition(), dir, false);
    }

    public void colide() {
        Gdx.app.log("player", "collision");
    }
}

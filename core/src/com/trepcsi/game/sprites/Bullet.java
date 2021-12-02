package com.trepcsi.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;

public class Bullet extends Sprite {

    private World world;
    private Body body;

    public Bullet(PlayScreen screen, Vector2 position, Vector2 velocity, boolean isLeft) {
        this.world = screen.getWorld();
        defineBullet(position, velocity, isLeft);
    }

    private void defineBullet(Vector2 position, Vector2 velocity, boolean isLeft) {
        float velocity_size = 8.f;

        BodyDef bdef = new BodyDef();

        float r = 40 / SpaceShooter.PPM;
        float alpha = velocity.angleRad();
        if (isLeft) {
            bdef.position.set(position.x + r * (cos(alpha) + sin(alpha)), position.y + r * (sin(alpha) - cos(alpha)));
        } else {
            bdef.position.set(position.x + r * (cos(alpha) - sin(alpha)), position.y + r * (sin(alpha) + cos(alpha)));
        }

        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / SpaceShooter.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = SpaceShooter.BULLET_BIT;
        fdef.filter.maskBits = SpaceShooter.METEOR_BIT;
        body.createFixture(fdef).setUserData(this);
        body.setLinearVelocity(new Vector2(velocity.x * velocity_size, velocity.y * velocity_size));
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public void colide() {
        Gdx.app.log("bullet", "collision");
    }
}

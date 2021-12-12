package com.trepcsi.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;

public class Bullet extends Sprite {

    private PlayScreen screen;
    private Body body;

    private boolean setToDestroy;
    private boolean destroyed;

    public Bullet(PlayScreen screen, Vector2 position, Vector2 velocity, boolean isLeft) {
        super(new Texture("laserBlue02.png"));
        setBounds(getX(), getY(), 6 / SpaceShooter.PPM, 6 / SpaceShooter.PPM);
        this.screen = screen;
        this.destroyed = false;
        this.setToDestroy = false;
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
        body = screen.getWorld().createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / SpaceShooter.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = SpaceShooter.BULLET_BIT;
        fdef.filter.maskBits = SpaceShooter.METEOR_BIT | SpaceShooter.WALL_BIT;
        body.createFixture(fdef).setUserData(this);
        body.setLinearVelocity(new Vector2(velocity.x * velocity_size, velocity.y * velocity_size));
    }

    public void update(float dt) {
        if (setToDestroy && !destroyed) {
            screen.getWorld().destroyBody(body);
            destroyed = true;
        } else if (!destroyed) {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
    }

    @Override
    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);
        }
    }

    public void onMeteorHit() {
        setToDestroy = true;
    }

    public void onWallHit() {
        setToDestroy = true;
    }
}

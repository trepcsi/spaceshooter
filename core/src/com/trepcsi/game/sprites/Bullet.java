package com.trepcsi.game.sprites;

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

    public Bullet(PlayScreen screen, Vector2 pos, Vector2 dir, boolean isLeft) {
        this.world = screen.getWorld();
        defineBullet(pos, dir, isLeft);
    }

    private void defineBullet(Vector2 pos, Vector2 dir, boolean isLeft) {
        BodyDef bdef = new BodyDef();

        float r = 40 / SpaceShooter.PPM;
        float alpha = dir.angleRad();
        if (isLeft) {
            bdef.position.set(pos.x + r * (cos(alpha) + sin(alpha)), pos.y + r * (sin(alpha) - cos(alpha)));
        } else {
            bdef.position.set(pos.x + r * (cos(alpha) - sin(alpha)), pos.y + r * (sin(alpha) + cos(alpha)));
        }

        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / SpaceShooter.PPM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
        body.setLinearVelocity(new Vector2(dir.x * 5.f, dir.y * 5.f));
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}

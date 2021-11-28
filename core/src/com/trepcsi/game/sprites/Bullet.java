package com.trepcsi.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

public class Bullet extends Sprite {

    private World world;
    private Body body;

    public Bullet(PlayScreen screen, Vector2 pos, Vector2 dir) {
        this.world = screen.getWorld();
        defineBullet(pos, dir);
    }

    private void defineBullet(Vector2 pos, Vector2 dir) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(pos.x, pos.y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / SpaceShooter.PPM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
        body.setLinearVelocity(dir);
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}

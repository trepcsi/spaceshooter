package com.trepcsi.game.sprites.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.trepcsi.game.screens.PlayScreen;
import com.trepcsi.game.sprites.walls.Wall;
import com.trepcsi.game.sprites.walls.WallType;

import static java.lang.Math.sqrt;

public abstract class Meteor extends Sprite {

    protected Body body;
    protected PlayScreen screen;

    protected Vector2 position;
    protected Vector2 velocity;

    protected boolean setToDestroy;
    protected boolean destroyed;

    protected Meteor(PlayScreen screen, Vector2 position, Vector2 velocity, String picturePath) {
        super(new Texture(picturePath));

        this.screen = screen;
        this.position = position;
        this.velocity = velocity;
        this.destroyed = false;
        this.setToDestroy = false;
        defineMeteor();
    }

    public abstract void defineMeteor();

    public void update(float dt) {
        if (setToDestroy && !destroyed) {
            screen.getWorld().destroyBody(body);
            destroyed = true;
        } else if (!destroyed) {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
    }

    public void onWallHit(Wall wall) {
        Vector2 oldVelocity = body.getLinearVelocity();

        float current_speed = (float) sqrt(oldVelocity.x * oldVelocity.x + oldVelocity.y * oldVelocity.y);
        float alpha = oldVelocity.angleRad();
        float velocity_x = MathUtils.cos(alpha) * current_speed;
        float velocity_y = MathUtils.sin(alpha) * current_speed;
        if (wall.getType() == WallType.LEFT || wall.getType() == WallType.RIGHT) {
            velocity_x = -velocity_x;
        } else {
            velocity_y = -velocity_y;
        }

        Vector2 newVelocity = new Vector2(velocity_x, velocity_y);
        body.setLinearVelocity(newVelocity);
    }

    @Override
    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);
        }
    }

    public abstract void onBulletHit();
}

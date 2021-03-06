package main.java.com.trepcsi.game.sprites.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import main.java.com.trepcsi.game.SpaceShooter;
import main.java.com.trepcsi.game.screens.PlayScreen;
import main.java.com.trepcsi.game.sprites.walls.Wall;
import main.java.com.trepcsi.game.sprites.walls.WallType;

import static java.lang.Math.sqrt;

public abstract class Meteor extends Sprite {

    protected PlayScreen screen;

    protected Body body;
    protected Vector2 position;
    protected Vector2 velocity;
    protected float radius;

    protected boolean setToDestroy;
    protected boolean destroyed;

    protected Meteor(PlayScreen screen, Vector2 position, Vector2 velocity, float radius, String texturePath) {
        super(new Texture(texturePath));
        this.screen = screen;
        this.position = position;
        this.velocity = velocity;
        this.destroyed = false;
        this.setToDestroy = false;
        this.radius = radius;
        defineMeteor();
        setBounds(getX(), getY(), radius * 2, radius * 2);
    }

    public abstract void onBulletHit();

    public void defineMeteor() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;

        body = screen.getWorld().createBody(bdef);
        MassData massData = new MassData();
        massData.mass = 100000.f;
        body.setMassData(massData);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fdef.shape = shape;
        fdef.filter.categoryBits = SpaceShooter.METEOR_BIT;
        fdef.filter.maskBits = SpaceShooter.PLAYER_BIT | SpaceShooter.BULLET_BIT | SpaceShooter.WALL_BIT;
        body.createFixture(fdef).setUserData(this);

        body.setLinearVelocity(velocity);
    }

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
}

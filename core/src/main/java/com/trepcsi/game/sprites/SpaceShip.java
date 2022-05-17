package main.java.com.trepcsi.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import main.java.com.trepcsi.game.SpaceShooter;
import main.java.com.trepcsi.game.screens.PlayScreen;

import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

public class SpaceShip extends Sprite {

    private final float POSITION_X = (float) SpaceShooter.V_WIDTH / 2 / SpaceShooter.PPM;
    private final float POSITION_Y = (float) SpaceShooter.V_HEIGHT / 5 / SpaceShooter.PPM;
    private final float R = 40 / SpaceShooter.PPM;

    private final PlayScreen screen;
    private final AssetManager manager;

    //Box2d variables
    private World world;
    private Body body;

    public SpaceShip(PlayScreen screen) {
        super(new Texture("playerShip1_blue.png"));
        setBounds(getX(), getY(), 80 / SpaceShooter.PPM, 80 / SpaceShooter.PPM);

        this.world = screen.getWorld();
        this.screen = screen;
        manager = screen.getAssetManager();
        defineSpaceShip();
    }

    private void defineSpaceShip() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(POSITION_X, POSITION_Y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setTransform(body.getWorldCenter(), (float) PI / 2);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(R);
        fdef.shape = shape;
        fdef.filter.categoryBits = SpaceShooter.PLAYER_BIT;
        fdef.filter.maskBits = SpaceShooter.METEOR_BIT | SpaceShooter.WALL_BIT;
        body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public void moveForward() {
        float velocity_size = 1.f;
        float max_velocity_size = 2.5f;

        float alpha = body.getAngle();
        float velX = MathUtils.cos(alpha) * velocity_size;
        float velY = MathUtils.sin(alpha) * velocity_size;
        Vector2 linearVelocity = body.getLinearVelocity();
        if (sqrt(linearVelocity.x * linearVelocity.x + linearVelocity.y * linearVelocity.y) <= max_velocity_size) {
            body.applyLinearImpulse(new Vector2(velX, velY), body.getWorldCenter(), true);
        }
    }

    public void moveForward(float velocity_size) {
        float max_velocity = 2.5f;

        float alpha = body.getAngle();
        float velocity_x = MathUtils.cos(alpha) * velocity_size;
        float velocity_y = MathUtils.sin(alpha) * velocity_size;

        Vector2 linearVelocity = body.getLinearVelocity();
        if (sqrt(linearVelocity.x * linearVelocity.x + linearVelocity.y * linearVelocity.y) <= max_velocity) {
            body.applyLinearImpulse(new Vector2(velocity_x, velocity_y), body.getWorldCenter(), true);
        }
    }

    public void turn(boolean toLeft) {
        int turnAngleDeg = 6;
        Vector2 linearVelocity = body.getLinearVelocity();
        var current_speed = sqrt(linearVelocity.x * linearVelocity.x + linearVelocity.y * linearVelocity.y);
        float alpha = body.getAngle();

        setOrigin(body.getWorldCenter().x / SpaceShooter.PPM + R, body.getWorldCenter().y / SpaceShooter.PPM + R);
        if (toLeft) {
            rotate(turnAngleDeg);
            body.setTransform(body.getWorldCenter(), alpha + (turnAngleDeg * MathUtils.degreesToRadians));
        } else {
            rotate(-turnAngleDeg);
            body.setTransform(body.getWorldCenter(), alpha + (-turnAngleDeg * MathUtils.degreesToRadians));
        }
        body.setLinearVelocity(new Vector2(0, 0));
        moveForward((float) current_speed * 0.9f);
    }

    public void shoot(List<Bullet> bulletList) {
        float alpha = body.getAngle();
        float velocity_x = MathUtils.cos(alpha);
        float velocity_y = MathUtils.sin(alpha);
        Vector2 velocity = new Vector2(velocity_x, velocity_y);

        bulletList.add(new Bullet(screen, this, body.getPosition(), velocity, true));
        bulletList.add(new Bullet(screen, this, body.getPosition(), velocity, false));
        manager.get("sounds/sfx_laser2.ogg", Sound.class).play(1.5f);
    }

    public void slowDown() {
        Vector2 linearVelocity = body.getLinearVelocity();
        var current_speed = sqrt(linearVelocity.x * linearVelocity.x + linearVelocity.y * linearVelocity.y);

        if (current_speed < 0.02) return;

        body.setLinearVelocity(new Vector2(0, 0));
        moveForward((float) current_speed * 0.93f);
    }

    public void colide() {
        Gdx.app.log("player", "collision");
    }

    public float getRadius() {
        return R;
    }
}

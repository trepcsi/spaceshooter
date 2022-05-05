package com.trepcsi.game.sprites.walls;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.screens.PlayScreen;

public class Wall extends Sprite {

    private final World world;
    private final WallType type;

    public Wall(PlayScreen screen, WallType type) {
        this.world = screen.getWorld();
        this.type = type;
        defineWall();
    }

    private void defineWall() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        switch (type) {
            case LEFT:
                bdef.position.set(new Vector2(0, (float) SpaceShooter.V_HEIGHT / 2 / SpaceShooter.PPM));
                shape.setAsBox(0.01f, (float) SpaceShooter.V_HEIGHT / 2 / SpaceShooter.PPM);
                break;
            case RIGHT:
                bdef.position.set(new Vector2((float) SpaceShooter.V_WIDTH / SpaceShooter.PPM, (float) SpaceShooter.V_HEIGHT / 2 / SpaceShooter.PPM));
                shape.setAsBox(0.01f, (float) SpaceShooter.V_HEIGHT / 2 / SpaceShooter.PPM);
                break;
            case TOP:
                bdef.position.set(new Vector2((float) SpaceShooter.V_WIDTH / 2 / SpaceShooter.PPM, (float) SpaceShooter.V_HEIGHT / SpaceShooter.PPM));
                shape.setAsBox((float) SpaceShooter.V_WIDTH / 2 / SpaceShooter.PPM, 0.01f);
                break;
            case BOTTOM:
                bdef.position.set(new Vector2((float) SpaceShooter.V_WIDTH / 2 / SpaceShooter.PPM, 0));
                shape.setAsBox((float) SpaceShooter.V_WIDTH / 2 / SpaceShooter.PPM, 0.01f);
                break;
        }
        Body body = world.createBody(bdef);
        fdef.shape = shape;
        fdef.filter.categoryBits = SpaceShooter.WALL_BIT;
        fdef.filter.maskBits = SpaceShooter.PLAYER_BIT | SpaceShooter.BULLET_BIT | SpaceShooter.METEOR_BIT;
        body.createFixture(fdef).setUserData(this);
    }

    public WallType getType() {
        return type;
    }
}

package com.trepcsi.game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.sprites.Bullet;
import com.trepcsi.game.sprites.Meteor;
import com.trepcsi.game.sprites.SpaceShip;
import com.trepcsi.game.sprites.walls.Wall;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getFilterData().categoryBits == SpaceShooter.METEOR_BIT) {
            if (fixB.getFilterData().categoryBits == SpaceShooter.BULLET_BIT) {
                ((Bullet) fixB.getUserData()).colide();
            }
            if (fixB.getFilterData().categoryBits == SpaceShooter.PLAYER_BIT) {
                ((SpaceShip) fixB.getUserData()).colide();
            }
        } else if (fixB.getFilterData().categoryBits == SpaceShooter.METEOR_BIT) {
            if (fixA.getFilterData().categoryBits == SpaceShooter.BULLET_BIT) {
                ((Bullet) fixA.getUserData()).colide();
            }
            if (fixA.getFilterData().categoryBits == SpaceShooter.PLAYER_BIT) {
                ((SpaceShip) fixA.getUserData()).colide();
            }
        }

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if (cDef == (SpaceShooter.METEOR_BIT | SpaceShooter.WALL_BIT)) {
            if (fixA.getFilterData().categoryBits == SpaceShooter.METEOR_BIT) {
                ((Meteor) fixA.getUserData()).onWallHit((Wall) fixB.getUserData());
            } else if (fixB.getFilterData().categoryBits == SpaceShooter.METEOR_BIT) {
                ((Meteor) fixB.getUserData()).onWallHit((Wall) fixA.getUserData());
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

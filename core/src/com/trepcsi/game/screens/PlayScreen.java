package com.trepcsi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.sprites.Meteor;
import com.trepcsi.game.sprites.SpaceShip;
import com.trepcsi.game.sprites.walls.Wall;
import com.trepcsi.game.sprites.walls.WallType;
import com.trepcsi.game.tools.WorldContactListener;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {

    private SpaceShooter game;

    private SpaceShip player;
    private List<Meteor> meteors;

    private OrthographicCamera camera;
    private Viewport viewport;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    public PlayScreen(SpaceShooter game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(SpaceShooter.V_WIDTH / SpaceShooter.PPM, SpaceShooter.V_HEIGHT / SpaceShooter.PPM, camera);  //need to scale after b2d
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener());
        box2DDebugRenderer = new Box2DDebugRenderer();

        Wall wallLeft = new Wall(this, WallType.LEFT);
        Wall wallRight = new Wall(this, WallType.RIGHT);
        Wall wallTop = new Wall(this, WallType.TOP);
        Wall wallBot = new Wall(this, WallType.BOTTOM);
        player = new SpaceShip(this);
        meteors = new ArrayList<>();
        generateMeteors();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor((float) 45 / 255, (float) 45 / 255, (float) 180 / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        box2DDebugRenderer.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.end();
    }

    private void update(float dt) {
        handleInput(dt);

        world.step(1 / 60f, 6, 2); //read more

        player.update(dt);
        for (Meteor m : meteors) {
            m.update(dt);
        }
    }

    private void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveForward();
        } else {
            player.slowDown();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.turn(true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.turn(false);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.shoot();
        }
    }

    public World getWorld() {
        return world;
    }

    private void generateMeteors() {
        meteors.add(new Meteor(this,
                new Vector2((SpaceShooter.V_WIDTH - 100) / SpaceShooter.PPM, (SpaceShooter.V_HEIGHT - 100) / SpaceShooter.PPM),
                new Vector2(-1.f, 0)));
        meteors.add(new Meteor(this,
                new Vector2(100 / SpaceShooter.PPM, 100 / SpaceShooter.PPM),
                new Vector2(.8f, .8f)));
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

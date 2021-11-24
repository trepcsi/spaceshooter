package com.trepcsi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.sprites.SpaceShip;

public class PlayScreen implements Screen {

    private SpaceShooter game;
    private SpaceShip player;

    private OrthographicCamera camera;
    private Viewport viewport;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    public PlayScreen(SpaceShooter game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(SpaceShooter.V_WIDTH / SpaceShooter.PPM, SpaceShooter.V_HEIGHT / SpaceShooter.PPM, camera);  //need to scale after b2d
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        box2DDebugRenderer = new Box2DDebugRenderer();
        player = new SpaceShip(this);
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
        world.step(1 / 60f, 6, 2); //read more
        player.update(dt);
    }

    public World getWorld() {
        return world;
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

package com.trepcsi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trepcsi.game.SpaceShooter;
import com.trepcsi.game.scenes.Hud;
import com.trepcsi.game.sprites.Bullet;
import com.trepcsi.game.sprites.SpaceShip;
import com.trepcsi.game.sprites.enemies.BigMeteor;
import com.trepcsi.game.sprites.enemies.Meteor;
import com.trepcsi.game.sprites.enemies.SmallMeteor;
import com.trepcsi.game.sprites.walls.Wall;
import com.trepcsi.game.sprites.walls.WallType;
import com.trepcsi.game.tools.WorldContactListener;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {

    private SpaceShooter game;

    private SpaceShip player;
    private List<Meteor> meteors;
    private List<Bullet> bullets;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Hud hud;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private Texture background;

    public PlayScreen(SpaceShooter game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(SpaceShooter.V_WIDTH / SpaceShooter.PPM, SpaceShooter.V_HEIGHT / SpaceShooter.PPM, camera);
        hud = Hud.getInstance(game.batch);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        background = new Texture("big_background.jpg");

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener());
        box2DDebugRenderer = new Box2DDebugRenderer();


        player = new SpaceShip(this);
        meteors = new ArrayList<>();
        bullets = new ArrayList<>();
        generateWalls();
        generateMeteors();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor((float) 45 / 255, (float) 45 / 255, (float) 180 / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, SpaceShooter.V_WIDTH / SpaceShooter.PPM, SpaceShooter.V_HEIGHT / SpaceShooter.PPM);
        player.draw(game.batch);
        for (Meteor meteor : meteors) {
            meteor.draw(game.batch);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(game.batch);
        }
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        box2DDebugRenderer.render(world, camera.combined);
    }

    private void update(float dt) {
        System.out.println(world.getBodyCount());
        handleInput(dt);

        world.step(1 / 60f, 6, 2); //read more

        player.update(dt);
        for (Meteor m : meteors) {
            m.update(dt);
        }
        for (Bullet b : bullets) {
            b.update(dt);
        }

        hud.update(dt);
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
            player.shoot(bullets);
        }
    }

    public World getWorld() {
        return world;
    }

    private void generateMeteors() {
        meteors.add(new BigMeteor(this,
                new Vector2((SpaceShooter.V_WIDTH - 100) / SpaceShooter.PPM, (SpaceShooter.V_HEIGHT - 100) / SpaceShooter.PPM),
                new Vector2(-1.f, 0)));
        meteors.add(new BigMeteor(this,
                new Vector2(100 / SpaceShooter.PPM, 100 / SpaceShooter.PPM),
                new Vector2(.7f, .7f)));
        meteors.add(new SmallMeteor(this,
                new Vector2(300 / SpaceShooter.PPM, 300 / SpaceShooter.PPM),
                new Vector2(.7f, -.7f)));
    }

    private void generateWalls() {
        new Wall(this, WallType.LEFT);
        new Wall(this, WallType.RIGHT);
        new Wall(this, WallType.TOP);
        new Wall(this, WallType.BOTTOM);
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
        box2DDebugRenderer.dispose();
        world.dispose();
        hud.dispose();
    }

    public AssetManager getAssetManager() {
        return this.game.manager;
    }
}

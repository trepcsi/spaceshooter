package com.trepcsi.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trepcsi.game.SpaceShooter;

public class Hud implements Disposable {

    private static Hud single_instance = null;    //singleton class

    public Stage stage;

    private Integer timer;
    private final Label timeLabel;
    private float timeCount;


    public static Hud getInstance(SpriteBatch sb) {
        if (single_instance == null) {
            single_instance = new Hud(sb);
        }
        return single_instance;
    }

    private Hud(SpriteBatch sb) {
        Viewport viewport = new FitViewport(SpaceShooter.V_WIDTH, SpaceShooter.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        timer = 0;
        timeLabel = new Label(String.format("%03d", timer), new Label.LabelStyle(new BitmapFont(), Color.CORAL));

        createTable();
    }

    private void createTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        timeLabel.setFontScale(2.5f);
        table.add(timeLabel).padTop(10).padRight(50);

        stage.addActor(table);
    }

    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            timer++;
            timeLabel.setText(String.format("%03d", timer));
            timeCount = 0;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

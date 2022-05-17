package main.java.com.trepcsi.game.helper;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDirectionHelper {

    public RandomDirectionHelper() {

    }

    public List<Vector2> generateRandomDirections(int directionCount) {
        List<Vector2> result = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < directionCount; i++) {
            float x = (float) random.nextDouble();
            float y = (float) Math.sqrt(1 - x * x);
            result.add(new Vector2(x, y));
        }
        return result;
    }
}


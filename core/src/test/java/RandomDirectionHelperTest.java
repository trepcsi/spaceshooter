import com.badlogic.gdx.math.Vector2;
import main.java.com.trepcsi.game.helper.RandomDirectionHelper;
import org.junit.jupiter.api.Test;

import static java.lang.Math.pow;
import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.*;


public class RandomDirectionHelperTest {
    private RandomDirectionHelper underTest;

    @Test
    public void shouldReturnCorrectAmountOfDirection() {
        underTest = new RandomDirectionHelper();
        assertEquals(1, underTest.generateRandomDirections(1).size());
    }

    @Test
    public void shouldReturnDirectionWithScaleOfOneUnit() {
        underTest = new RandomDirectionHelper();
        Vector2 direction = underTest.generateRandomDirections(1).get(0);
        assertEquals(1, round(pow(direction.x, 2) + pow(direction.y, 2)));
    }
}

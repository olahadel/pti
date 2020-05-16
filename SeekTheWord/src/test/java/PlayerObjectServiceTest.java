import model.Player;
import org.junit.Assert;
import org.junit.Test;

public class PlayerObjectServiceTest {

    @Test
    public void shouldCreateAndReturnPlayer() {
        Player testPlayer = new Player();
        String testName = "testName";
        testPlayer.setPlayerName(testName);
        int testGameTime = 111;
        testPlayer.setGameTime(testGameTime);
        int testScore = 11;
        testPlayer.setScore(testScore);

        Assert.assertTrue(testPlayer.getPlayerName().equals(testName));
        Assert.assertTrue(testPlayer.getGameTime() == testGameTime);
        Assert.assertTrue(testPlayer.getScore() == testScore);

        Player testPlayer2 = new Player(testName, testGameTime, testScore);

        Assert.assertTrue(testPlayer2.getPlayerName().equals(testName));
        Assert.assertTrue(testPlayer2.getGameTime() == testGameTime);
        Assert.assertTrue(testPlayer2.getScore() == testScore);
    }
}

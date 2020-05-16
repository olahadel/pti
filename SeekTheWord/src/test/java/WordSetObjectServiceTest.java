import model.WordSet;
import org.junit.Assert;
import org.junit.Test;

public class WordSetObjectServiceTest {

    @Test
    public void shouldCreateAndReturnWordSet() {
        String testLetterSet = "abcdefghi";
        String testPossibleWord = "abc";
        WordSet testWordSet = new WordSet(testLetterSet, testPossibleWord);

        Assert.assertTrue(testWordSet.getLetterSet().equals(testLetterSet));
        Assert.assertTrue(testWordSet.getPossibleWord().equals(testPossibleWord));
    }
}

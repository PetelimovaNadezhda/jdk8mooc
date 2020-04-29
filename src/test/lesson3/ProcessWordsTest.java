package lesson3;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static lesson3.Lesson3.processWords;

public class ProcessWordsTest {

    @Test
    public void processWordsTest() {
        List<String> list = Arrays.asList("abandonment", "RAISETHYSWORD", "raisethysword", "machineability");
        List<String> expectedList = processWords(list, true);
        List<String> actualList = Arrays.asList("RAISETHYSWORD", "MACHINEABILITY");
        Assert.assertEquals(expectedList, actualList, "Expected list is equals actual");
    }
}
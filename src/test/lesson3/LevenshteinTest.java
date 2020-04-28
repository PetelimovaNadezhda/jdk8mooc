package lesson3;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static lesson3.Lesson3.computeLevenshtein;

public class LevenshteinTest {

    @Test
    void testComputeLevenshtein() {
        List<String> list = Arrays.asList("rosettacode", "raisethysword", "raisethyswords");
        int[][] expectedDistances = {{0, 8, 8}, {8, 0, 1}, {8, 1, 0}};
        int[][] actualDistances = computeLevenshtein(list, true);
        Assert.assertTrue(Arrays.deepEquals(expectedDistances, actualDistances), "Levenshtein distance is not correct");
    }
}
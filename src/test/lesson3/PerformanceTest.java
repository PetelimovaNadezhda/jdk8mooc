package lesson3;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static lesson3.Lesson3.measureOneRun;

public class PerformanceTest {

    @Test
    void testComputeLevenshtein1() throws IOException {
        RandomWords fullWordList = new RandomWords();
        List<String> wordList = fullWordList.createList(1000);
        final int LIST_SIZE = wordList.size();
        int[][] distances = new int[LIST_SIZE][LIST_SIZE];
        long timeParallel = 0;

        for (int t = 0; t < 15; t++) {
            measureOneRun("Parallel", () -> {
                IntStream.range(0, LIST_SIZE)
                        .parallel()
                        .forEach(i -> IntStream.range(i, LIST_SIZE)
                                .parallel()
                                .forEach(j -> {
                                    distances[i][j] = Levenshtein.lev(wordList.get(i), wordList.get(j));
                                    distances[j][i] = distances[i][j];
                                }));
                return distances;
            });
        }

        for (int t = 0; t < 100; t++) {
            timeParallel += measureOneRun("Parallel", () -> {
                IntStream.range(0, LIST_SIZE)
                        .parallel()
                        .forEach(i -> IntStream.range(i, LIST_SIZE)
                                .parallel()
                                .forEach(j -> {
                                    distances[i][j] = Levenshtein.lev(wordList.get(i), wordList.get(j));
                                    distances[j][i] = distances[i][j];
                                }));
                return distances;
            });
        }
        System.out.println(timeParallel / 100);
    }

    @Test
    void testComputeLevenshtein2() throws IOException {
        RandomWords fullWordList = new RandomWords();
        List<String> wordList = fullWordList.createList(1000);
        final int LIST_SIZE = wordList.size();
        int[][] distances = new int[LIST_SIZE][LIST_SIZE];
        long timeParallel = 0;

        for (int t = 0; t < 15; t++) {
            measureOneRun("Parallel", () -> {
                IntStream.range(0, LIST_SIZE).parallel()
                        .mapToObj(int1 -> IntStream.range(int1, LIST_SIZE).mapToObj(int2 -> Arrays.asList(int1, int2)))
                        .flatMap(lists -> lists)
                        .forEach(lists -> {
                            int i = lists.get(0);
                            int j = lists.get(1);
                            distances[i][j] = Levenshtein.lev(wordList.get(i), wordList.get(j));
                            distances[j][i] = distances[i][j];
                        });
                return distances;
            });
        }

        for (int t = 0; t < 100; t++) {
            timeParallel += measureOneRun("Parallel", () -> {
                IntStream.range(0, LIST_SIZE).parallel()
                        .mapToObj(int1 -> IntStream.range(int1, LIST_SIZE).mapToObj(int2 -> Arrays.asList(int1, int2)))
                        .flatMap(lists -> lists)
                        .forEach(lists -> {
                            int i = lists.get(0);
                            int j = lists.get(1);
                            distances[i][j] = Levenshtein.lev(wordList.get(i), wordList.get(j));
                            distances[j][i] = distances[i][j];
                        });
                return distances;
            });
        }
        System.out.println(timeParallel / 100);
    }

    @Test
    void testComputeLevenshtein3() throws IOException {
        RandomWords fullWordList = new RandomWords();
        List<String> wordList = fullWordList.createList(1000);
        final int LIST_SIZE = wordList.size();
        long timeParallel = 0;

        for (int t = 0; t < 15; t++) {
            measureOneRun("Parallel", () -> IntStream.range(0, LIST_SIZE).parallel()
                    .mapToObj(int1 -> IntStream.range(0, LIST_SIZE).mapToObj(int2 -> new Pair(int1, int2)))
                    .flatMap(Function.identity())
                    .collect(() -> new int[LIST_SIZE][LIST_SIZE],
                            (intArray, pair) ->
                                    intArray[pair.getFirstIndex()][pair.getSecondIndex()] = Levenshtein.lev(wordList.get(pair.getFirstIndex()), wordList.get(pair.getSecondIndex())),
                            (a, b) -> {
                                for (int i = 0; i < a.length; i++) {
                                    for (int j = 0; j < b.length; j++) {
                                        a[i][j] = a[i][j] + b[i][j];
                                    }
                                }
                            }));
        }

        for (int t = 0; t < 100; t++) {
            timeParallel += measureOneRun("Parallel", () -> IntStream.range(0, LIST_SIZE).parallel()
                    .mapToObj(int1 -> IntStream.range(0, LIST_SIZE).mapToObj(int2 -> new Pair(int1, int2)))
                    .flatMap(Function.identity())
                    .collect(() -> new int[LIST_SIZE][LIST_SIZE],
                            (intArray, pair) ->
                                    intArray[pair.getFirstIndex()][pair.getSecondIndex()] = Levenshtein.lev(wordList.get(pair.getFirstIndex()), wordList.get(pair.getSecondIndex())),
                            (a, b) -> {
                                for (int i = 0; i < a.length; i++) {
                                    for (int j = 0; j < b.length; j++) {
                                        a[i][j] = a[i][j] + b[i][j];
                                    }
                                }
                            }));
        }
        System.out.println(timeParallel / 100);
    }
}

/**
 * Copyright © 2014, Oracle and/or its affiliates. All rights reserved.
 * <p>
 * JDK 8 MOOC Lesson 3 homework
 */
package lesson3;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Simon Ritter (@speakjava)
 * @author Stuart Marks
 */
public class Lesson3 {
    /* How many times to repeat the test.  5 seems to give reasonable results */
    private static final int RUN_COUNT = 5;

    /**
     * Used by the measure method to determine how long a Supplier takes to
     * return a result.
     *
     * @param <T>      The type of the result provided by the Supplier
     * @param label    Description of what's being measured
     * @param supplier The Supplier to measure execution time of
     * @return
     */
    static <T> long measureOneRun(String label, Supplier<T> supplier) {
        long startTime = System.nanoTime();
        T result = supplier.get();
        long endTime = System.nanoTime();
        long time = (endTime - startTime + 500_000L) / 1_000_000L;
        System.out.printf("%s took %dms%n",
                label, time);
        return time;
    }

    /**
     * Repeatedly generate results using a Supplier to eliminate some of the
     * issues of running a micro-benchmark.
     *
     * @param <T>      The type of result generated by the Supplier
     * @param label    Description of what's being measured
     * @param supplier The Supplier to measure execution time of
     * @return The last execution time of the Supplier code
     */
    static <T> long measure(String label, Supplier<T> supplier) {
        long result = 0;

        for (int i = 0; i < RUN_COUNT; i++)
            result = measureOneRun(label, supplier);

        return result;
    }

    /**
     * Computes the Levenshtein distance between every pair of words in the
     * subset, and returns a matrix of distances. This actually computes twice as
     * much as it needs to, since for every word a, b it should be the case that
     * lev(a,b) == lev(b,a) i.e., Levenshtein distance is commutative.
     *
     * @param wordList The subset of words whose distances to compute
     * @param parallel Whether to run in parallel
     * @return Matrix of Levenshtein distances
     */
    static int[][] computeLevenshtein(List<String> wordList, boolean parallel) {
        final int LIST_SIZE = wordList.size();
        int[][] distances = new int[LIST_SIZE][LIST_SIZE];
        if (parallel) {
            IntStream.range(0, LIST_SIZE)
                    .parallel()
                    .forEach(i -> distances[i] = computeDistancesForOneWord(i, wordList));
        } else {
            IntStream.range(0, LIST_SIZE)
                    .sequential()
                    .forEach(i -> distances[i] = computeDistancesForOneWord(i, wordList));
        }
        return distances;
    }

    private static int[] computeDistancesForOneWord(int i, List<String> wordList) {
        final int LIST_SIZE = wordList.size();
        int[] distances = new int[LIST_SIZE];
        for (int j = 0; j < LIST_SIZE; j++) {
            distances[j] = Levenshtein.lev(wordList.get(i), wordList.get(j));
        }
        return distances;
    }

    /**
     * Process a list of random strings and return a modified list
     *
     * @param wordList The subset of words whose distances to compute
     * @param parallel Whether to run in parallel
     * @return The list processed in whatever way you want
     */
    static List<String> processWords(List<String> wordList, boolean parallel) {
        if (parallel) {
            return processWordsForStream(wordList.parallelStream());
        } else {
            return processWordsForStream(wordList.stream());

        }
    }

    private static List<String> processWordsForStream(Stream<String> stream) {
        return stream.sorted()
                .filter(word -> word.startsWith("a"))
                .map(String::toUpperCase)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Main entry point for application
     *
     * @param args the command line arguments
     * @throws IOException If word file cannot be read
     */
    public static void main(String[] args) throws IOException {
        RandomWords fullWordList = new RandomWords();
        List<String> wordList = fullWordList.createList(1000);

        measure("Sequential", () -> computeLevenshtein(wordList, false));
        measure("Parallel", () -> computeLevenshtein(wordList, true));

//        measure("Sequential", () -> processWords(wordList, false));
//        measure("Parallel", () -> processWords(wordList, true));
    }
}
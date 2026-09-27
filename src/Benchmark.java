import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            100, 1_000, 10_000, 100_000
    };

    private static final int RUNS = 5;
    private static final int RANDOM_ACCESS_OPERATIONS = 10_000;
    private static final int SEARCH_OPERATIONS = 1_000;
    private static final int MODIFY_OPERATIONS = 1_000;

    private static final long SEED = 42;

    public static void main(String[] args) throws IOException {

        new File("results/tables").mkdirs();

        System.out.println("Starting benchmarks...");

        workload1RandomAccess();
        workload2Search();
        workload3InsertionRemoval();
        workload4Heap();

        System.out.println("All benchmarks finished.");
        System.out.println("Results saved in results/tables/");
    }

    private static void workload1RandomAccess() throws IOException {

        System.out.println("\nWorkload 1: Random Access");

        PrintWriter writer = new PrintWriter(new FileWriter(
                "results/tables/random_access.csv"));

        writer.println(
                "n,structure,averageTimeNs,averageAccesses,complexity");

        for (int n : SIZES) {

            Random random = new Random(SEED);

            int[] data = generateData(n, random);
            int[] indices = generateIndices(n, RANDOM_ACCESS_OPERATIONS, random);

            long totalTime = 0;
            long totalAccesses = 0;

            for (int run = 0; run < RUNS; run++) {

                DynamicArray array = buildDynamicArray(data);
                array.resetCounters();

                long start = System.nanoTime();

                for (int index : indices) {
                    array.get(index);
                }

                long end = System.nanoTime();

                totalTime += end - start;
                totalAccesses += array.getAccessCount();
            }

            writer.println(
                    n + ",DynamicArray,"
                            + (totalTime / RUNS) + ","
                            + (totalAccesses / RUNS) + ",Theta(1)");

            totalTime = 0;
            totalAccesses = 0;

            for (int run = 0; run < RUNS; run++) {

                LinkedList list = buildLinkedList(data);
                list.resetCounters();

                long start = System.nanoTime();

                for (int index : indices) {
                    list.get(index);
                }

                long end = System.nanoTime();

                totalTime += end - start;
                totalAccesses += list.getAccessCount();
            }

            writer.println(
                    n + ",LinkedList,"
                            + (totalTime / RUNS) + ","
                            + (totalAccesses / RUNS) + ",Theta(n)");

            System.out.println("n = " + n + " finished");
        }

        writer.close();
    }

    private static void workload2Search() throws IOException {

        System.out.println("\nWorkload 2: Search");

        PrintWriter writer = new PrintWriter(new FileWriter(
                "results/tables/search.csv"));

        writer.println(
                "n,structure,averageTimeNs,averageComparisons,complexity");

        for (int n : SIZES) {

            Random random = new Random(SEED);

            int[] data = generateData(n, random);

            int[] searchValues = new int[SEARCH_OPERATIONS];

            for (int i = 0; i < SEARCH_OPERATIONS; i++) {
                searchValues[i] = random.nextInt(n * 2);
            }

            long totalTime = 0;
            long totalComparisons = 0;

            for (int run = 0; run < RUNS; run++) {

                DynamicArray array = buildDynamicArray(data);
                array.resetCounters();

                long start = System.nanoTime();

                for (int value : searchValues) {
                    array.contains(value);
                }

                long end = System.nanoTime();

                totalTime += end - start;
                totalComparisons += array.getComparisonCount();
            }

            writer.println(
                    n + ",DynamicArray,"
                            + (totalTime / RUNS) + ","
                            + (totalComparisons / RUNS) + ",Theta(n)");

            totalTime = 0;
            totalComparisons = 0;

            for (int run = 0; run < RUNS; run++) {

                LinkedList list = buildLinkedList(data);
                list.resetCounters();

                long start = System.nanoTime();

                for (int value : searchValues) {
                    list.contains(value);
                }

                long end = System.nanoTime();

                totalTime += end - start;
                totalComparisons += list.getComparisonCount();
            }

            writer.println(
                    n + ",LinkedList,"
                            + (totalTime / RUNS) + ","
                            + (totalComparisons / RUNS) + ",Theta(n)");

            System.out.println("n = " + n + " finished");
        }

        writer.close();
    }

    private static void workload3InsertionRemoval()
            throws IOException {

        System.out.println("\nWorkload 3: Insertion / Removal");

        PrintWriter writer = new PrintWriter(new FileWriter(
                "results/tables/insertion_removal.csv"));

        writer.println(
                "n,structure,operation,position,averageTimeNs,averageMetric");

        for (int n : SIZES) {

            Random random = new Random(SEED);
            int[] data = generateData(n, random);

            benchmarkDynamicArrayInsertion(
                    writer, n, data, 0, "beginning");

            benchmarkDynamicArrayInsertion(
                    writer, n, data, n / 2, "middle");

            benchmarkLinkedListInsertion(
                    writer, n, data, 0, "beginning");

            benchmarkLinkedListInsertion(
                    writer, n, data, n / 2, "middle");

            benchmarkDynamicArrayRemoval(
                    writer, n, data, 0, "beginning");

            benchmarkDynamicArrayRemoval(
                    writer, n, data, n / 2, "middle");

            benchmarkLinkedListRemoval(
                    writer, n, data, 0, "beginning");

            benchmarkLinkedListRemoval(
                    writer, n, data, n / 2, "middle");

            System.out.println("n = " + n + " finished");
        }

        writer.close();
    }

    private static void benchmarkDynamicArrayInsertion(
            PrintWriter writer,
            int n,
            int[] data,
            int index,
            String position) {

        long totalTime = 0;
        long totalMovements = 0;

        for (int run = 0; run < RUNS; run++) {

            DynamicArray array = buildDynamicArray(data);
            array.resetCounters();

            long start = System.nanoTime();

            for (int i = 0; i < MODIFY_OPERATIONS; i++) {
                array.add(index, i);
            }

            long end = System.nanoTime();

            totalTime += end - start;
            totalMovements += array.getMovementCount();
        }

        writer.println(
                n + ",DynamicArray,insertion,"
                        + position + ","
                        + (totalTime / RUNS) + ","
                        + (totalMovements / RUNS));
    }

    private static void benchmarkLinkedListInsertion(
            PrintWriter writer,
            int n,
            int[] data,
            int index,
            String position) {

        long totalTime = 0;
        long totalAccesses = 0;

        for (int run = 0; run < RUNS; run++) {

            LinkedList list = buildLinkedList(data);
            list.resetCounters();

            long start = System.nanoTime();

            for (int i = 0; i < MODIFY_OPERATIONS; i++) {
                list.add(index, i);
            }

            long end = System.nanoTime();

            totalTime += end - start;
            totalAccesses += list.getAccessCount();
        }

        writer.println(
                n + ",LinkedList,insertion,"
                        + position + ","
                        + (totalTime / RUNS) + ","
                        + (totalAccesses / RUNS));
    }

    private static void benchmarkDynamicArrayRemoval(
            PrintWriter writer,
            int n,
            int[] data,
            int index,
            String position) {

        long totalTime = 0;
        long totalMovements = 0;

        for (int run = 0; run < RUNS; run++) {

            int completed = 0;

            while (completed < MODIFY_OPERATIONS) {

                DynamicArray array = buildDynamicArray(data);
                array.resetCounters();

                int possible = Math.max(1, n - index);

                int operations = Math.min(
                        possible,
                        MODIFY_OPERATIONS - completed);

                long start = System.nanoTime();

                for (int i = 0; i < operations; i++) {
                    array.remove(index);
                }

                long end = System.nanoTime();

                totalTime += end - start;
                totalMovements += array.getMovementCount();

                completed += operations;
            }
        }

        writer.println(
                n + ",DynamicArray,removal,"
                        + position + ","
                        + (totalTime / RUNS) + ","
                        + (totalMovements / RUNS));
    }

    private static void benchmarkLinkedListRemoval(
            PrintWriter writer,
            int n,
            int[] data,
            int index,
            String position) {

        long totalTime = 0;
        long totalAccesses = 0;

        for (int run = 0; run < RUNS; run++) {

            int completed = 0;

            while (completed < MODIFY_OPERATIONS) {

                LinkedList list = buildLinkedList(data);
                list.resetCounters();

                int possible = Math.max(1, n - index);

                int operations = Math.min(
                        possible,
                        MODIFY_OPERATIONS - completed);

                long start = System.nanoTime();

                for (int i = 0; i < operations; i++) {
                    list.remove(index);
                }

                long end = System.nanoTime();

                totalTime += end - start;
                totalAccesses += list.getAccessCount();

                completed += operations;
            }
        }

        writer.println(
                n + ",LinkedList,removal,"
                        + position + ","
                        + (totalTime / RUNS) + ","
                        + (totalAccesses / RUNS));
    }

    private static void workload4Heap() throws IOException {

        System.out.println("\nWorkload 4: Priority Processing");

        PrintWriter writer = new PrintWriter(new FileWriter(
                "results/tables/heap.csv"));

        writer.println(
                "n,operation,averageTimeNs,averageComparisons,complexity");

        for (int n : SIZES) {

            Random random = new Random(SEED);
            int[] data = generateData(n, random);

            long insertionTime = 0;
            long insertionComparisons = 0;

            long extractionTime = 0;
            long extractionComparisons = 0;

            for (int run = 0; run < RUNS; run++) {

                MinHeap heap = new MinHeap();

                heap.resetComparisonCount();

                long start = System.nanoTime();

                for (int value : data) {
                    heap.insert(value);
                }

                long end = System.nanoTime();

                insertionTime += end - start;
                insertionComparisons += heap.getComparisonCount();

                check(heap.isValidHeap(),
                        "Heap invalid after insertion");

                heap.resetComparisonCount();

                int previous = Integer.MIN_VALUE;

                start = System.nanoTime();

                while (!heap.isEmpty()) {

                    int current = heap.extractMin();

                    if (current < previous) {
                        throw new AssertionError(
                                "Heap extraction order invalid");
                    }

                    previous = current;
                }

                end = System.nanoTime();

                extractionTime += end - start;
                extractionComparisons += heap.getComparisonCount();
            }

            writer.println(
                    n + ",insert,"
                            + (insertionTime / RUNS) + ","
                            + (insertionComparisons / RUNS)
                            + ",O(log n)");

            writer.println(
                    n + ",extractMin,"
                            + (extractionTime / RUNS) + ","
                            + (extractionComparisons / RUNS)
                            + ",O(log n)");

            System.out.println("n = " + n + " finished");
        }

        writer.close();
    }

    private static int[] generateData(
            int n,
            Random random) {

        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(n * 2);
        }

        return data;
    }

    private static int[] generateIndices(
            int n,
            int count,
            Random random) {

        int[] indices = new int[count];

        for (int i = 0; i < count; i++) {
            indices[i] = random.nextInt(n);
        }

        return indices;
    }

    private static DynamicArray buildDynamicArray(
            int[] data) {

        DynamicArray array = new DynamicArray();

        for (int value : data) {
            array.add(value);
        }

        array.resetCounters();

        return array;
    }

    private static LinkedList buildLinkedList(
            int[] data) {

        LinkedList list = new LinkedList();

        for (int i = data.length - 1; i >= 0; i--) {
            list.add(0, data[i]);
        }

        list.resetCounters();

        return list;
    }

    private static void check(
            boolean condition,
            String message) {

        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
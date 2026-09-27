public class Tests {

    public static void main(String[] args) {
        testDynamicArray();
        testLinkedList();
        testMinHeap();

        System.out.println("ALL TESTS PASSED");
    }

    private static void testDynamicArray() {
        System.out.println("Testing DynamicArray...");

        DynamicArray array = new DynamicArray();

        check(array.size() == 0, "DynamicArray empty size");

        array.add(10);
        check(array.get(0) == 10, "DynamicArray one element");

        array.add(20);
        array.add(30);
        check(array.get(1) == 20, "DynamicArray multiple elements");

        array.add(20);
        check(array.contains(20), "DynamicArray duplicates");

        array.add(1, 99);
        check(array.get(1) == 99, "DynamicArray indexed add");

        array.add(0, 5);
        check(array.get(0) == 5, "DynamicArray add at beginning");

        array.add(array.size(), 100);
        check(array.get(array.size() - 1) == 100,
                "DynamicArray add at end");

        int removed = array.remove(0);
        check(removed == 5, "DynamicArray remove");

        boolean exceptionThrown = false;

        try {
            array.get(1000);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        check(exceptionThrown, "DynamicArray invalid index");

        DynamicArray large = new DynamicArray();

        for (int i = 0; i < 100000; i++) {
            large.add(i);
        }

        check(large.size() == 100000, "DynamicArray large size");
        check(large.get(99999) == 99999, "DynamicArray large input");

        System.out.println("DynamicArray PASSED");
    }

    private static void testLinkedList() {
        System.out.println("Testing LinkedList...");

        LinkedList list = new LinkedList();

        check(list.size() == 0, "LinkedList empty size");

        list.add(10);
        check(list.get(0) == 10, "LinkedList one element");

        list.add(20);
        list.add(30);
        check(list.get(2) == 30, "LinkedList multiple elements");

        list.add(20);
        check(list.contains(20), "LinkedList duplicates");

        list.add(1, 99);
        check(list.get(1) == 99, "LinkedList indexed add");

        list.add(0, 5);
        check(list.get(0) == 5, "LinkedList add at beginning");

        list.add(list.size(), 100);
        check(list.get(list.size() - 1) == 100,
                "LinkedList add at end");

        int removed = list.remove(0);
        check(removed == 5, "LinkedList remove");

        boolean exceptionThrown = false;

        try {
            list.get(1000);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        check(exceptionThrown, "LinkedList invalid index");

        LinkedList large = new LinkedList();

        for (int i = 0; i < 10000; i++) {
            large.add(i);
        }

        check(large.size() == 10000, "LinkedList large size");
        check(large.get(9999) == 9999, "LinkedList large input");

        System.out.println("LinkedList PASSED");
    }

    private static void testMinHeap() {
        System.out.println("Testing MinHeap...");

        MinHeap heap = new MinHeap();

        check(heap.isEmpty(), "Heap empty");

        heap.insert(5);
        check(heap.peekMin() == 5, "Heap one element");
        check(heap.isValidHeap(), "Heap valid after one insertion");

        heap.insert(2);
        heap.insert(8);
        heap.insert(1);
        heap.insert(3);

        check(heap.peekMin() == 1, "Heap minimum");
        check(heap.isValidHeap(), "Heap property after insertions");

        heap.insert(1);
        check(heap.peekMin() == 1, "Heap duplicates");
        check(heap.isValidHeap(), "Heap valid with duplicates");

        int previous = Integer.MIN_VALUE;

        while (!heap.isEmpty()) {
            int current = heap.extractMin();

            check(current >= previous,
                    "Heap extraction must be non-decreasing");

            previous = current;

            check(heap.isValidHeap(),
                    "Heap property after extraction");
        }

        boolean exceptionThrown = false;

        try {
            heap.extractMin();
        } catch (Exception e) {
            exceptionThrown = true;
        }

        check(exceptionThrown, "Heap empty extraction");

        MinHeap large = new MinHeap();

        for (int i = 100000; i >= 1; i--) {
            large.insert(i);
        }

        check(large.peekMin() == 1, "Heap large input min");
        check(large.isValidHeap(), "Heap large input property");

        System.out.println("MinHeap PASSED");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("FAILED: " + message);
        }
    }
}
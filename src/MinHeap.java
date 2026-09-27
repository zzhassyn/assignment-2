import java.util.NoSuchElementException;

public class MinHeap {

    private int[] heap;
    private int size;
    private long comparisonCount;

    public MinHeap() {
        heap = new int[10];
        size = 0;
        comparisonCount = 0;
    }

    public void insert(int value) {
        ensureCapacity();

        heap[size] = value;
        int current = size;
        size++;

        while (current > 0) {
            int parent = (current - 1) / 2;

            comparisonCount++;

            if (heap[current] >= heap[parent]) {
                break;
            }

            swap(current, parent);
            current = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }

        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }

        int min = heap[0];

        heap[0] = heap[size - 1];
        size--;

        int current = 0;

        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;
            int smallest = current;

            if (left < size) {
                comparisonCount++;

                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                comparisonCount++;

                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == current) {
                break;
            }

            swap(current, smallest);
            current = smallest;
        }

        return min;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public long getComparisonCount() {
        return comparisonCount;
    }

    public void resetComparisonCount() {
        comparisonCount = 0;
    }

    public boolean isValidHeap() {
        for (int i = 0; i < size; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size && heap[i] > heap[left]) {
                return false;
            }

            if (right < size && heap[i] > heap[right]) {
                return false;
            }
        }

        return true;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            int[] newHeap = new int[heap.length * 2];

            for (int i = 0; i < heap.length; i++) {
                newHeap[i] = heap[i];
            }

            heap = newHeap;
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
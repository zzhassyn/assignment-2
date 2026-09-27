public class DynamicArray {

    private int[] data;
    private int size;

    private long comparisonCount;
    private long movementCount;
    private long accessCount;

    public DynamicArray() {
        data = new int[10];
        size = 0;

        comparisonCount = 0;
        movementCount = 0;
        accessCount = 0;
    }

    public void add(int value) {
        ensureCapacity();

        data[size] = value;
        size++;
    }

    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index);
        }

        ensureCapacity();

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            movementCount++;
        }

        data[index] = value;
        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        int removedValue = data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            movementCount++;
        }

        size--;

        return removedValue;
    }

    public int get(int index) {
        checkIndex(index);

        accessCount++;

        return data[index];
    }

    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            comparisonCount++;

            if (data[i] == value) {
                return true;
            }
        }

        return false;
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

    public long getMovementCount() {
        return movementCount;
    }

    public long getAccessCount() {
        return accessCount;
    }

    public void resetComparisonCount() {
        comparisonCount = 0;
    }

    public void resetMovementCount() {
        movementCount = 0;
    }

    public void resetAccessCount() {
        accessCount = 0;
    }

    public void resetCounters() {
        comparisonCount = 0;
        movementCount = 0;
        accessCount = 0;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            int[] newData = new int[data.length * 2];

            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }

            data = newData;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index);
        }
    }
}
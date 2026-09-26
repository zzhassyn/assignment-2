public class DynamicArray {
    private int[] data;
    private int size;

    public DynamicArray() {
        data = new int[10];
        size = 0;
    }

    public void add(int value) {
        ensureCapacity();
        data[size] = value;
        size++;
    }

    public int get(int index) {
        checkIndex(index);
        return data[index];
    }

    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                return true;
            }
        }

        return false;
    }

    public int remove(int index) {
        checkIndex(index);

        int removedValue = data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        size--;

        return removedValue;
    }

    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        ensureCapacity();

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        data[index] = value;
        size++;
    }

    public int size() {
        return size;
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
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}
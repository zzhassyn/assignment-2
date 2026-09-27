public class LinkedList {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    private long comparisonCount;
    private long accessCount;

    public LinkedList() {
        head = null;
        size = 0;
        comparisonCount = 0;
        accessCount = 0;
    }

    public void add(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newNode;
        }

        size++;
    }

    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Node newNode = new Node(value);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            accessCount++;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                accessCount++;
            }

            newNode.next = current.next;
            current.next = newNode;
        }

        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        int removedValue;

        if (index == 0) {
            accessCount++;

            removedValue = head.value;
            head = head.next;
        } else {
            Node current = head;
            accessCount++;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                accessCount++;
            }

            accessCount++;

            removedValue = current.next.value;
            current.next = current.next.next;
        }

        size--;

        return removedValue;
    }

    public int get(int index) {
        checkIndex(index);

        Node current = head;
        accessCount++;

        for (int i = 0; i < index; i++) {
            current = current.next;
            accessCount++;
        }

        return current.value;
    }

    public boolean contains(int value) {
        Node current = head;

        while (current != null) {
            comparisonCount++;

            if (current.value == value) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    public int size() {
        return size;
    }

    public long getComparisonCount() {
        return comparisonCount;
    }

    public long getAccessCount() {
        return accessCount;
    }

    public void resetComparisonCount() {
        comparisonCount = 0;
    }

    public void resetAccessCount() {
        accessCount = 0;
    }

    public void resetCounters() {
        comparisonCount = 0;
        accessCount = 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}
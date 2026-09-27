public class Tests {
    public static void main(String[] args) {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);
        heap.insert(3);

        System.out.println(heap.peekMin());
        System.out.println(heap.isValidHeap());

        while (!heap.isEmpty()) {
            System.out.println(heap.extractMin());
        }
    }
}
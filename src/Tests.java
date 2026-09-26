public class Tests {
    public static void main(String[] args) {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);
        array.add(40);

        int removed = array.remove(1);

        System.out.println("Removed: " + removed);
        System.out.println(array.get(0));
        System.out.println(array.get(1));
        System.out.println(array.get(2));
        System.out.println("Size: " + array.size());
    }
}
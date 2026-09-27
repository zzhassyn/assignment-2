public class Tests {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        list.add(1, 99);

        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));

        System.out.println(list.remove(1));
        System.out.println(list.size());

        System.out.println(list.contains(20));
        System.out.println(list.contains(99));
    }
}
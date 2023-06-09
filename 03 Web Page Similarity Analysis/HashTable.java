public class HashTable {

    static double RESIZE_THRESHOLD = .75;

    public static class Node {
        public String key;
        public Node next;
        int value;

        Node (String k, int v, Node n) {
            key = k;
            value = v;
            next = n;
        }
    }

    public Node[] table = new Node[16];
    int count = 0;

    public int get (String k) {
        int h = k.hashCode();
        int i = h & (table.length - 1);
        for (Node p = table[i]; p != null; p = p.next) {
            if (k.equals(p.key)) {
                return p.value;
            }
        }
        return 0;
    }

    public void add (String k) {
        int h = k.hashCode();
        int i = h & (table.length -1);
        for (Node p = table[i]; p != null; p = p.next) {
            if (k.contentEquals(p.key)) {
                ++p.value;
                return;
            }
        }

        table[i] = new Node(k, 1, table[i]);
        if (++count > RESIZE_THRESHOLD * table.length) {
            resize();
        }
    }

    public  void resize(){
        int m = table.length;
        Node [] old = table;
        table = new Node[2*m];

        for (int i = 0; i< m; ++i){
            // Node p = table[i];
            while (old[i] !=null){
                rehash(old[i].key,old[i].value);
                old[i]=old[i].next;
            }
        }
    }

    void rehash(String key, int value) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        table[i] = new Node(key, value, table[i]);
    }
}


public class Hashtables {

    static class Node {

        //Node holds a (key,value) pair
        String key;
        int value;
        Node next; // Pointer to next node in the list

        Node(String k, int v, Node n) {
            key = k;
            value = v;
            next = n;
        }
    }

    // The hash table, represented as an array of linked lists
    Node[] table;

    // The number of (key,value) pairs in the hash table
    int count = 0;

    // first node is null
    boolean firstNode = true;

    // first node
    Node first;
    // Create a hash table with size of 10
    public Hashtables() {
        table = new Node[10];
    }

    // add to the hashtable
    public void add(String k) {
        int h = k.hashCode();
        int i = h & (table.length - 1);
        for (Node p = table[i]; p != null; p = p.next) {
            if (k.equals(p.key)) {
                ++p.value;
                return;
            }
        }

        table[i] = new Node(k, 1, table[i]);
        if (firstNode) {
            first = table[i];
            firstNode = false;
        }

        if (++count > (0.75 * table.length)) {
            resize();
        }
    }

    // get the specific value associated with specific key in the table
    public int get(String k) {
        int h = k.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (k.equals(e.key)) {
                return e.value;
            }
        }
        return 0;
    }

    // resize the table by the length on x2
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

    // rehash
    public void rehash(String k, int v){
        int h = k.hashCode();
        int i = h & (table.length - 1);
        table[i]= new Node(k, v, table[i]);
    }

    //boolean value to find true or false
    public boolean contains(String k){
        int h = k.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (k.equals(e.key)) {
                return true;
            }
        }
        return false;
    }

}

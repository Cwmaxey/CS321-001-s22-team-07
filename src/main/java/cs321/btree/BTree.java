package cs321.btree;

import java.util.Comparator;

// added in Comparable - alex
public class BTree<E>{
    // Constructor for BTree class.
    public BTree<E>(
    BTree<E> T)
    {
        x = new BTree<E>(); // allocate new node
        x.leaf = true;
        x.n = 0; // number of keys in tree
        T.root = x;
        RandomAccessFileWrite(T);
    }

    BTree_Create(BTree <E>) {
        x = New BTree<E>();
    }

    // Inserts into a BTree.
    // If full, splits the child then calls B_Tree_Insert_Nonfull.
    private void BTree_Insert(BTree <E>, TreeObject k) {
        return null
    }

    // Inserts into a non-full BTree TreeObject.
    private void BTree_Insert_Nonfull(TreeObject x, int key) {
        return null
    }

    // BTree_Split_Child splits a node that is full
    // based on degree t.
    // writes to disk if successful
    private void BTree_Split_Child(TreeObject x, int key) {
        return null
    }

    // BTree_Search looks for a node with key k in a given node x.
    private TreeObject<E> BTree_Search(TreeObject<E> x, int key) {
        int i = 0; // start at the root, top down search method
        // loop thru the keys starting at the root,
        // while the index is < than the total number of x Nodes
        // and the key to be searched for is > than the key at index 'x'
        // increment the search
        while(i <= x.n && key > x.key[i]){
            i++;
        }
        // if key is found return node and index
        if(i <= x.n && key == x.key[i]){
            return(x, key);
        } else if(x.leaf){
            return null;
        } else {
            RandomAccessFileRead();
            return BTree_Search(x, key);
        }



    }

    // https://docs.oracle.com/javase/8/docs/api/java/io/RandomAccessFile.html#writeBytes-java.lang.String-
    public void RandomAccessFileWrite(BTree<E> T) {
        // writeBytes(String s) to write binary to a disk file.
    }

    // https://docs.oracle.com/javase/8/docs/api/java/io/RandomAccessFile.html#readLong--
    public long RandomAccessFileRead() {
        // btree.readLong()
    }

}

package cs321.btree;

import java.util.LinkedList;
import java.util.Queue;

// added in Comparable - alex
public class BTree {
    public BTreeNode root; // Pointer to root node
    public int t; // Minimum degree
    public BTreeNode currentChild;
    // Constructor for BTree class.
    public BTree(int t) {
        BTreeNode x = new BTreeNode(t, true);
        RandomAccessFileWrite(x);
        this.root = x;
        this.t = t;
    }

    public BTreeNode getRoot() {
        return this.root;
    }

    public class BTreeNode {
        public TreeObject keys[]; // Array of keys
        public long childPointers[]; // An array of child pointers
        int t; // degree t (max number of keys a node can have 2t-1)
        boolean leaf; // check if node is a leaf or not
        int n; // returns the number of keys in BTree
        long addressSelf; // Holds a pointer to the nodes position
        long parentPointer; // Holds a pointer to the parent

        public BTreeNode(int t, long parent, long addressSelf) {
            this.t = t;
            this.leaf = true;
            this.childPointers = new long[2 * t];
            this.keys = new TreeObject[(2 * t) - 1];
            this.n = 0;
            this.parentPointer = parent;
            this.addressSelf = addressSelf;
        }

        public BTreeNode(int t, boolean leaf) {
            this.t = t;
            this.leaf = leaf;
            this.childPointers = new long[2 * t];
            this.keys = new TreeObject[(2 * t) - 1];
            this.n = 0;

        }

        private long accessAddressSelf() {
            return this.addressSelf;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public BTreeNode getParent() {
            if (parentPointer == -1L) 
                return null; // current node is root

            return null; // This needs to load the noad from storage once we have that implemented.
            
        }

        //toString returns string with contents of Keys with order
    public String toString()
    {
        String ret = "";
        for(int i=0; i<this.keys.length; i++)
        {
            ret +=keys[i] + ",";
        }
        return ret;
    }

    }

    // Inserts into a BTree.
    // If full, splits the child then calls B_Tree_Insert_Nonfull.
    public void BTree_Insert(long k) {
        BTreeNode r = this.root;
        int t = this.t;
        if (r.n == (2 * t - 1)) {
            BTreeNode s = new BTreeNode(t, false);
            this.root = s;
            s.n = 0;
            s.childPointers[0] = r.addressSelf;
            BTree_Split_Child(s, 1, r);
            BTree_Insert_Nonfull(s, k);
        } else {
            BTree_Insert_Nonfull(r, k);
        }
    }

    // Inserts into a non-full BTree TreeObject.
    private void BTree_Insert_Nonfull(BTreeNode x, long key) {
        // The first if block handles if x is a leaf node by inserting key k into x.
        // If x is not a leaf node then we insert k into the appropriate leaf node in
        // the subtree
        // that is rooted at internal node x.
        int i = x.n - 1;
        if (x.isLeaf()) {
            while (i >= 0 && key < x.keys[i].getKey()) {
                x.keys[i + 1] = new TreeObject(x.keys[i].getKey(), x.keys[i].getFreq());
                i--;
            }
            x.keys[i + 1] = new TreeObject(key);
            x.n++;
            RandomAccessFileWrite(x);
        } else {
            while (i >= 0 && key < x.keys[i].getKey()) {
                i--;
            }
            i++;
            BTreeNode child;
            if (x.childPointers[i] != -1) {
                child = RandomAccessFileRead(x.childPointers, i);
                if (child.n == 2*t-1) {
                    BTree_Split_Child(child, i, x);
                    if (key > x.keys[i].getKey()) {
                        i++;
                    }
                }
                BTreeNode newNode = RandomAccessFileRead(x.childPointers, i);
                BTree_Insert_Nonfull(newNode, key); 
            }
            BTreeNode childInsert = RandomAccessFileRead(x.childPointers,i);
            BTree_Insert_Nonfull(childInsert, key);
        }
    }

    // BTree_Split_Child splits a node that is full
    // based on degree t.
    // writes to disk if successful
    // x is the parent of y, y is the node being split.
    // z represents the new node which is half of y's keys/children
    // -1L indicates an empty TreeObject
    private void BTree_Split_Child(BTreeNode x, int key, BTreeNode y) {
        BTreeNode z = new BTreeNode(t, y.parentPointer, key);
        z.leaf = y.isLeaf();
        z.n = t-1;
        RandomAccessFileWrite(z);

        for (int i = 0; i < t-1; i++) {
            z.keys[i] = new TreeObject(y.keys[i+t].getKey(), x.keys[i].getFreq());
            y.keys[i+t] = new TreeObject();
        }
        if (!y.isLeaf()) {
            for (int i = 0; i < t; i++) {
                z.childPointers[i] = y.childPointers[i+t];
                y.childPointers[i+t] = -1L;
            }
        }

        // Number of keys under node being split
        y.n = t-1;
        for (int i = x.n; i > key; i--) {
            x.childPointers[i+1] = x.childPointers[i];
            x.childPointers[i] = -1L;
        }

        x.childPointers[key+1] = z.addressSelf;
        for (int i = x.n-1; i > key-1; i--) {
            x.keys[i+1] = new TreeObject(x.keys[i].getKey(), x.keys[i].getFreq());
        }
        x.keys[key] = new TreeObject(y.keys[t-1].getKey(), y.keys[t-1].getFreq());
        y.keys[t-1] = new TreeObject();
        x.n = x.n+1;
        RandomAccessFileWrite(z);
        RandomAccessFileWrite(y);
        RandomAccessFileWrite(x);

    }   

    // BTree_Search looks for a node with key k in a given node x.
    private TreeObject BTree_Search(BTreeNode x, int key) {
        int i = 1;
        // start at the root, top down search method
        // loop thru the keys starting at the root,
        // while the index is < than the total number of x Nodes
        // and the key to be searched for is > than the key at index 'x'
        // increment the search
        while (i <= x.n && key > x.keys[i].DNA) {
            i++;
        }
        // if key is found return node and index
        if (i <= x.n && key == x.keys[i].DNA) {
            return (x.keys[i]);
        } else if (x.leaf) {
            return null;
        } else {
            RandomAccessFileRead(x.childPointers, i);
            return BTree_Search(x, i);
        }

    }

    


    // Gets an internal node using Level Order traversal.
    public String GetNodeAtIndex(int j) {
        if(j<1)
        {
            return null;
        }
        Queue<BTreeNode> q = new LinkedList<BTreeNode>();
        q.add(this.root);
        int i = 0;
        while (!q.isEmpty()){
            BTreeNode n = q.remove();
            if(i==j)
            {
                return n.toString();
            }
            else
            {
                i++;
            }
            if(!n.leaf)
            {
                for(int c=1; c<n.childPointers.length; i++)
                {
                    BTreeNode child = RandomAccessFileRead(n.childPointers,c);
                    q.add(child);
                }
            }
        }
        // q = newQueue()->queue interface using a list
        // q.enQueue(root)
        // while (!q.isEmpty())
        // n = q.deQueue()
        // if i == j
        // return n
        // else
        // i++
        // if (!n.leaf)
        // for each child pointer c in n
        // child = disk-read(c)
        // q.enQueue(child)
        return null;
    }

    // https://docs.oracle.com/javase/8/docs/api/java/io/RandomAccessFile.html#writeBytes-java.lang.String-
    public void RandomAccessFileWrite(BTreeNode T) {
        // writeBytes(String s) to write binary to a disk file.
    }

    // https://docs.oracle.com/javase/8/docs/api/java/io/RandomAccessFile.html#readLong--
    public BTreeNode RandomAccessFileRead(long[] C, int index) {
        BTreeNode temp = this.currentChild;//instead of currentChild temp should be the ACTUAL child node
        this.currentChild = temp; //sets currentChild to whatever was read
        return temp;
    }

}

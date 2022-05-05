package cs321.btree;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;

public class BTree {
    public BTreeNode root; // Pointer to root node
    public int t; // Minimum degree
    public boolean cacheFlag; // Used for using the cache
    public RandomAccessFile raf;
    public File fileName; // Name of the file
    public LinkedList<BTreeNode> newCache; // used in cache

    // Constructor for BTree class.
    public BTree(int t) {
        BTreeNode x = new BTreeNode(t);
        // RandomAccessFileWrite(x);
        this.root = x;
        this.t = t;
    }

    public class BTreeNode {
        public TreeObject keys[]; // Array of keys
        public long childPointers[]; // An array of child pointers
        public int t; // degree t (max number of keys a node can have 2t-1)
        public boolean leaf; // check if node is a leaf or not
        public int n; // returns the number of keys in BTree
        public long addressSelf; // Holds a pointer to the nodes position (file position)
        public long parentPointer; // Holds a pointer to the parent

        public BTreeNode(int t, long parent, long addressSelf) {
            this.t = t;
            this.leaf = true;
            this.childPointers = new long[2 * t];
            this.n = 0;
            this.parentPointer = parent;
            this.addressSelf = addressSelf;
            // initialization of childPointers array to NULL
            for (int i = 0; i < childPointers.length; i++) {
                childPointers[i] = -1L;
            }

            this.keys = new TreeObject[(2 * t) - 1];
            // initialization of childPointers array to NULL
            for (int i = 0; i < keys.length; i++) {
                keys[i] = new TreeObject(-1L);
                n++;
            }
        }

        public BTreeNode(int t) {
            this.t = t;
            this.leaf = true;
            this.childPointers = new long[2 * t];
            this.keys = new TreeObject[(2 * t) - 1];
            this.n = 0;
        }

        public long accessAddressSelf() {
            return this.addressSelf;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public BTreeNode getParent() {
            if (parentPointer == -1L) {
                return null; // current node is root
            }
            // RandomAccessFileWrite(fileName, data, T);

            return null; // This needs to load the noad from storage once we have that implemented.

        }

        // returns the number of BTree keys available
        public int getBTreeKeyCount() {
            return this.n;
        }

    }

    // Inserts into a BTree.
    // If full, splits the child then calls B_Tree_Insert_Nonfull.
    public void BTree_Insert(BTree T, TreeObject k) {
        BTreeNode r = T.root;
        int t = this.t;
        if (r.n == (2 * t - 1)) {
            BTreeNode s = new BTreeNode(t);
            T.root = s;
            s.n = 0;
            s.childPointers[1] = r.addressSelf; // setting child of new root to previous root
            BTree_Split_Child(s, 1, r);
            BTree_Insert_Nonfull(s, k.DNA);
        } else {
            BTree_Insert_Nonfull(r, k.DNA);
        }
    }

    // Inserts into a non-full BTree TreeObject.
    public void BTree_Insert_Nonfull(BTreeNode x, long key) {
        // The first if block handles if x is a leaf node by inserting key k into x.
        // If x is not a leaf node then we insert k into the appropriate leaf node in
        // the subtree
        // that is rooted at internal node x.
        // key is the DNA
        int i = x.n - 1;
        if (x.isLeaf()) {
            while (i >= 0 && key < x.keys[i].getKey()) {
                x.keys[i + 1] = new TreeObject(x.keys[i].getKey(), x.keys[i].getFreq());
                i--;
            }
            x.keys[i + 1] = new TreeObject(key);
            x.n++;
            // RandomAccessFileWrite(x);
        } else {
            while (i >= 0 && key < x.keys[i].getKey()) {
                i--;
            }
            i++;
            BTreeNode child;
            if (x.childPointers[i] != -1) {
                child = RandomAccessFileRead(x.childPointers, i);
                if (child.n == 2 * t - 1) {
                    BTree_Split_Child(x, i, child);
                    if (key > x.keys[i].getKey()) {
                        i++;
                    }
                }
                BTreeNode newNode = RandomAccessFileRead(x.childPointers, i);
                BTree_Insert_Nonfull(newNode, key);
            }
        }
    }

    // BTree_Split_Child splits a node that is full
    // based on degree t.
    // writes to disk if successful
    // x is the parent of y, y is the node being split.
    // z represents the new node which is half of y's keys/children
    // -1L indicates an empty TreeObject
    public void BTree_Split_Child(BTreeNode x, int key, BTreeNode y) {
        BTreeNode z = new BTreeNode(t, y.parentPointer, key);
        z.leaf = y.isLeaf();
        z.n = t - 1;
        // RandomAccessFileWrite(z);

        for (int i = 0; i < t - 1; i++) {
            z.keys[i] = new TreeObject(y.keys[i + t].getKey(), x.keys[i].getFreq());
            y.keys[i + t] = new TreeObject();
        }
        if (!y.isLeaf()) {
            for (int i = 0; i < t; i++) {
                z.childPointers[i] = y.childPointers[i + t];
                y.childPointers[i + t] = -1L;
            }
        }

        // Number of keys under node being split
        y.n = t - 1;
        for (int i = x.n; i > key; i--) {
            x.childPointers[i + 1] = x.childPointers[i];
            x.childPointers[i] = -1L;
        }

        x.childPointers[key + 1] = z.addressSelf;
        for (int i = x.n - 1; i > key - 1; i--) {
            x.keys[i + 1] = new TreeObject(x.keys[i].getKey(), x.keys[i].getFreq());
        }
        x.keys[key] = new TreeObject(y.keys[t - 1].getKey(), y.keys[t - 1].getFreq());
        y.keys[t - 1] = new TreeObject();
        x.n = x.n + 1;
        // RandomAccessFileWrite(z);
        // RandomAccessFileWrite(y);
        // RandomAccessFileWrite(x);

    }

    // BTree_Search looks for a node with key k in a given node x.
    public TreeObject BTree_Search(BTreeNode x, int key) {
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
            // RandomAccessFileRead(x.childPointers, i);
            return BTree_Search(x, i);
        }

    }

    // Gets an internal node using Level Order traversal.
    public TreeObject GetNodeAtIndex(int j) {
        // if i < 1
        // return error
        // q = newQueue()
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
    public void RandomAccessFileWrite(BTreeNode node) throws IOException {
        // writeBytes(String s) to write binary to a disk file.
        try {
            RandomAccessFile rafw = new RandomAccessFile(fileName, "rw");
            ByteBuffer buffer = ByteBuffer.allocate(node.n);

            // moves file pointer to position specified
            // then will start at position 0 then read in bytes maintaining correct position
            rafw.seek(node.addressSelf);

            // scan through the DNA sequence and retrieve the nodes keys frequency and keys
            for (int i = 1; i < node.keys.length; i++) {
                rafw.writeInt(node.keys[i].getFreq());
                rafw.writeLong(node.keys[i].getKey());
            }

            for (int i = 1; i < node.childPointers.length; i++) {
                rafw.writeLong(node.childPointers[i]);
            }

            // build the byte buffer
            buffer.putInt(node.n);
            buffer.putLong(node.n);

            // writing Primitives to RandomAccessFile
            rafw.writeInt(node.n);
            rafw.writeLong(node.addressSelf);
            rafw.writeBoolean(node.leaf);

            // add ignore "n"

            rafw.close();

            if (cacheFlag) {
                LinkedList<BTreeNode> newCache = BTreeCache.getBTreeCache();
                newCache.getObject(RandomAccessFileRead(newCache.accessAddressSelf());
                newCache.add(node);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // https://docs.oracle.com/javase/8/docs/api/java/io/RandomAccessFile.html#readLong--
    public BTreeNode RandomAccessFileRead(int index) throws IOException {
        // btree.readLong()

        if (cacheFlag) {
            LinkedList<BTreeNode> cache = BTreeCache.getBTreeCache();
            Iterator<BTreeNode> iterator = cache.iterator();
            while (iterator.hasNext()) {
                BTreeNode node = iterator.next();
                if (node.addressSelf == index) {
                    return node;
                }
            }
        }
        BTreeNode node = new BTreeNode(t, index);

        try {
            raf = new RandomAccessFile(fileName, "r");
            raf.seek(index);

            // scan through the DNA sequence and retrieve the nodes keys frequency and keys
            for (int i = 1; i < node.keys.length; i++) {
                if (i <= node.n) {

                }
                node.keys[i].DNA = raf.readLong();
                node.keys[i].freq = raf.readInt();
            }

            for (int i = 1; i < node.childPointers.length; i++) {
                node.childPointers[i] = raf.readLong();
            }

            node.n = raf.readInt();
            node.leaf = raf.readBoolean();
            node.addressSelf = raf.readLong();
            raf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return node;
    }

}

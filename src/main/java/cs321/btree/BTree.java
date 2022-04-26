package cs321.btree;

import java.util.LinkedList;
import java.util.Queue;

// added in Comparable - alex
public class BTree {
    public BTreeNode root; // Pointer to root node
    public int t; // Minimum degree
    public BTreeNode currentChild;
    // Constructor for BTree class.
    BTree(int t) {
        BTreeNode x = new BTreeNode(t, true);
        RandomAccessFileWrite(x);
        this.root = x;
        this.t = t;
    }

    private class BTreeNode {
        public TreeObject keys[]; // Array of keys
        public long childPointer[]; // An array of child pointers
        int t; // degree t (max number of keys a node can have 2t-1)
        boolean leaf; // check if node is a leaf or not
        int n; // returns the number of keys in BTree
        long addressSelf;

        BTreeNode(int t, boolean leaf) {
            this.t = t;
            this.leaf = leaf;
            this.childPointer = new long[2 * t];
            this.keys = new TreeObject[(2 * t) - 1];
            this.n = 0;

        }

        private long accessAddressSelf() {
            return this.addressSelf;
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
            s.childPointer[1] = r;
            BTree_Split_Child(s, 1);
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
        int i = x.n;
        if (x.leaf) {
            while (i >= 1 && key < x.keys[i].DNA) {
                x.keys[i + 1] = x.keys[i];
                i--;
            }
            x.keys[i + 1].DNA = key;
            x.n = x.n + 1;
            RandomAccessFileWrite(x);
        } else {
            while (i >= 1 && key < x.keys[i].DNA) {
                i--;
            }
            i++;
            RandomAccessFileRead(x.childPointer, i);
            if (x.childPointer[i].n == (2 * t - 1)) {
                BTree_Split_Child(x, i);
                if (key > x.keys[i].DNA) {
                    i++;
                }
            }
            BTreeNode child = RandomAccessFileRead(x.childPointer,i);
            BTree_Insert_Nonfull(child, key);
        }
    }

    // BTree_Split_Child splits a node that is full
    // based on degree t.
    // writes to disk if successful
    private void BTree_Split_Child(BTreeNode x, int key) {

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
            RandomAccessFileRead(x.childPointer, i);
            return BTree_Search(x, i);
        }

    }

    //toString returns string with contents of Keys with order
    public String toString()
    {
        String treeString="";
        //Use Inorder traversal to print the tree from left to right
        //Format: keys separated by commas
        
        return treeString;
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
                for(int c=1; c<n.childPointer.length; i++)
                {
                    BTreeNode child = RandomAccessFileRead(n.childPointer,c);
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
        // TODO btree.readLong()
        BTreeNode temp = this.currentChild;//instead of currentChild temp should be the ACTUAL child node
        this.currentChild = temp; //sets currentChild to whatever was read
        return temp;
    }

}

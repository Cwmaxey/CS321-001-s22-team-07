package cs321.btree;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * BTree node class
 */
public class TreeObject<E> extends Comparable<E>
{
    public ArrayList<E> BTreeList;
    int t; // degree t (max number of keys a node can have 2t-1)
    boolean leaf; // check if node is a leaf or not
    int n; // returns the number of keys in BTree
    //TreeObject<E> child = new TreeObject<E>(2 * t);

    // Constructor
    public TreeObject(int t){
        this.t = t;
        this.leaf = leaf;
        this.n = 0;
        BTreeList = new ArrayList<E>((2 * t) -1);
    }


}

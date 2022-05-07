import java.util.LinkedList;

import cs321.btree.BTree.BTreeNode;

/**
 * BTree cache implementation
 */
public class BTreeCache {
    private static LinkedList<BTreeNode> BTreeCache;
    private int cacheSize; // max cache size

    // BTreeCache constructor
    public BTreeCache(int cacheSize) {
        this.cacheSize = cacheSize;
        BTreeCache = new LinkedList<BTreeNode>();
    }

    // returns and instance of the BTreeCache
    public static LinkedList<BTreeNode> getBTreeCache() {
        return BTreeCache;
    }

    public int getCacheSize(int cacheSize) {
        return this.cacheSize;
    }

    // check is node is in cache
    public BTreeNode getObject(BTreeNode node) {
        if (BTreeCache.contains(node)) {
            return node;
        } else {

            return null;
        }
    }

    // error checking and if cache size is at capacity then remove the last node and
    // then insert at front
    // otherwise just insert at front
    public void addObject(BTreeNode node) {
        if (cacheSize == 0) {
            return;
        } else if (BTreeCache.size() == cacheSize) {
            BTreeCache.removeLast();
            BTreeCache.addFirst(node);
            cacheSize++;
        } else {
            BTreeCache.addFirst(node);
            cacheSize++;
        }
    }

    // removing first occurence of node from cache
    public void removeObject(BTreeNode node) {
        BTreeCache.remove(node);
        cacheSize--;
    }

    // removeing the last occurence of node from cache
    public void removeLastObject() {
        BTreeCache.removeLast();
        cacheSize--;
    }

    // clears the cache starting at the front of the cache
    public void clearCache() {
        while (!BTreeCache.isEmpty()) {
            BTreeCache.removeFirst();
        }
    }

}
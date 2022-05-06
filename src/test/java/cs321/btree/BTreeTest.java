package cs321.btree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BTreeTest
{
    long DNA;
        // HINT:
    //  instead of checking all intermediate states of constructing a tree
    //  you can check the final state of the tree and
    //  assert that the constructed tree has the expected number of nodes and
    //  assert that some (or all) of the nodes have the expected values
    public static void main(String[] args){
        BTree test = new BTree(2);
        long A = 1;
        long B = 2;
        long C = 3;
        test.BTree_Insert(A);
        test.BTree_Insert(B);
        test.BTree_Insert(C);
        assertEquals(C,test.GetNodeAtIndex(2));   
    }
    @Test
    public void btreeInsertToFillRoot()
    {   
        String expString1 = "Node A";
        String expString2 = "Node B";
        String expString3 = "Node C";
        //using degree 2
        BTree test = new BTree(2);
        long A = 1;
        long B = 2;
        long C = 3;
        test.BTree_Insert(A);
        test.BTree_Insert(B);
        test.BTree_Insert(C);
        assertEquals(C,test.GetNodeAtIndex(2));
    } 

    @Test
    public void btree_ABCDE_insertF_ABCDEF()
    {   
        //using degree 2
        BTree test = new BTree(2);
        long A = 1;
        long B = 2;
        long C = 3;
        long D = 4;
        long E = 5;
        long F = 6;
        test.BTree_Insert(A);
        test.BTree_Insert(B);
        test.BTree_Insert(C);
        test.BTree_Insert(D);
        test.BTree_Insert(E);
        test.BTree_Insert(F);
        //Node at 6 should be F
        assert(test.GetNodeAtIndex(6).equals(F));
    } 

    @Test
    public void btree_ABCDEF_insertGH_ABCDEFGH()
    {   
        //using degree 2
        BTree test = new BTree(2);
        long A = 1;
        long B = 2;
        long C = 3;
        long D = 4;
        long E = 5;
        long F = 6;
        long G = 7;
        long H = 8;
        test.BTree_Insert(A);
        test.BTree_Insert(B);
        test.BTree_Insert(C);
        test.BTree_Insert(D);
        test.BTree_Insert(E);
        test.BTree_Insert(F);
        test.BTree_Insert(G);
        test.BTree_Insert(H);
        //Node at 8 should be H, 7 should be G
        assert(test.GetNodeAtIndex(8).equals(H)&&test.GetNodeAtIndex(7).equals(G));
    } 

    

    @Test
    public void btree_ABCDEFGH_insertIJK_ABCDEFGHIJK()
    {   
        //using degree 2
        BTree test = new BTree(2);
        long A = 1;
        long B = 2;
        long C = 3;
        long D = 4;
        long E = 5;
        long F = 6;
        long G = 7;
        long H = 8;
        long I = 9;
        long J = 10;
        long K = 11;
        test.BTree_Insert(A);
        test.BTree_Insert(B);
        test.BTree_Insert(C);
        test.BTree_Insert(D);
        test.BTree_Insert(E);
        test.BTree_Insert(F);
        test.BTree_Insert(G);
        test.BTree_Insert(H);
        test.BTree_Insert(I);
        test.BTree_Insert(J);
        test.BTree_Insert(K);
        //Node at 8 should be H, 7 should be G
        assert(test.GetNodeAtIndex(9).equals(I)&&test.GetNodeAtIndex(10).equals(J)
        &&test.GetNodeAtIndex(11).equals(K));
    } 

    @Test

    public void btreeDegree4Test()
    {
//        //TODO instantiate and populate a bTree object
//        int expectedNumberOfNodes = TBD;
//
//        // it is expected that these nodes values will appear in the tree when
//        // using a level traversal (i.e., root, then level 1 from left to right, then
//        // level 2 from left to right, etc.)
//        String[] expectedNodesContent = new String[]{
//                "TBD, TBD",      //root content
//                "TBD",           //first child of root content
//                "TBD, TBD, TBD", //second child of root content
//        };
//
//        assertEquals(expectedNumberOfNodes, bTree.getNumberOfNodes());
//        for (int indexNode = 0; indexNode < expectedNumberOfNodes; indexNode++)
//        {
//            // root has indexNode=0,
//            // first child of root has indexNode=1,
//            // second child of root has indexNode=2, and so on.
//            assertEquals(expectedNodesContent[indexNode], bTree.getArrayOfNodeContentsForNodeIndex(indexNode).toString());
//        }
    }

}

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
        //Node should now be full and have objects [A B C] in that order
        assert(test.GetNodeAtIndex(1)==expString1&&test.GetNodeAtIndex(2)==expString2
        && test.GetNodeAtIndex(3)==expString3);
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

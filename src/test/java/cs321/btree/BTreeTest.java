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
        //using degree 2
        BTreeNode test = new BTreeNode();
        TreeObject A = new TreeObject(DNA);
        TreeObject B = new TreeObject(DNA);
        TreeObject C = new TreeObject(DNA);

        test.BTree_Insert(A);
        test.BTree_Insert(B);
        test.BTree_Insert(c);
        //Node should now be full and should return a size (?) of 3
        if(test.getSize()==3)
        {
            assert(true);
        }
        else
        {
            assert(false);
        }


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

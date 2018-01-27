package mhmht;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MHMHTTest 
    extends TestCase
{
    public MHMHTTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( MHMHTTest.class );
    }

    public void test1()
    {
        MHMHT tree = new MHMHT();
        tree.Insert(1);
    }
    
    public void test2()
    {
        MHMHT tree = new MHMHT();
        tree.Insert(1);
        tree.Insert(2);
        assertEquals(3, tree.NumNodes());
        assertEquals(2, tree.NumItems());
    }
    
    public void testAllOps()
    {
        MHMHT tree = new MHMHT();
        
        for (int p = 0; p <= 10; ++p) {
        	int low = (int)Math.pow(2, p);
        	int high = (int)Math.pow(2, p + 1);
        	for (int i = low; i < high; ++ i) {
        		tree.Insert(i);
        		assertEquals(high - 1, tree.NumNodes());
        		assertEquals(i, tree.NumItems());
        	}
        }
        
        int high = (int)Math.pow(2,  11);
        for (int i = 1; i < high; ++ i) { assertTrue(tree.Lookup(i)); }
        
        for (int i = 1; i < high; ++ i) {
        	assertTrue(tree.Remove(i));
        	assertEquals(high - i - 1, tree.NumItems());
        	assertFalse(tree.Lookup(i));
        }
    }
    
    public void test100()
    {
        MHMHT tree = new MHMHT();
        for (int i = 1; i < 100; ++ i) {
        	tree.Insert(i);
        }
    }
    
    public void testPerformance()
    {
        MHMHT tree = new MHMHT();
        long timeStart = System.nanoTime();
        long runStart = timeStart;
        for (int i = 1; i <= 1000; ++ i) {
        	if (i > 1 && i % 10 == 0) {
        		System.err.println("" + i
        						+ " " + ((System.nanoTime() - runStart)/i));
        		timeStart = System.nanoTime();
        	}
          tree.Insert(i);
        }        
    }
}

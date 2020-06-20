package bearmaps;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    @Test
    public void testAdd() {
        ArrayHeapMinPQ<Integer> p = new ArrayHeapMinPQ<>();
        p.add(1, 1);
        p.add(8, 8);
        p.add(6, 6);
        p.add(2, 2);
        p.add(10, 10);
        p.add(3, 3);
        p.add(11, 11);

        Assert.assertEquals(p.size(), 7);
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> p = new ArrayHeapMinPQ<>();
        p.add(1, 1);
        p.add(8, 8);
        p.add(6, 6);
        p.add(2, 2);
        p.add(10, 10);
        p.add(3, 3);

        int i = p.getSmallest();

        Assert.assertEquals(i, 1);

        i = p.removeSmallest();

        Assert.assertEquals(i, 1);

        i = p.getSmallest();
        Assert.assertEquals(i, 2);

        Assert.assertEquals(p.size(), 5);
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add("hi", 12);
        test.add("him", 10);
        test.add("this", 1);
        test.add("that", 0);
        test.add("hits", 3);
        assertEquals(5, test.size());
        assertEquals("that", test.getSmallest());
        assertEquals("that", test.removeSmallest());
        assertEquals(4, test.size());
        assertEquals("this", test.getSmallest());
        test.changePriority("hi", 0);
        assertEquals("hi", test.removeSmallest());
        assertEquals("this", test.getSmallest());
        assertEquals(3, test.size());
        test.changePriority("this", 10);
        assertEquals("hits", test.getSmallest());

        NaiveMinPQ testNaive = new NaiveMinPQ();
        testNaive.add("hi", 12);
        testNaive.add("him", 10);
        testNaive.add("this", 1);
        testNaive.add("that", 0);
        testNaive.add("hits", 3);
        assertEquals(5, testNaive.size());
        assertEquals("that", testNaive.getSmallest());
        assertEquals("that", testNaive.removeSmallest());
        assertEquals(4, testNaive.size());
        assertEquals("this", testNaive.getSmallest());
    }

    @Test
    public void testTest() {
        ArrayHeapMinPQ test3 = new ArrayHeapMinPQ();
        test3.add("hi", 12);
        test3.add("him", 12);
        test3.add("this", 12);
        test3.add("that", 10);
        test3.add("hits", 9);
        test3.add("bit", 11.5);
    }
}

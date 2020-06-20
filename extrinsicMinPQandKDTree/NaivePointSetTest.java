package bearmaps;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class NaivePointSetTest {
    @Test
    public void testAdd() {
        Point p1 = new Point(-1, -1);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(0, 1);
        Point p4 = new Point(1, 0);
        Point p5 = new Point(-2, -2);
        Point p6 = new Point(-3, 2.5);

        List<Point> pts = new LinkedList<>();
        pts = List.of(p1, p2, p3, p4, p5, p6);
        NaivePointSet t = new NaivePointSet(pts);

        Point n = t.nearest(-1, 2);
        Assert.assertEquals(n, new Point(0, 1));
    }
}

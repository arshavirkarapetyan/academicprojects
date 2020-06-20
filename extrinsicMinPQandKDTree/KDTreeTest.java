package bearmaps;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class KDTreeTest {
    @Test
    public void testAdd() {
        Point p1 = new Point(-1, -1);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(0, 1);
        Point p4 = new Point(1, 0);
        Point p5 = new Point(-2, -2);
        Point p6 = new Point(-3, 2.5);

        List<Point> pts = new LinkedList<>();
        pts.add(p1);
        pts.add(p2);
        pts.add(p3);
        pts.add(p4);
        pts.add(p5);
        pts.add(p6);

        KDTree t = new KDTree(pts);
        Point n = t.nearest(-1, 2);
        Assert.assertEquals(n, new Point(0, 1));

        Point a = new Point(-1, -1);
        Point b = new Point(2, 2);
        Point c = new Point(-1, -1);
        Point d = new Point(0, 1);
        Point e = new Point(-1, 2);
        Point f = new Point(-1, -1);

        List<Point> ps = new LinkedList<>();
        ps.add(a);
        ps.add(b);
        ps.add(c);
        ps.add(d);
        ps.add(e);
        ps.add(f);

        KDTree t1 = new KDTree(ps);
        Point n1 = t1.nearest(-1, 2);
        Assert.assertEquals(n1, new Point(-1, 2));
    }
}

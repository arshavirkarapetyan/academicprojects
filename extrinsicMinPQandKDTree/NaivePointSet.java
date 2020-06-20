package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    List<Point> points;

    public NaivePointSet() {

    }

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    public Point nearest(double x, double y) {
        double distance = 0;
        Point p = null;
        for (Point i : points) {
            if (distance == 0) {
                p = i;
                distance = Point.distance(i, new Point(x, y));
            } else if (distance > Point.distance(i, new Point(x, y))) {
                p = i;
                distance = Point.distance(i, new Point(x, y));
            }
        }

        return p;
    }
}

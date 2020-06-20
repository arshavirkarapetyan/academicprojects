package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private Node root;
    private static final boolean HORIZONTAL = false;

    private class Node {
        private Point point;
        private Node leftChild;
        private Node rightChild;

        private Node(Point p, Node left, Node right) {
            point = p;
            leftChild = left;
            rightChild = right;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point p = new Point(0, 0);
        return nearestHelper(x, y, Double.POSITIVE_INFINITY, root, HORIZONTAL, p);
    }

    public Point nearestHelper(double x, double y, double best, Node n, boolean o, Point p) {
        if (n == null) {
            return p;
        }

        if (best > Point.distance(n.point, new Point(x, y))) {
            p = n.point;
            best = Point.distance(p, new Point(x, y));
        }

        if (comparePoints(n.point, new Point(x, y), o) > 0) {
            p = nearestHelper(x, y, best, n.leftChild, !o, p);
            best = Point.distance(p, new Point(x, y));

            if (o == HORIZONTAL) {
                if (best > Math.pow(x - n.point.getX(), 2)) {
                    p = nearestHelper(x, y, best, n.rightChild, !o, p);
                    best = Point.distance(p, new Point(x, y));
                }
            } else {
                if (best > Math.pow(y - n.point.getY(), 2)) {
                    p = nearestHelper(x, y, best, n.rightChild, !o, p);
                    best = Point.distance(p, new Point(x, y));
                }
            }
        } else {
            p = nearestHelper(x, y, best, n.rightChild, !o, p);
            best = Point.distance(p, new Point(x, y));

            if (o == HORIZONTAL) {
                if (best > Math.pow(x - n.point.getX(), 2)) {
                    p = nearestHelper(x, y, best, n.leftChild, !o, p);
                    best = Point.distance(p, new Point(x, y));
                }
            } else {
                if (best > Math.pow(y - n.point.getY(), 2)) {
                    p = nearestHelper(x, y, best, n.leftChild, !o, p);
                    best = Point.distance(p, new Point(x, y));
                }
            }
        }

        return p;
    }

    private Node add(Point p, Node n, boolean orientation) {
        if (n == null) {
            return new Node(p, null, null);
        }

        if (comparePoints(n.point, p, orientation) > 0) {
            return new Node(n.point, add(p, n.leftChild, !orientation), n.rightChild);
        } else {
            return new Node(n.point, n.leftChild, add(p, n.rightChild, !orientation));
        }
    }

    private static int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(a.getX(), b.getX());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }
}

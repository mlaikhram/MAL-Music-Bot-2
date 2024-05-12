package util;
import java.util.Comparator;
import java.util.TreeSet;

public class IntSegmentList {

    private class Point {
        public int value;
        public boolean isStartPoint;

        public Point(int value, boolean isStartPoint) {
            this.value = value;
            this.isStartPoint = isStartPoint;
        }

        public Point(int value) {
            this(value, true);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "value=" + value +
                    ", isStartPoint=" + isStartPoint +
                    '}';
        }
    }

    private final TreeSet<Point> pointList;


    public IntSegmentList() {
        pointList = new TreeSet<>(Comparator.comparingInt(p -> p.value));
    }

    public IntSegmentList(String segmentString) {
        this();

        String[] segments = segmentString.split(",");
        for (String segment : segments) {
            String[] points = segment.strip().split("-", -2);
            if (points.length == 1) {
                addPoint(Integer.parseInt(points[0]));
            }
            else {
                addSegment(Integer.parseInt(points[0]), points[points.length - 1].strip().isEmpty() ? Integer.MAX_VALUE - 1 : Integer.parseInt(points[points.length - 1]));
            }
        }
    }

    public void addSegment(int startInclusive, int endInclusive) {
        Point startPoint = new Point(startInclusive);
        Point endPoint = new Point(endInclusive);

        Point upperBound = pointList.higher(endPoint);
        if (containsPoint(endPoint)) {
            endPoint = upperBound;
        }
        else if (upperBound != null && upperBound.value == endPoint.value + 1) {
            endPoint = pointList.higher(upperBound);
        }
        else {
            endPoint = new Point(endInclusive + 1, false);
            pointList.add(endPoint);
        }

        Point lowerBound = pointList.floor(startPoint);
        if (containsPoint(startPoint)) {
            startPoint = lowerBound;
        }
        else if (lowerBound != null && lowerBound.value == startPoint.value) {
            startPoint = pointList.lower(lowerBound);
        }
        else {
            pointList.add(startPoint);
        }

        pointList.removeAll(pointList.subSet(startPoint, false, endPoint, false));
    }

    public void addPoint(int point) {
        addSegment(point, point);
    }

    public boolean containsPoint(Point point) {
        Point floor = pointList.floor(point);

        return floor != null && floor.isStartPoint;
    }

    public boolean containsPoint(int target) {
        return containsPoint(new Point(target));
    }

    public int getMinPoint() {
        return pointList.first().value;
    }

    @Override
    public String toString() {
        return "IntSegmentList{" +
                "pointList=" + pointList +
                '}';
    }
}

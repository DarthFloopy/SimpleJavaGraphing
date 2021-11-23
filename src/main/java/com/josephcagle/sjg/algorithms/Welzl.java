
package com.josephcagle.sjg.algorithms;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.josephcagle.sjg.Circle;
import com.josephcagle.sjg.Point;

public class Welzl {

    private static final Random random = ThreadLocalRandom.current();



    public static Circle welzl(List<Point> points) {
        return welzlHelper(points, Collections.<Point>emptyList());
    }

    public static Circle welzlHelper(List<Point> points, List<Point> boundaryPoints) {
        if (points.isEmpty() || boundaryPoints.size() == 3)
            return trivial(boundaryPoints);

        Point p = points.get(random.nextInt(points.size()));
        Circle d = welzlHelper(
            listMinus(points, p),
            boundaryPoints
        );
        if (d.containsPoint(p))
            return d;

        return welzlHelper(
            listMinus(points, p),
            union(boundaryPoints, List.of(p))
        );
    }

    private static Circle trivial(List<Point> points) {
        if (points.isEmpty()) {
            return new Circle(new Point(0,0), 0);
        }

        int size = points.size();

        if (size == 1)
            return new Circle(points.get(0), 0);

        if (size == 2)
            return Circle.fromDiameterPoints(points.get(0), points.get(1));

        if (size == 3)
            return Circle.fromThreePoints(points.get(0), points.get(1), points.get(2));

        throw new IllegalArgumentException("list must have 1-3 points");
    }

    private static <T> List<T> listMinus(List<T> list, T toRemove) {
        return list.stream().filter(item -> item != toRemove).collect(toList());
    }

    private static <T> List<T> union(List<T> list1, List<T> list2) {
        List<T> container = new ArrayList<>(list1.size() + list2.size());
        container.addAll(list1);
        container.addAll(list2);
        return container;
    }

}

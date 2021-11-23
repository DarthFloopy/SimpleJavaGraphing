
package com.josephcagle.sjg.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;

import com.josephcagle.sjg.Circle;
import com.josephcagle.sjg.Drawable;
import com.josephcagle.sjg.GraphFrame;
import com.josephcagle.sjg.Point;

class RandomBlockingWelzl {
    static GraphFrame frame = new GraphFrame(1000, 1000);
    static Random random = ThreadLocalRandom.current();
    static List<Drawable> thingsToDraw = new LinkedList<>();
    static Point[] allPoints = new Point[8];

    public static void main(String[] args) {
        generateThings();
    }

    private static void generateThings() {
        for (int i=0; i<8; i++)
            allPoints[i] = new Point(random.nextInt(500)+250, random.nextInt(500)+250);

        int a = random.nextInt(allPoints.length), b = 0, c = 0;
        do b = random.nextInt(allPoints.length); while (a == b);
        do c = random.nextInt(allPoints.length); while (a == c && b == c);

        thingsToDraw.addAll(List.of(allPoints));
        frame.render(thingsToDraw);
        frame.blockUntilClick();

        thingsToDraw.add(blockingWelzl(List.of(allPoints)));
        frame.render(thingsToDraw);
        frame.displayMessage("done");

    }

    private static void blockShowing(List<Point> points, Circle circle) {
        thingsToDraw.clear();
        thingsToDraw.addAll(points);
        thingsToDraw.add(circle);
        frame.render(thingsToDraw);
        frame.blockUntilClick();
    }

    public static Circle blockingWelzl(List<Point> points) {
        return blockingWelzlHelper(points, Collections.<Point>emptyList());
    }

    public static Circle blockingWelzlHelper(List<Point> points, List<Point> boundaryPoints) {

        if (points.isEmpty() || boundaryPoints.size() == 3) {
            Circle trivial = trivial(boundaryPoints);
            blockShowing(boundaryPoints, trivial);
            frame.displayMessage("trivial");
            return trivial;
        }

        Point p = points.get(random.nextInt(points.size()));
        Circle d = blockingWelzlHelper(
            listMinus(points, p),
            boundaryPoints
        );
        if (d.containsPoint(p)) {
            blockShowing(union(points,boundaryPoints), d);
            frame.displayMessage("d");
            return d;
        }

        return blockingWelzlHelper(
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


package com.josephcagle.sjg.algorithms;

import java.util.*;

import com.josephcagle.sjg.*;

public class Solver {

    // This doesn't always work
    public static List<Point> expansion(List<Point> points) {
        if (points.size() < 3) {
            return points;
        }

        List<Point> bestPoints = new ArrayList<>(Arrays.asList(points.get(0), points.get(1), points.get(2)));

        for (int i = 3; i < points.size(); i++) {
            bestPoints.add(points.get(i));
            bestPoints = bruteForce(bestPoints);
        }
        
        return bestPoints;
    }

    public static List<Point> bruteForce(List<Point> points) {
        if (points.size() < 3) {
            return points;
        }

        Point p0, p1, p2;
        List<Point> bestPoints = new ArrayList<Point>();
        double bestRadius = 0;
        
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                p0 = points.get(i);
                p1 = points.get(j);
                double radius = p0.getDistance(p1) / 2;
                if (radius > bestRadius) {
                    bestRadius = radius;
                    bestPoints = new ArrayList<>(Arrays.asList(p0, p1));
                }
            }
        }

        if (Circle.fromDiameterPoints(bestPoints.get(0), bestPoints.get(1)).containsPoints(points)) {
            return bestPoints;
        }

        bestRadius *= 2;

        for (int i = 0; i < points.size() - 2; i++) {
            for (int j = i + 1; j < points.size() - 1; j++) {
                for (int k = j + 1; k < points.size(); k++) {
                    p0 = points.get(i);
                    p1 = points.get(j);
                    p2 = points.get(k);
                    Circle circle = Circle.fromThreePoints(p0, p1, p2);
                    if (circle.containsPoints(points) && circle.radius() < bestRadius) {
                        bestRadius = circle.radius();
                        bestPoints = new ArrayList<>(Arrays.asList(p0, p1, p2));
                    }
                }
            }
        }

        return bestPoints;
    }
}

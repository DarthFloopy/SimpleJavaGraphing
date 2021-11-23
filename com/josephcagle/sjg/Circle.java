
package com.josephcagle.sjg;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

import java.util.List;

public record Circle(Point center, double radius) implements Drawable {

    public Point topLeft() { return center().translate(new Vector(-radius(), -radius())); }

    // Mostly copied from https://stackoverflow.com/a/4103418/10409447
    private static final double TOLERANCE = 1.0e-7;

    public static Circle fromThreePoints(Point ...points) {
        if (points.length != 3) throw new IllegalArgumentException("must give 3 args");
        final Point p1 = points[0], p2 = points[1], p3 = points[2];

        if (p1.equals(p2)) return Circle.fromDiameterPoints(p1, p3);
        if (p1.equals(p3)) return Circle.fromDiameterPoints(p1, p2);
        if (p2.equals(p3)) return Circle.fromDiameterPoints(p1, p2);

        final double offset = Math.pow(p2.x(),2) + Math.pow(p2.y(),2);
        final double bc =   ( Math.pow(p1.x(),2) + Math.pow(p1.y(),2) - offset )/2.0;
        final double cd =   (offset - Math.pow(p3.x(),2) - Math.pow(p3.y(),2))/2.0;
        final double det =  (p1.x() - p2.x()) * (p2.y() - p3.y()) - (p2.x() - p3.x()) * (p1.y() - p2.y());

        if (Math.abs(det) < TOLERANCE) { throw new IllegalArgumentException("det is ~0"); }

        final double idet = 1/det;

        final double centerX =  (bc * (p2.y() - p3.y()) - cd * (p1.y() - p2.y())) * idet;
        final double centerY =  (cd * (p1.x() - p2.x()) - bc * (p2.x() - p3.x())) * idet;
        final double radius = Math.sqrt( Math.pow(p2.x()-centerX,2) + Math.pow(p2.y()-centerY,2));

        return new Circle(new Point(centerX,centerY),radius);
    }

    public boolean containsPoint(Point point) {
        return Math.hypot(point.x() - center.x(), point.y() - center.y()) <= radius;
    }

    public boolean containsPoints(List<Point> points) {
        for (Point point : points) {
            if (!containsPoint(point)) return false;
        }
        return true;
    }

    public static Circle fromDiameterPoints(Point p1, Point p2) {
        return new Circle(p1.getMidpoint(p2), p1.getDistance(p2) / 2);
    }

    private static final float LINE_THICKNESS = 3.0f;

    @Override
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(LINE_THICKNESS));
        g2.setColor(Color.BLUE);
        g2.drawOval(
            (int) this.topLeft().x(),
            (int) this.topLeft().y(),
            (int) (this.radius()*2),
            (int) (this.radius()*2)
        );
    }
}

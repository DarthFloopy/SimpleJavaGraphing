
package com.josephcagle.sjg;

import java.awt.*;

public record Point(double x, double y) implements Drawable {

    public Point translate(Vector vector) {
        return new Point(this.x() + vector.x(), this.y() + vector.y());
    }

    public Point getMidpoint(Point point) {
        return new Point((this.x() + point.x()) / 2, (this.y() + point.y()) / 2);
    }

    public double getDistance(Point point) {
        return Math.hypot(this.x() - point.x(), this.y() - point.y());
    }

    private static final double VISUAL_RADIUS = 5.0;
    @Override
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(Color.BLACK);
        g2.fillOval(
            (int) (this.x()-VISUAL_RADIUS),
            (int) (this.y()-VISUAL_RADIUS),
            (int) VISUAL_RADIUS*2,
            (int) VISUAL_RADIUS*2
        );
    }
}


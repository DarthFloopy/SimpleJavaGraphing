
package com.josephcagle.sjg;

public record Vector(double x, double y) {
    public Vector scale(double factor) {
        return new Vector(this.x()*factor, this.y()*factor);
    }

    public double abs() { return Math.hypot(this.x(), this.y()); }
}


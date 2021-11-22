
package com.josephcagle.sjg;

public record Vector(double x, double y) {
    public Vector times(double factor) {
        return new Vector(this.x()*factor, this.y()*factor);
    }
}


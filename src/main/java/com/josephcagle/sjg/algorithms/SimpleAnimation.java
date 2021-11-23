
package com.josephcagle.sjg.algorithms;

import com.josephcagle.sjg.*;
import java.util.*;

class SimpleAnimation {
    public static void main(String[] args) {
        GraphFrame frame = new GraphFrame(800, 800);
        List<Drawable> thingsToDraw = new LinkedList<>();

        Point[] circlePoints = {
            new Point(200,000),
            new Point(150,500),
            new Point(600,600)
        };
        thingsToDraw.add(Circle.fromThreePoints(circlePoints));
        thingsToDraw.add(new Point(400, 400));

        frame.render(thingsToDraw);
    }
}

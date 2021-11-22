
package com.josephcagle.sjg.algorithms;

import com.josephcagle.sjg.*;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

class RandomDrawing {
    static GraphFrame frame = new GraphFrame(1000, 1000);
    static Random random = ThreadLocalRandom.current();

    public static void main(String[] args) {
        generateThings();

        frame.addMouseListener(new MouseInputAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                generateThings();
            }
        });
    }

    private static void generateThings() {
        List<Drawable> thingsToDraw = new LinkedList<>();

        Point[] points = new Point[8];
        for (int i=0; i<8; i++)
            points[i] = new Point(random.nextInt(1000), random.nextInt(1000));

        int a = random.nextInt(points.length), b = 0, c = 0;
        do b = random.nextInt(points.length); while (a == b);
        do c = random.nextInt(points.length); while (a == c && b == c);

        thingsToDraw.addAll(List.of(points));

        thingsToDraw.add(Circle.fromThreePoints(points[a], points[b], points[c]));

        frame.render(thingsToDraw);

    }
}


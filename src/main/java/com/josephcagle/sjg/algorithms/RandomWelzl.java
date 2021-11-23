
package com.josephcagle.sjg.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.josephcagle.sjg.Drawable;
import com.josephcagle.sjg.GraphFrame;
import com.josephcagle.sjg.Point;

class RandomWelzl {
    static GraphFrame frame = new GraphFrame(1000, 1000);
    static Random random = ThreadLocalRandom.current();

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            do {
                generateThings();
                frame.blockUntilClick();
            } while (true);
        });
        t.setDaemon(true);
        t.start();
    }

    private static void generateThings() {
        List<Drawable> thingsToDraw = new LinkedList<>();

        Point[] points = new Point[8];
        for (int i=0; i<8; i++)
            points[i] = new Point(random.nextInt(500)+250, random.nextInt(500)+250);

        int a = random.nextInt(points.length), b = 0, c = 0;
        do b = random.nextInt(points.length); while (a == b);
        do c = random.nextInt(points.length); while (a == c && b == c);

        thingsToDraw.addAll(List.of(points));

        thingsToDraw.add(Welzl.welzl(List.of(points), List.of()));

        frame.render(thingsToDraw);

    }
}


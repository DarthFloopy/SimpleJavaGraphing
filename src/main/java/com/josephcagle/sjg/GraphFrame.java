
package com.josephcagle.sjg;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * GraphFrame
 */
public class GraphFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private List<Drawable> drawables = java.util.Collections.emptyList();
    private String message = "";
    private GraphPanel panel;

    private List<Consumer<MouseEvent>> clickListeners = new LinkedList<>();

    private CountDownLatch clickSignal;

    public GraphFrame(int width, int height) {
        this.add(this.panel = new GraphPanel(width, height));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.clickSignal = new CountDownLatch(1);
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (Consumer<MouseEvent> clickListener : clickListeners)
                    clickListener.accept(e);

                clickSignal.countDown();
                clickSignal = new CountDownLatch(1);
            }
        });

        this.setVisible(true);
    }

    public void render(List<Drawable> drawables) {
        this.drawables = drawables;
        this.panel.repaint();
    }

    public void displayMessage(String message) {
        this.message = message;
    }

    public void addClickListener(Consumer<MouseEvent> clickListener) {
        this.clickListeners.add(clickListener);
    }

    public void blockUntilClick() {
        try {
            GraphFrame.this.clickSignal.await();
        } catch (InterruptedException ignore) { }
    }

    private class GraphPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        GraphPanel(int width, int height) {
            this.setPreferredSize(new Dimension(width, height));
        }

        @Override
        public void paint(Graphics g) {
            if (g instanceof Graphics2D g2) {
                // g2.setRenderingHints(new RenderingHints(
                //     RenderingHints.KEY_TEXT_ANTIALIASING,
                //     RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                // ));

                super.paintComponent(g);
                for (Drawable d : GraphFrame.this.drawables)
                    d.draw(g2);

                if (! GraphFrame.this.message.isBlank()) {
                    g2.setColor(Color.BLACK);
                    g2.drawString(GraphFrame.this.message, 20, 20);
                }
            }
            else
                throw new RuntimeException("needs Graphics2D instance");
        }
    }
}

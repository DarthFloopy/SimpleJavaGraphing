
import java.awt.*;
import javax.swing.*;
import java.util.List;

/**
 * GraphFrame
 */
public class GraphFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private List<Drawable> drawables = java.util.Collections.emptyList();
    private GraphPanel panel;

    public GraphFrame(int width, int height) {
        this.add(this.panel = new GraphPanel(width, height));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void render(List<Drawable> drawables) {
        this.drawables = drawables;
        this.panel.repaint();
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
            }
            else
                throw new RuntimeException("needs Graphics2D instance");
        }
    }
}

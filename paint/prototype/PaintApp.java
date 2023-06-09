package idv.jingshing.pixel.filter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JColorChooser;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PaintApp {
	// Main method to start the application
	public static void main(String[] args) {
	    new PaintApp();
	}

	// Constructor for the PaintApp class
	public PaintApp() {
	    // Run the Swing components on the Event Dispatch Thread
	    EventQueue.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	            try {
	                // Set the look and feel to the system's default
	                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	            }

	            // Create the main frame for the application
	            JFrame frame = new JFrame("Testing");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setLayout(new BorderLayout());
	            frame.add(new TestPane());
//	            frame.pack();
	            frame.setSize(900, 900);
	            frame.setLocationRelativeTo(null);
	            frame.setVisible(true);
	        }
	    });
	}

	// Class for the main panel of the application
	public class TestPane extends JPanel {

	    private PaintPane paintPane;

	    public TestPane() {
	        setLayout(new BorderLayout());
	        // Create the paint panel and add it to the main panel
	        add((paintPane = new PaintPane()));
	        // Add a panel with color selection buttons below the paint panel
	        add(new ColorsPane(paintPane), BorderLayout.SOUTH);
	    }
	}

	// Class for the panel containing the color selection buttons
	public class ColorsPane extends JPanel {
		JButton paletteButton;
	    public ColorsPane(PaintPane paintPane) {
	        // Create a button for each color and add it to the panel
	    	paletteButton = new JButton("Pick a color");
	    	paletteButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
		            paintPane.setForeground(JColorChooser.showDialog(null, "pick a color", Color.black));
	            }
	        });
	    	add(paletteButton);
	    }
	}

	// Class for the panel used for painting
	public class PaintPane extends JPanel {

	    private BufferedImage background;

	    public PaintPane() {
//	        setBackground(Color.WHITE);
	        setForeground(Color.BLACK);
	        try {
	        	File file = new File("image/or.png");
	        	background = ImageIO.read(file);
	         } catch (IOException e) {
	            System.out.println("Error occurred when reading image.");
	         }
	        // Add a mouse listener for drawing dots
	        MouseAdapter handler = new MouseAdapter() {

	            @Override
	            public void mousePressed(MouseEvent e) {
	                drawDot(e.getPoint());
	            }

	            @Override
	            public void mouseDragged(MouseEvent e) {
	                drawDot(e.getPoint());
	            }

	        };
	        addMouseListener(handler);
	        addMouseMotionListener(handler);
	    }

	    // Method for drawing a dot on the panel
	    protected void drawDot(Point p) {
            if (background == null) {
                updateBuffer();;
            }

            if (background != null) {
                Graphics2D g2d = background.createGraphics();
                g2d.setColor(getForeground());
                g2d.fillOval(p.x - 5, p.y - 5, 10, 10);
                g2d.dispose();
            }
            repaint();
        }

        @Override
        public void invalidate() {
            super.invalidate();
            updateBuffer();
        }

        protected void updateBuffer() {

            if (getWidth() > 0 && getHeight() > 0) {
                BufferedImage newBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = newBuffer.createGraphics();
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                if (background != null) {
                    g2d.drawImage(background, 0, 0, this);
                }
                g2d.dispose();
                background = newBuffer;
            }

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            if (background == null) {
                updateBuffer();
            }
            g2d.drawImage(background, 0, 0, this);
            g2d.dispose();
        }
    }
}

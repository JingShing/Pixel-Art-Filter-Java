package idv.jingshing.pixel.filter;
import java.awt.*;
import javax.swing.*;
import org.opencv.core.Mat;
import org.opencv.core.Core;
import java.util.ArrayList;
import org.opencv.core.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.CvType;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JColorChooser;
public class DrawGUI extends JFrame {

    private PaintPane label;
    private JButton saveButton;
    private JButton rebackButton;
    private JButton paletteButton;
    private JPanel button;
    
    private ArrayList<Mat> img = new ArrayList<>();
    public Mat getNowImg() {
        if(img.size()==0) return null;
        return img.get(img.size()-1);
    }
    public Mat backReturn() {
        if(img.size()==0) return null;
        img.remove(img.size()-1);
        return getNowImg();
    }
    public void setImg(Mat input) {
        if(img.size()!=0) return;
        img.add(input); return;
    }
    public DrawGUI(String filePath) {
    	super("Painter");
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Imgcodecs.imread(filePath);
        ImageIcon iconImage = new ImageIcon("src/icon/icon.png");// create image icon
		setIconImage(iconImage.getImage());
        setImg(image);
        JLabel imageSize = new JLabel();
        Image image2 = matToBufferedImage(image);
        imageSize.setIcon(new ImageIcon(image2));
        // create label for image preview
        label = new PaintPane();
        displayImage(image);
        label.setPreferredSize(new Dimension(image.width(), image.height()));
        label.setSize(image.width(), image.height());
        setSize(image.width(), image.height());
        // create text area and save button
        saveButton = new JButton("Save");
        rebackButton = new JButton("返回");
        
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(DrawGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    saveImg(getNowImg(), selectedDirectory.getPath() + File.separator +"output.png");
                }
            }
        });
        rebackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.stop();
                displayImage(backReturn());
                label.start();
            }
        });
        
        button = new JPanel(new GridLayout(1, 3));
        paletteButton = new JButton("Pick a color");
    	paletteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
	            label.setForeground(JColorChooser.showDialog(null, "pick a color", Color.black));
            }
        });
        button.add(saveButton);
        button.add(rebackButton);
        button.add(paletteButton);
        // TODO: add listener to save button
        
        // add components to frame

        add(label, BorderLayout.CENTER);
        add(imageSize, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);    
        pack();
    }
    public class PaintPane extends JPanel {

        private BufferedImage background;
        public void setBackground(Mat img) {
            background = matToBufferedImage(img);
            try{
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateBuffer();
            try{
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
        public void stop() {
            removeMouseListener(handler);
            removeMouseMotionListener(handler);
        }
        public void start() {
            addMouseListener(handler);
            addMouseMotionListener(handler);
        }
        MouseAdapter handler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                drawDot(new Point(e.getX(), e.getY()));
            }
        
            @Override
            public void mouseDragged(MouseEvent e) {
                drawDot(new Point(e.getX(), e.getY()));
            }
        
            @Override
            public void mouseReleased(MouseEvent e) {
                drawDot(new Point(e.getX(), e.getY()));
                img.add(bufferedImageToMat(background));
            } 
        };
        public PaintPane() {
            //	        setBackground(Color.WHITE);
            setForeground(Color.BLACK);
            background = matToBufferedImage(getNowImg());
            // Add a mouse listener for drawing dots
            addMouseListener(handler);
            addMouseMotionListener(handler);
        }

        // Method for drawing a dot on the panel
        protected void drawDot(Point p) {
            if (background == null) {
                updateBuffer();
            }

            if (background != null) {
                Graphics2D g2d = background.createGraphics();
                g2d.setColor(getForeground());
                g2d.fillOval((int)p.x, (int)p.y, 10, 10);
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
                BufferedImage newBuffer = matToBufferedImage(getNowImg());
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
    private void displayImage(Mat img) {
        if(img == null) return;
        label.setBackground(img);
    }
    private static BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage img = new BufferedImage(mat.cols(), mat.rows(), type);
        mat.get(0, 0, ((DataBufferByte) img.getRaster().getDataBuffer()).getData());
        return img;
    }
    private static Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
    
        int channels = bi.getRaster().getNumDataElements();
        if (channels == 1) {
            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2BGR);
        }
        return mat;
    } 
    public static void saveImg(Mat image, String fileName) {
        try {
            File outputfile = new File(fileName);
            ImageIO.write(matToBufferedImage(image), "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        DrawGUI check = new DrawGUI("image/or.jpg");
        check.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		check.setVisible(true); // display frame
    }
}


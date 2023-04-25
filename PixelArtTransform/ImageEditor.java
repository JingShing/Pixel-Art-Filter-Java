package idv.jingshing.pixel.filter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import org.opencv.imgcodecs.Imgcodecs;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageEditor extends JFrame {
    private Filters allImg = new Filters();
    private JLabel imageLabel;
    private JButton backButton;
    private JButton kuwaharaButton;
    private JComboBox<String> scaleComboBox;
    private JButton flameButton;
    private JButton highGaussianButton;
    private JButton unknownButton;
    private JButton loadButton;
    private JButton saveButton;

    public static void main(String[] args) {
        ImageEditor check = new ImageEditor();
        check.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		check.setSize(400, 200); // set frame size
		check.setVisible(true); // display frame
    }
	public ImageEditor() {
		init();	
	}
    public void init() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // set title
        setTitle("Image Editor");
        // set window size
        setSize(800, 600);
        // set close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // GUI component
        backButton = new JButton("返回");
        kuwaharaButton = new JButton("Kuwahara");
        scaleComboBox = new JComboBox<String>(new String[] { "1", "2", "3", "4" });
        flameButton = new JButton("Flame");
        highGaussianButton = new JButton("High Gaussian");
        unknownButton = new JButton("Unknown");
        loadButton = new JButton("載入圖片");
        saveButton = new JButton("儲存圖片");
        imageLabel = new JLabel();

        // GUI layout
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1));
        buttonPanel.add(backButton);
        buttonPanel.add(kuwaharaButton);
        buttonPanel.add(scaleComboBox);
        buttonPanel.add(flameButton);
        buttonPanel.add(highGaussianButton);
        buttonPanel.add(unknownButton);
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(loadButton);
        bottomPanel.add(saveButton);

        // add GUI component to window
        add(buttonPanel, BorderLayout.WEST);
        add(imagePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // button listener
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayImage(allImg.backReturn());
            }
        });

        kuwaharaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int scale = Integer.parseInt(scaleComboBox.getSelectedItem().toString());
                displayImage(allImg.kuwahara(allImg.getNowImg(), scale));
            }
        });

        flameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayImage(allImg.Flame(allImg.getNowImg()));
            }
        });

        highGaussianButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayImage(allImg.highGaussian(allImg.getNowImg()));
            }
        });

        unknownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayImage(allImg.unknown(allImg.getNowImg()));
            }
        });
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(ImageEditor.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String imagePath = fileChooser.getSelectedFile().getPath();
                    allImg.setImg(Imgcodecs.imread(imagePath)); // 使用 OpenCV 讀取圖片
                    displayImage(allImg.getNowImg()); // 顯示圖片在 JLabel 上
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(ImageEditor.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    saveImg(allImg.getNowImg(), selectedDirectory.getPath() + File.separator +"output.png");
                }
            }
        });
    }
	private void displayImage(Mat img) {
        if(img == null) return;
        Image image = matToBufferedImage(img);
        imageLabel.setIcon(new ImageIcon(image));
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
    public static void saveImg(Mat image, String fileName) {
        try {
            File outputfile = new File(fileName);
            ImageIO.write(matToBufferedImage(image), "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
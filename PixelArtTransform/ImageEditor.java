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

public class ImageEditor extends JFrame{
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
        ImageEditor check = new ImageEditor("");
        check.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		check.setSize(400, 200); // set frame size
		check.setVisible(true); // display frame
    }
    public ImageEditor(String filePath) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // set title
        setTitle("Filters");
        // set size
        setSize(800, 600);
        // set close action
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon iconImage = new ImageIcon("src/icon/icon.png");// create image icon
		setIconImage(iconImage.getImage());

        // create gui component
        backButton = new JButton("undo");
        kuwaharaButton = new JButton("Kuwahara");
        String[] scaleComboBoxItems = new String[5];
        for (int i = 0; i < scaleComboBoxItems.length; i++) {
            scaleComboBoxItems[i] = String.valueOf(i + 1);
        }
        scaleComboBox = new JComboBox<String>(scaleComboBoxItems);
        // scaleComboBox = new JComboBox<String>(new String[] { "1", "2", "3", "4", "5", "6" });
        flameButton = new JButton("Flame");
        highGaussianButton = new JButton("High Gaussian");
        unknownButton = new JButton("Unknown");
        loadButton = new JButton("Load");
        saveButton = new JButton("save");
        imageLabel = new JLabel();

        // set gui layout
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1));
        buttonPanel.add(backButton);
        JPanel kuwaPanel = new JPanel();
        kuwaPanel.setLayout(new GridLayout(1, 2));
        kuwaPanel.add(kuwaharaButton);
        kuwaPanel.add(scaleComboBox);
        buttonPanel.add(kuwaPanel);
//        buttonPanel.add(kuwaharaButton);
//        buttonPanel.add(scaleComboBox);
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

        // add gui componet to window
        add(buttonPanel, BorderLayout.WEST);
        add(imagePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // set action listener to button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayImage(allImg.backReturn());
            }
        });

        kuwaharaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setButtonActive(false);
                int scale = Integer.parseInt(scaleComboBox.getSelectedItem().toString());
                displayImage(allImg.kuwahara(allImg.getNowImg(), scale));
            }
        });

        flameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setButtonActive(false);
                displayImage(allImg.Flame(allImg.getNowImg()));
            }
        });

        highGaussianButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setButtonActive(false);            	
                displayImage(allImg.highGaussian(allImg.getNowImg()));
            }
        });

        unknownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setButtonActive(false);            	
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
                    pack();
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
        if(filePath=="");
        else {
        	allImg.setImg(Imgcodecs.imread(filePath)); // use opencv load image
            displayImage(allImg.getNowImg()); // display image on JLabel
            pack();
        }
    }
    private void displayImage(Mat img) {
        if(img == null) return;
        Image image = matToBufferedImage(img);
        imageLabel.setIcon(new ImageIcon(image));
        setButtonActive(true);
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
    public void setButtonActive(boolean active) {
        kuwaharaButton.setEnabled(active);
        flameButton.setEnabled(active);
        highGaussianButton.setEnabled(active);
        unknownButton.setEnabled(active);
        loadButton.setEnabled(active);
        saveButton.setEnabled(active);
        backButton.setEnabled(active);
    }
}
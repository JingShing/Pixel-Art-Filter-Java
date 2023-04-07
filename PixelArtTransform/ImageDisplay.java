package idv.jingshing.pixel.fileter;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class ImageDisplay {
	JFrame imageDisplay = new JFrame();
	JLabel imageLabel = new JLabel(new ImageIcon("src/icon/icon.png"));
	ImageDisplay(){
		imageDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imageDisplay.setTitle("Preview");// window title
		imageDisplay.setIconImage(new ImageIcon("src/icon/icon.png").getImage());
		imageDisplay.setSize(500, 500);
		Color bgColor = new Color(0, 0, 0); // black
		imageDisplay.getContentPane().setBackground(bgColor);
		imageDisplay.add(imageLabel);
		imageDisplay.pack();
		imageDisplay.setVisible(true);
	}
	public void setImage(String filePath) {
		imageLabel.setIcon(new ImageIcon(filePath));
		imageDisplay.pack();
	}
}

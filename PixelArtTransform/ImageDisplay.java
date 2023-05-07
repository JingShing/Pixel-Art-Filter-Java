package idv.jingshing.pixel.filter;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class ImageDisplay {
	JFrame imageDisplay = new JFrame();
	JLabel imageLabel = new JLabel(new ImageIcon("src/icon/icon.png"));// set image display frame
	ImageDisplay(){
		imageDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imageDisplay.setTitle("Preview");// window title
		imageDisplay.setIconImage(new ImageIcon("src/icon/icon.png").getImage());// set default icon
		imageDisplay.setSize(500, 500);// set image display window size
		Color bgColor = new Color(0, 0, 0); // black
		imageDisplay.getContentPane().setBackground(bgColor);
		imageDisplay.add(imageLabel);
		imageDisplay.pack();// make size suitable in image
		imageDisplay.setVisible(true);// make it visible
	}
	ImageDisplay(int x, int y){
		imageDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imageDisplay.setTitle("Preview");// window title
		imageDisplay.setIconImage(new ImageIcon("src/icon/icon.png").getImage());// set default icon
		imageDisplay.setSize(500, 500);// set image display window size
		Color bgColor = new Color(0, 0, 0); // black
		imageDisplay.getContentPane().setBackground(bgColor);
		imageDisplay.add(imageLabel);
		imageDisplay.pack();// make size suitable in image
		imageDisplay.setLocation(x, y);
		imageDisplay.setVisible(true);// make it visible
	}
	public void setImage(String filePath) {
		imageLabel.setIcon(new ImageIcon(filePath));
		imageDisplay.pack();// suit for image
	}
}

package idv.jingshing.pixel.fileter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;

public class GuiHelper{
	JFrame mainMenu = new JFrame();// create a mainMenu
	
	public GuiHelper() {
		initUI();
	}
	public static void main(String[] args) {
		GuiHelper gui = new GuiHelper();		
	}
	private void initUI() {
		//	JmainMenu = a GUI window
		mainMenu.setTitle("PixelFilter");
		mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
//		mainMenu.setResizable(false);// keep window from resized
		mainMenu.setSize(500, 500); // set the x and y dimension
		
		// set icon
		ImageIcon image = new ImageIcon("src/icon/icon.png");// create image icon
		mainMenu.setIconImage(image.getImage());
		
		// background
		Color bgColor = new Color(0, 0, 0); // black
		mainMenu.getContentPane().setBackground(bgColor);// change background color
		// border
		Border border = BorderFactory.createLineBorder(Color.white, 3);
		
		
		// label
		String hintSentence = "Please select the image";
		JLabel labelTest = new JLabel();
		labelTest.setText(hintSentence);// set text
		labelTest.setIcon(image);// set image
		labelTest.setHorizontalTextPosition(JLabel.CENTER);// set text LEFT, CENTER, RIGHT
		labelTest.setVerticalTextPosition(JLabel.TOP);//
		labelTest.setForeground(new Color(255, 255, 255));// set text color
		mainMenu.add(labelTest);
		labelTest.setFont(new Font("MV Boli", Font.PLAIN, 20));// text font
		labelTest.setIconTextGap(-25);// set gap of text to image
		labelTest.setBackground(Color.black);
		labelTest.setOpaque(true);//display background color
		labelTest.setBorder(border);
		labelTest.setHorizontalAlignment(JLabel.CENTER);
		labelTest.setVerticalAlignment(JLabel.CENTER);
		labelTest.setBounds(100, 100, 250, 250);
		
//		mainMenu.setLayout(null);
		mainMenu.setVisible(true);// make mainMenu visible
//		mainMenu.pack();
	}
}

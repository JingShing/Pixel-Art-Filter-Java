package idv.jingshing.pixel.filter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiHelper extends JFrame implements ActionListener{
	// this class is for realizing a window for gui and display image
	// components
	JButton transformButton;
	JButton fileSelectButton;
	JButton painterButton;
	JButton filterButton;
	
	JTextField kInput;
	JLabel kLabel;
	JTextField scaleInput;
	JLabel scaleLabel;
	JTextField blurInput;
	JLabel blurLabel;
	JTextField erodeInput;
	JLabel erodeLabel;
	JTextField contrastInput;
	JLabel contrastLabel;
	JTextField saturationInput;
	JLabel saturationLabel;
	
	ImageDisplay imageDisplay;
	
	ImageEditor imageEditor = null;
	DrawGUI painter = null;
	
	String filePath = "";
	// set default image to icon
	public GuiHelper() {
		PixelTransform pixelTransform = new PixelTransform();
		initUI(this);
	}
	public static void main(String[] args) {
		GuiHelper gui = new GuiHelper();		
	}
	private void initUI(JFrame mainMenu) {
		//	JmainMenu = a GUI window
		mainMenu.setTitle("PixelFilter");
		mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
//		mainMenu.setResizable(false);// keep window from resized
		mainMenu.setSize(500, 500); // set the x and y dimension
		mainMenu.setLayout(new GridLayout(10, 2, 10, 0));
		
		// set icon
		ImageIcon image = new ImageIcon("src/icon/icon.png");// create image icon
		mainMenu.setIconImage(image.getImage());
		
		// background
		Color bgColor = new Color(0, 0, 0); // black
		mainMenu.getContentPane().setBackground(bgColor);// change background color
		
//		labelTest(mainMenu);
		setInput(mainMenu);
		
		// button
		transformButton = new JButton("Transform");
		transformButton.addActionListener(this);
		mainMenu.add(transformButton);
		fileSelectButton = new JButton("File Select");
		fileSelectButton.addActionListener(this);
		mainMenu.add(fileSelectButton);
		
		painterButton = new JButton("painter");
		painterButton.addActionListener(this);
		mainMenu.add(painterButton);
		filterButton = new JButton("filters");
		filterButton.addActionListener(this);
		mainMenu.add(filterButton);		
		
		mainMenu.setVisible(true);// make mainMenu visible
//		mainMenu.pack();
		
		imageDisplay = new ImageDisplay();
//		imageDisplay.setImage("test.png");
	}
	private void labelTest(JFrame mainMenu) {
		// label
		ImageIcon image = new ImageIcon("src/icon/icon.png");// create image icon
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

		// border
		Border border = BorderFactory.createLineBorder(Color.white, 3);
		labelTest.setBorder(border);
		labelTest.setHorizontalAlignment(JLabel.CENTER);
		labelTest.setVerticalAlignment(JLabel.CENTER);
		labelTest.setBounds(100, 100, 250, 250);
	}
	
	private void setInput(JFrame frame) {
		// k, scale, blur, erode, contrast, saturation
		Color textColor = new Color(255, 255, 255);
		kInput = new JTextField("3");
		kLabel = new JLabel("Color num:");
		scaleInput = new JTextField("2");
		scaleLabel = new JLabel("Scale:");
		blurInput = new JTextField("0");
		blurLabel = new JLabel("Blur:");
		erodeInput = new JTextField("0");
		erodeLabel = new JLabel("erode:");
		contrastInput = new JTextField("0");
		contrastLabel = new JLabel("Contrast:");
		saturationInput = new JTextField("0");
		saturationLabel = new JLabel("saturation:");
		
		kLabel.setForeground(textColor);
		scaleLabel.setForeground(textColor);
		blurLabel.setForeground(textColor);
		erodeLabel.setForeground(textColor);
		contrastLabel.setForeground(textColor);
		saturationLabel.setForeground(textColor);
		
		frame.add(kLabel);
		frame.add(kInput);
		frame.add(scaleLabel);
		frame.add(scaleInput);
		frame.add(blurLabel);
		frame.add(blurInput);
		frame.add(erodeLabel);
		frame.add(erodeInput);
		frame.add(contrastLabel);
		frame.add(contrastInput);
		frame.add(saturationLabel);
		frame.add(saturationInput);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==transformButton) {
			int k, scale, blur, erode, contrast, saturation;
			k = Integer.parseInt(kInput.getText());
			scale = Integer.parseInt(scaleInput.getText());
			blur = Integer.parseInt(blurInput.getText());
			erode = Integer.parseInt(erodeInput.getText());
			contrast = Integer.parseInt(contrastInput.getText());
			saturation = Integer.parseInt(saturationInput.getText());
			if (filePath != "") {
				String outputFilePath = PixelTransform.pixelTransformProcess(filePath, k, scale, blur, erode, contrast, saturation);
				imageDisplay.setImage(outputFilePath);
			}
		}
		else if(e.getSource()==fileSelectButton) {
			JFileChooser fileChooser = new JFileChooser();
			int response = fileChooser.showOpenDialog(null);
			if(response == JFileChooser.APPROVE_OPTION) {
				filePath = fileChooser.getSelectedFile().getAbsolutePath();
				imageDisplay.setImage(filePath);
			}
		}
		else if(e.getSource()==painterButton) {
			if(painter==null) {
		        DrawGUI check = null;
        		if (filePath=="")check = new DrawGUI("src/icon/icon.png");
        		else check = new DrawGUI(filePath);
		        check.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				check.setVisible(true); // display frame
			}
		}
		else if(e.getSource()==filterButton) {
			if(imageEditor==null) {
		        ImageEditor check = new ImageEditor(filePath);
		        check.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				check.setVisible(true); // display frame
			}
		}
	}
}
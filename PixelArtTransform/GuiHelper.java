package idv.jingshing.pixel.filter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javax.swing.ImageIcon;

import java.awt.Color;
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
	
	String filePath = "src/icon/icon.png";
	// set default image to icon
	public GuiHelper(int x, int y) {
		PixelTransform pixelTransform = new PixelTransform();
		initUI(this, x, y);
	}
	public static void main(String[] args) {
		GuiHelper gui = new GuiHelper(100, 100);
	}
	private void initUI(JFrame mainMenu, int x, int y) {
		//	JmainMenu = a GUI window
		mainMenu.setTitle("PixelFilter");
		mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
//		mainMenu.setResizable(false);// keep window from resized
		mainMenu.setSize(500, 500); // set the x and y dimension
		JPanel mainPanel = new JPanel();
		mainMenu.setLayout(new GridLayout(1, 1));
		mainPanel.setLayout(new GridLayout(10, 2, 10, 10));
		// allign for outbox
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// set icon
		ImageIcon image = new ImageIcon("src/icon/icon.png");// create image icon
		mainMenu.setIconImage(image.getImage());
		
		// background
		Color bgColor = new Color(0, 0, 0); // black
		mainMenu.getContentPane().setBackground(bgColor);// change background color
		mainPanel.setBackground(bgColor);
		
		setInput(mainPanel);
		setButton(mainPanel);
		
		mainMenu.add(mainPanel);
		mainMenu.setLocation(x, y);
		mainMenu.setVisible(true);// make mainMenu visible
//		mainMenu.pack();
		
		imageDisplay = new ImageDisplay(x+500, y+0);
//		imageDisplay.setImage("test.png");
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
	private void setInput(JPanel frame) {
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
	private void setButton(JFrame frame) {
		// button
		transformButton = new JButton("Transform");
		transformButton.addActionListener(this);
		frame.add(transformButton);
		fileSelectButton = new JButton("File Select");
		fileSelectButton.addActionListener(this);
		frame.add(fileSelectButton);
		painterButton = new JButton("painter");
		painterButton.addActionListener(this);
		frame.add(painterButton);
		filterButton = new JButton("filters");
		filterButton.addActionListener(this);
		frame.add(filterButton);
	}
	private void setButton(JPanel frame) {
		// button
		transformButton = new JButton("Transform");
		transformButton.addActionListener(this);
		frame.add(transformButton);
		fileSelectButton = new JButton("File Select");
		fileSelectButton.addActionListener(this);
		frame.add(fileSelectButton);
		painterButton = new JButton("painter");
		painterButton.addActionListener(this);
		frame.add(painterButton);
		filterButton = new JButton("filters");
		filterButton.addActionListener(this);
		frame.add(filterButton);
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
		        DrawGUI painter = null;
        		if (filePath=="")painter = new DrawGUI("src/icon/icon.png");
        		else painter = new DrawGUI(filePath);
        		painter.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        		painter.setVisible(true); // display frame
			}
		}
		else if(e.getSource()==filterButton) {
			if(imageEditor==null) {
		        ImageEditor editor = null;
        		if (filePath=="")editor = new ImageEditor("src/icon/icon.png");
        		else editor = new ImageEditor(filePath);
        		editor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        		editor.setVisible(true); // display frame
			}
		}
	}
}
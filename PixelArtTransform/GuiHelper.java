package idv.jingshing.pixel.fileter;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Color;

public class GuiHelper{
	public static void main(String[] args) {
		//	JFrame = a GUI window
		JFrame frame = new JFrame();// create a frame
		frame.setTitle("PixelFilter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
		frame.setResizable(false);// keep window from resized
		frame.setSize(420, 420); // set the x and y dimension
		frame.setVisible(true);// make frame visible
		
		ImageIcon image = new ImageIcon("src/icon/icon.png");// create image icon
		frame.setIconImage(image.getImage());
		frame.getContentPane().setBackground(new Color(0, 0, 0));// change background color
	}
}

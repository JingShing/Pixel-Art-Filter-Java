package idv.jingshing.pixel.filter;
import javax.swing.JFrame;
import java.util.Arrays;

import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import org.opencv.imgcodecs.Imgcodecs;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
public class drawGUITest {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Imgcodecs.imread("image/or.jpg");
        drawGUI check = new drawGUI(image);
        check.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		check.setSize(400, 200); // set frame size
		check.setVisible(true); // display frame
    }
}

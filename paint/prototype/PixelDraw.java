package idv.jingshing.pixel.filter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.event.MouseEvent;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PixelDraw{
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		String fileSrc = "image/or.jpg";
        Mat imgMat = Imgcodecs.imread(fileSrc);
		
		drawPointByPosistion(imgMat, new Scalar(0, 0, 255), new Point(80, 50), 2);
		drawLine(imgMat, new Scalar(0, 0, 255), new Point(15, 15), new Point(50, 50), 2, 100);
		saveImg(imgMat, "test.png");
	}
	public static void saveImg(Mat image, String fileName) {
        try {
            File outputfile = new File(fileName);
            ImageIO.write(matToBufferedImage(image), "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static void drawPointByMouse(Mat src, Scalar color, MouseEvent position, int pointSize) {
		Imgproc.circle(src, new Point(position.getX(), position.getY()), pointSize/2, color, pointSize/2+1);
	}
	public static void drawPointByPosistion(Mat src, Scalar color, Point position, int pointSize) {
		Imgproc.circle(src, position, pointSize/2, color, pointSize/2+1);
	}
	public static void drawLine(Mat src, Scalar color, Point from, Point to, int pointSize, int density) {
		double tempFromX = from.x;
		double tempFromY = from.y;
		double diffX = (to.x-from.x)/(double)density;
		double diffY = (to.y-from.y)/(double)density;
		while((tempFromX-to.x)*(tempFromX+diffX-to.x)>0 && (tempFromY-to.y)*(tempFromY+diffY-to.y)>0) {
			drawPointByPosistion(src, color, new Point((int)Math.round(tempFromX), (int)Math.round(tempFromY)), pointSize);
			tempFromX+=diffX;
			tempFromY+=diffY;
		}
		drawPointByPosistion(src, color, new Point(tempFromX, tempFromY), pointSize);
		return;
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
}

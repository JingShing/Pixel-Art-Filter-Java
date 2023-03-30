package pixel.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfByte;
import org.opencv.core.TermCriteria;
import org.opencv.core.Size;
import org.opencv.core.Range;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.DataBufferByte;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PixelTransform{
    public static void main(String[] args) {
        saveImg(transform("image/or.jpg", 3, 1, 0), "test.png");
    }

    public static void saveImg(BufferedImage image, String fileName) {
        try {
            File outputfile = new File(fileName);
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage transform(String src, int k, double scale, int blur) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat imgMat = Imgcodecs.imread(src);
        // Imgproc.cvtColor(imgMat, imgMat, Imgproc.COLOR_BGR2RGB);
        // Imgproc.cvtColor(imgMat, imgMat, Imgproc.COLOR_RGB2BGR);
    
        if (blur > 0) {
            Imgproc.bilateralFilter(imgMat, imgMat, 15, blur, 20);
        }
    
        int h = imgMat.height();
        int w = imgMat.width();
        int c = imgMat.channels();
        int d_h = (int) Math.round(h / scale);
        int d_w = (int) Math.round(w / scale);
    
        Mat resizedMat = new Mat();
        Imgproc.resize(imgMat, resizedMat, new Size(d_w, d_h), 0, 0, Imgproc.INTER_NEAREST);    
        
        Mat result = cluster(resizedMat, k).get(0);
        // result = result.reshape(3, d_h);
        Imgproc.resize(result, result, new Size(d_w * scale, d_h * scale), 0, 0, Imgproc.INTER_NEAREST);
        // Imgproc.cvtColor(result, result, Imgproc.COLOR_BGR2RGB);
    
        return matToBufferedImage(result);
    }
    public static List<Mat> cluster(Mat cutout, int k) {
		Mat samples = cutout.reshape(1, cutout.cols() * cutout.rows());
		Mat samples32f = new Mat();
		samples.convertTo(samples32f, CvType.CV_32F, 1.0 / 255.0);
		
		Mat labels = new Mat();
		TermCriteria criteria = new TermCriteria(TermCriteria.COUNT, 100, 1);
		Mat centers = new Mat();
		Core.kmeans(samples32f, k, labels, criteria, 10, Core.KMEANS_PP_CENTERS, centers);		
		return showClusters(cutout, labels, centers);
	}

	private static List<Mat> showClusters (Mat cutout, Mat labels, Mat centers) {
		centers.convertTo(centers, CvType.CV_8UC1, 255.0);
		centers.reshape(3);
		
		List<Mat> clusters = new ArrayList<Mat>();
		for(int i = 0; i < centers.rows(); i++) {
			clusters.add(Mat.zeros(cutout.size(), cutout.type()));
		}
		
		Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for(int i = 0; i < centers.rows(); i++) counts.put(i, 0);
		
		int rows = 0;
		for(int y = 0; y < cutout.rows(); y++) {
			for(int x = 0; x < cutout.cols(); x++) {
				int label = (int)labels.get(rows, 0)[0];
				int r = (int)centers.get(label, 2)[0];
				int g = (int)centers.get(label, 1)[0];
				int b = (int)centers.get(label, 0)[0];
				counts.put(label, counts.get(label) + 1);
				clusters.get(label).put(y, x, b, g, r);
				// clusters.get(label).put(y, x, g, b, r);
				rows++;
			}
		}
		System.out.println(counts);
		return clusters;
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

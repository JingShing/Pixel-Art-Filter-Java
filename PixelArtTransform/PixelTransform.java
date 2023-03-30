package pixel.filter;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
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
        BufferedImage img = matToBufferedImage(imgMat);
    
        Imgproc.cvtColor(imgMat, imgMat, Imgproc.COLOR_BGR2RGB);
        int h = img.getHeight();
        int w = img.getWidth();
        int c = 3;
        int d_h = (int) Math.round(h / scale);
        int d_w = (int) Math.round(w / scale);
        if (blur > 0) {
            Imgproc.bilateralFilter(imgMat, imgMat, 15, blur, 20);
        }
    
        Imgproc.resize(imgMat, imgMat, new org.opencv.core.Size(d_w, d_h), 0, 0, Imgproc.INTER_NEAREST);
        Mat img_cp = imgMat.reshape(1, d_h * d_w);
        img_cp.convertTo(img_cp, CvType.CV_32F);
        TermCriteria criteria = new TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER, 10, 1.0);
        Mat label = new Mat();
        Mat center = new Mat();
        Core.kmeans(img_cp, k, label, criteria, 10, Core.KMEANS_PP_CENTERS, center);
        center.convertTo(center, CvType.CV_8U);
    
        // Use LUT to map the original image to the k-means clustered colors
        // Mat result = new Mat();
        Mat result = new Mat();
        Mat img_reshape = imgMat.reshape(1, d_h * d_w);
        center.convertTo(center, CvType.CV_8U);
        Core.normalize(center, center, 0, 255, Core.NORM_MINMAX, CvType.CV_8U);

        System.out.println(center.toString());
        System.out.println(center.isContinuous());
        System.out.println(center.channels());
        System.out.println(center.total());
        System.out.println("");
        System.out.println(img_reshape.channels());
        System.out.println(img_reshape.total());
        System.out.println("");
        System.out.println(result.channels());
        System.out.println(result.total());
        
        Mat repeatedMat = new Mat(1, 256, CvType.CV_8UC1);
        Core.repeat(center, 1, 256/3, repeatedMat);
        Core.LUT(img_reshape, center, result);

        result = img_reshape;
        result = result.reshape(3, d_h);
        Imgproc.resize(result, result, new org.opencv.core.Size(d_w * scale, d_h * scale), 0, 0, Imgproc.INTER_NEAREST);
    
        return matToBufferedImage(result);
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

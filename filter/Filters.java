package pixel.filter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.core.Size;
import org.opencv.core.Range;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Filters{
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		String fileSrc = "image/or.jpg";
        Mat imgMat = Imgcodecs.imread(fileSrc);
		//Imgproc.cvtColor(imgMat, imgMat, Imgproc.COLOR_BGR2GRAY);
		saveImg(Normalized(imgMat), "test.png");
	}
	
	public static void saveImg(Mat image, String fileName) {
        try {
            File outputfile = new File(fileName);
            ImageIO.write(matToBufferedImage(image), "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    public static double[] get(Mat image, int i, int j) {
        if(i>=image.rows() || i<0 || j>=image.cols() || j<0) return null;
        return image.get(i, j);
    }
	
    public static Mat kuwahara(Mat image, int scale) {
        if(scale>5 || scale<=0) return image;
        Mat output;
        if(image.channels()==3) output = new Mat(image.rows(), image.cols(), CvType.CV_8UC3);
        else output = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
        for(int i=0 ; i<image.rows() ; i++) {
            for(int j=0 ; j<image.cols() ; j++) {
                double[] data=new double[3];
                for(int channel=0 ; channel<image.channels() ; channel++) {
                    double[] avg = new double[4]; Arrays.fill(avg, 0);
                    double[] sii = new double[4]; Arrays.fill(sii, 0);
                    int[] count = new int[4]; Arrays.fill(count, 0);
                    for(int dx=-scale ; dx<=scale ; dx++) {
                        for(int dy=-scale ; dy<=scale ; dy++) {
                            int nowX = i+dx, nowY = j+dy;
                            if(get(image, nowX, nowY)==null) continue;
                            if(dx+dy<=0 && dx-dy<=0) {avg[0]+=get(image, nowX, nowY)[channel]; sii[0]+=get(image, nowX, nowY)[channel]*get(image, nowX, nowY)[channel]; count[0]++;}
                            if(dx+dy>=0 && dx-dy<=0) {avg[1]+=get(image, nowX, nowY)[channel]; sii[1]+=get(image, nowX, nowY)[channel]*get(image, nowX, nowY)[channel]; count[1]++;}
                            if(dx+dy>=0 && dx-dy>=0) {avg[2]+=get(image, nowX, nowY)[channel]; sii[2]+=get(image, nowX, nowY)[channel]*get(image, nowX, nowY)[channel]; count[2]++;}
                            if(dx+dy<=0 && dx-dy>=0) {avg[3]+=get(image, nowX, nowY)[channel]; sii[3]+=get(image, nowX, nowY)[channel]*get(image, nowX, nowY)[channel]; count[3]++;}
                        }
                    }
                    for(int index=0 ; index<4 ; index++) {
                        avg[index] = avg[index]/count[index];
                        sii[index] = sii[index]/count[index]-avg[index]*avg[index];
                    }
                    int minIndex=0; double minData=sii[0];
                    for(int index=0 ; index<4 ; index++) {
                        if(sii[index]<minData) {
                            minData = sii[index]; minIndex = index;
                        }
                    }
                    data[channel] = avg[minIndex];
                }
                if(image.channels()==3) output.put(i, j, data);
                else output.put(i, j, data[0]);
            }
        }
        return output;
    }
	
    public static Mat Normalized(Mat image) {
        double mask[][] = {{0,0,0,8,4}, {2,4,8,4,2}, {1,2,4,2,1}}; int weight = 42;
        Mat output = image.clone();
        for(int i=0 ; i<output.rows() ; i++) {
            for(int j=0 ; j<output.cols() ; j++) {
                double diff[] = new double[3];
                double data[] = get(output, i, j);
                for(int channel=0 ; channel<output.channels() ; channel++) {
                    if(data[channel]>=128) {
                        diff[channel] = data[channel]-255;
                        data[channel] = 255;
                    } else {
                        diff[channel] = data[channel];
                        data[channel] = 0;
                    }
                }
                for(int a=0 ; a<=2 && i+a>=0 && i+a<output.rows() ; a++) {
                    for(int b=-2 ; b<=2 && j+b>=0 && j+b<output.cols() ; b++) {
                        double putData[] = get(output, i+a, j+b);
                        for(int channel=0 ; channel<output.channels() ; channel++) {
                            putData[channel] += diff[channel]*mask[a][b+2]/weight;
                            if(putData[channel]>=256) putData[channel]=255;
                            if(putData[channel]<0) putData[channel]=0;
                        }
                        if(output.channels()==3) output.put(i+a, j+b, putData);
                        else output.put(i+a, j+b, putData[0]);
                    }
                }
                if(output.channels()==3) output.put(i, j, data);
                else output.put(i, j, data[0]);
            }
        }
        return output;
    }
	
    public static Mat Flame(Mat input) {
        Mat output = new Mat();
        input.copyTo(output);
        
        for(int i=0 ; i<output.rows() ; i++) {
            for(int j=0 ; j<output.cols() ; j++) {
                double[] diff = new double[3];
                double[] data = output.get(i, j);
                
                for(int channel=0 ; channel<output.channels() ; channel++) {
                    if(data[channel]>=128) {
                        diff[channel] = data[channel]-255;
                        data[channel] = 255;
                    } else {
                        diff[channel] = data[channel];
                        data[channel] = 0;
                    }
                }
                if(output.channels()==3) output.put(i, j, data);
                else output.put(i, j, data[0]);
            }
        }
        return output;
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

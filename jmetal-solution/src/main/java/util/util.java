package util;

import java.util.Random;

import com.github.davidmoten.rtree.geometry.Point;

public class util {

	public static String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "abcdefghijklmnopqrstuvwxyz";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 } 
	public static float[] handleSquData(Point point,float maxx,float maxy,float minx,float miny)
	{
		
		float[] xyReturn= new float[2];
		xyReturn[0]=Const.pictureSize*(point.x1()-minx)/(maxx-minx);
		xyReturn[1]=Const.pictureSize*(point.y1()-miny)/(maxy-miny);
		return xyReturn;
	}
}

package Rtree;

import java.util.ArrayList;

import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;

import util.util;
/*
 * 闅忔満澧炲姞瀵硅薄鑺傜偣*/
public class RandomObjects {
	public Object objectStart=new Object();
	public Object objectEnd=new Object();
	public int numberofwords=0;
	public ArrayList<Object> randomobjects=new ArrayList<Object>();
	public RectangleMBR MBR= null;
	public RandomObjects(int numberofobjects)
	{
		
		for(int i=0;i<numberofobjects;i++)
		{
			//鍒濆鍖栬妭鐐瑰璞�
			Object newobject= new Object();
			numberofwords=(int)(Math.random()*10);
			//鐢熸垚闅忔満澧炲姞闀垮害涓簁鐨勫叧閿瘝
			String randomString=util.getRandomString(numberofwords);
			newobject.setText(randomString);
			float x1=(float) (Math.random()*100);
			float y1=(float) (Math.random()*100);
			Point newpoint= Geometries.point(x1,y1);
			newobject.point=newpoint;
			randomobjects.add(newobject);
		}
	}

	public RandomObjects(int numberofobjects,Point pointStart,Point pointEnd)
	{
		this.objectEnd.setPoint(pointStart);
		this.objectStart.setPoint(pointEnd);
	
		for(int i=0;i<numberofobjects;i++)
		{
			//鍒濆鍖栬妭鐐瑰璞�
			Object newobject= new Object();
			numberofwords=(int)(Math.random()*10);
			//鐢熸垚闅忔満澧炲姞闀垮害涓簁鐨勫叧閿瘝
			String randomString=util.getRandomString(numberofwords);
			newobject.setText(randomString);
			float x1=(float) (pointStart.x()+Math.random()*(pointEnd.x()-pointStart.x()));
			float y1=(float) (pointStart.y()+Math.random()*(pointEnd.y()-pointStart.y()));
			Point newpoint= Geometries.point(x1,y1);
			newobject.point=newpoint;
			randomobjects.add(newobject);
		}
	}
	public Object getObject(int key)
	{
		Object newobject= new Object();
		newobject=this.randomobjects.get(key);
		return newobject;
	}
	public RectangleMBR getRectMBR(){
		RectangleMBR mbr= new RectangleMBR(0, 0, 0, 0);
		for(int i=0;i<randomobjects.size();i++)
		{
			mbr.accumulateRect(mbr, randomobjects.get(i).getPoint());
		}
		return mbr;
	}

}

package Rtree;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.davidmoten.guavamini.Objects;
import com.github.davidmoten.guavamini.Optional;
import com.github.davidmoten.guavamini.Preconditions;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;
import com.github.davidmoten.rtree.internal.util.ObjectsHelper;

import info.debatty.java.stringsimilarity.Jaccard;

public class RectangleMBR implements Rectangle{

	 	public float x1, y1, x2, y2;
	 	public String text="";
	 	public ArrayList<RectangleMBR> arrRects = new ArrayList<RectangleMBR>();
	    public RectangleMBR(float x1, float y1, float x2, float y2) {
	    	float minx= (x2 >= x1)?x1:x2;
	    	float miny= (y2 >= y1)?y1:y2;
	    	float maxx= (x2 >= x1)?x2:x1;
	    	float maxy= (y2 >= y1)?y2:y1;
	        Preconditions.checkArgument(maxx >= minx);
	        Preconditions.checkArgument(maxy >= miny);
	        this.x1 = minx;
	        this.y1 = miny;
	        this.x2 = maxx;
	        this.y2 = maxy;
	    }

	    static Rectangle create(double x1, double y1, double x2, double y2) {
	        return new RectangleMBR((float) x1, (float) y1, (float) x2, (float) y2);
	    }
	    
	    static Rectangle create(float x1, float y1, float x2, float y2) {
	        return new RectangleMBR(x1, y1, x2, y2);
	    }
	    /*
	     *澧炲姞鍚堝苟涓や釜鐐圭殑瀛楃涓叉柟娉�
	     * 
	
	     */
	    public void acumalateString(String string1,String string2)
	    {
	    	String newstring="";
	    	newstring=string1+" "+string2;
	    	this.text= newstring;
	    }
	    
	    /*
	     *澧炲姞鍚堝苟涓や釜鐐圭殑鏂规硶
	     * 
	
	     */
	    public RectangleMBR accumulatePoints(Point p1,Point p2)
	    {
	    	
			return (RectangleMBR) RectangleMBR.create(p1.x(), p1.y(), p2.x(), p2.y());
	    	
	    }
	    /*
	     *澧炲姞鍚堝苟涓や釜鐭╁舰鐨勬柟娉�
	     * 
	
	     */
	    public void accumulateRect(RectangleMBR r1,Point p2)
	    {
	    	float x1,y1,x2,y2;
	    	x1=min(r1.x1(),p2.x(),r1.x2());
	    	y1=min(r1.y1(),p2.y(),r1.y2());
	    	x2=max(r1.x1(),p2.x(),r1.x2());
	    	y2=max(r1.y1(),p2.y(),r1.y2());
	    	this.x1=x1;
	    	this.y1=y1;
	    	this.x2=x2;
	    	this.y2=y2;
			//return (RectangleMBR) RectangleMBR.create(x1,y1,x2,y2);
	    }
	    /*
	     *澧炲姞璁＄畻浜ゅ弶闈㈢Н鐨勬柟娉�
	     * 
	
	     */
	    public static float crossSqureRect(RectangleMBR r1,RectangleMBR r2)
	    {
	    	float squre=0;
	    	if(min(r1.x1,r1.x2)>max(r2.x1,r2.x2)||max(r1.x1,r1.x2)<min(r2.x1,r2.x2))
	    	{
	    		return squre;
	    	}
	    	else{
	    		squre=middleTwo(r1.x1,r1.x2,r2.x1,r2.x2)*middleTwo(r1.x1,r1.x2,r2.x1,r2.x2);
	    	}
	    	return squre;
	    }
	    /*
	     *澧炲姞璁＄畻鎬婚潰绉殑鏂规硶
	     * 
	
	     */
	    public static float totalSqureRect(RectangleMBR r1,RectangleMBR r2)
	    {
	    	float squre=0;
	    	float maxx=max(max(r1.x1,r2.x1),max(r2.x2,r1.x2));
	    	float maxy= max(max(r1.y1,r2.y1),max(r2.y2,r1.y2));
	    	float minx=min(min(r1.x1,r2.x1),min(r2.x2,r1.x2));
	    	float miny= min(min(r1.y1,r2.y1),min(r2.y2,r1.y2));
	    	squre=(maxx-minx)*(maxy-miny);
	    	return squre;
	    }
	    /*
	     *澧炲姞璁＄畻鎬绘枃鏈浉浼煎害鐨勬柟娉�
	     * 
	
	     */
	    public static float totalSimil(RectangleMBR r1,RectangleMBR r2)
	    {
	    	Jaccard jsc= new Jaccard();
	    	
	    	float squre=(float) (1.0-jsc.distance(r1.getText(), r2.getText()));
	    	
	    	return squre;
	    }
	    
	    /*
	     * (non-Javadoc)
	     * 
	     * @see com.github.davidmoten.rtree.geometry.RectangleI#x1()
	     */
	    public float x1() {
	        return x1;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see com.github.davidmoten.rtree.geometry.RectangleI#y1()
	     */
	    public float y1() {
	        return y1;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see com.github.davidmoten.rtree.geometry.RectangleI#x2()
	     */
	    public float x2() {
	        return x2;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see com.github.davidmoten.rtree.geometry.RectangleI#y2()
	     */
	    public float y2() {
	        return y2;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see com.github.davidmoten.rtree.geometry.RectangleI#area()
	     */
	    public float area() {
	        return (x2 - x1) * (y2 - y1);
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see
	     * com.github.davidmoten.rtree.geometry.RectangleI#add(com.github.davidmoten
	     * .rtree.geometry.Rectangle)
	     */
	    public Rectangle add(Rectangle r) {
	        return new RectangleMBR(min(x1, r.x1()), min(y1, r.y1()), max(x2, r.x2()),
	                max(y2, r.y2()));
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see com.github.davidmoten.rtree.geometry.RectangleI#contains(double,
	     * double)
	     */
	    public boolean contains(double x, double y) {
	        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
	    }

	    public boolean intersects(Rectangle r) {
	        return intersects(x1, y1, x2, y2, r.x1(), r.y1(), r.x2(), r.y2());
	        // return r.x2() >= x1 && r.x1() <= x2 && r.y2() >= y1 && r.y1() <= y2;
	    }

	    public double distance(Rectangle r) {
	        return distance(x1, y1, x2, y2, r.x1(), r.y1(), r.x2(), r.y2());
	        // if (intersects(r))
	        // return 0;
	        // else {
	        // Rectangle mostLeft = x1 < r.x1() ? this : r;
	        // Rectangle mostRight = x1 > r.x1() ? this : r;
	        // double xDifference = max(0,
	        // mostLeft.x1() == mostRight.x1() ? 0 : mostRight.x1() -
	        // mostLeft.x2());
	        //
	        // Rectangle upper = y1 < r.y1() ? this : r;
	        // Rectangle lower = y1 > r.y1() ? this : r;
	        //
	        // double yDifference = max(0, upper.y1() == lower.y1() ? 0 : lower.y1()
	        // - upper.y2());
	        //
	        // return Math.sqrt(xDifference * xDifference + yDifference *
	        // yDifference);
	        // }
	    }

	    public static double distance(float x1, float y1, float x2, float y2, float a1, float b1,
	            float a2, float b2) {
	        if (intersects(x1, y1, x2, y2, a1, b1, a2, b2)) {
	            return 0;
	        }
	        boolean xyMostLeft = x1 < a1;
	        float mostLeftX1 = xyMostLeft ? x1 : a1;
	        float mostRightX1 = xyMostLeft ? a1 : x1;
	        float mostLeftX2 = xyMostLeft ? x2 : a2;
	        double xDifference = max(0, mostLeftX1 == mostRightX1 ? 0 : mostRightX1 - mostLeftX2);

	        boolean xyMostDown = y1 < b1;
	        float mostDownY1 = xyMostDown ? y1 : b1;
	        float mostUpY1 = xyMostDown ? b1 : y1;
	        float mostDownY2 = xyMostDown ? y2 : b2;

	        double yDifference = max(0, mostDownY1 == mostUpY1 ? 0 : mostUpY1 - mostDownY2);

	        return Math.sqrt(xDifference * xDifference + yDifference * yDifference);
	    }

	    private static boolean intersects(float x1, float y1, float x2, float y2, float a1, float b1,
	            float a2, float b2) {
	        return x1 <= a2 && a1 <= x2 && y1 <= b2 && b1 <= y2;
	    }

	    public Rectangle mbr() {
	        return this;
	    }

	    @Override
	    public String toString() {
	        return "Rectangle [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + "]";
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(x1, y1, x2, y2);
	    }

	    public boolean equals(Object obj) {
	        Optional<RectangleMBR> other = ObjectsHelper.asClass(obj, RectangleMBR.class);
	        if (other.isPresent()) {
	            return Objects.equal(x1, other.get().x1) && Objects.equal(x2, other.get().x2)
	                    && Objects.equal(y1, other.get().y1) && Objects.equal(y2, other.get().y2);
	        } else
	            return false;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see
	     * com.github.davidmoten.rtree.geometry.RectangleI#intersectionArea(com.
	     * github.davidmoten.rtree.geometry.Rectangle)
	     */
	    public float intersectionArea(Rectangle r) {
	        if (!intersects(r))
	            return 0;
	        else
	            return create(max(x1, r.x1()), max(y1, r.y1()), min(x2, r.x2()), min(y2, r.y2()))
	                    .area();
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see com.github.davidmoten.rtree.geometry.RectangleI#perimeter()
	     */
	    public float perimeter() {
	        return 2 * (x2 - x1) + 2 * (y2 - y1);
	    }

	    public Geometry geometry() {
	        return this;
	    }

	    private static float max(float a, float b) {
	        if (a < b)
	            return b;
	        else
	            return a;
	    }
	    private static float max(float a, float b,float c) {
	     float d=max(a,b);
	     return max(c,d);
		
	    }
	    //姹備腑闂翠袱涓�肩殑宸�
	    @SuppressWarnings({ "null", "unused" })
		private static float middleTwo(float a, float b,float c,float d) {
	    	float[] middle = new float[4];
		     middle[0]=a;
		     middle[1]=b;
		     middle[2]=c;
		     middle[3]=d;
		     Arrays.sort(middle); 
		     return Math.abs(middle[1]-middle[2]);
			
		    }

	    private static float min(float a, float b) {
	        if (a < b)
	            return a;
	        else
	            return b;
	    }
	    private static float min(float a, float b,float c) {
	     float d=min(a,b);
	     return min(c,d);
	    }

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
}

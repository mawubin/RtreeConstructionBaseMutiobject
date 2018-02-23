package Rtree;

import com.github.davidmoten.rtree.geometry.Point;

public class Object {
	public String text="";
	public Point point;
	public String ID="";

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point= point;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	
	
}

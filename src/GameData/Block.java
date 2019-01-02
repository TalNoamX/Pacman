package GameData;

import Geom.Point3D;

public class Block {
	Point3D topright;
	Point3D bottomleft;
	String id;
	
	public Block(String id,String lat1,String lon1,String alt1,String lat2,String lon2,String alt2) {
		this.id=id;
		topright=new Point3D(Double.parseDouble(lat1), Double.parseDouble(lon1), Double.parseDouble(alt1));
		bottomleft=new Point3D(Double.parseDouble(lat2), Double.parseDouble(lon2), Double.parseDouble(alt2));
	}
	public Point3D GetTop() {
		return topright;
	}
	public Point3D GetBottom() {
		return bottomleft;
	}

	public String GetId() {
		return id;
	}
}

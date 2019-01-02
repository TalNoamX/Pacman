package GameData;

import Geom.Point3D;

public class Player {
	private timeData time;
	private Point3D point;
	private double speed;
	private double radius;
	private final int ID=0;
	private int score;
	
	public Player(double lat ,double lon , double alt, double sp ,double rad) {
		time = new timeData();
		point = new Point3D(lat,lon,alt);
		speed=sp;
		radius=rad;
		score = 0;
	}
	public Player(String lat ,String lon , String alt, String sp ,String rad) {
		time = new timeData();
		double dLat = Double.parseDouble(lat);
		double dAlt = Double.parseDouble(alt);
		double dLon = Double.parseDouble(lon);
		point=new Point3D(dLat,dLon,dAlt);		
		this.setRadius(rad);
		this.setSpeed(sp);
		score = 0;
	}
	
	public Player(Player me) {
		this.time=me.time;
		this.point=new Point3D(me.point);
		this.speed=me.speed;
		this.radius=me.radius;
		this.score=me.score;
	}
	public Point3D getPoint() {
		return point;
	}
	
	public void setPoint(Point3D p) {
		point = new Point3D(p);
	}

	private void setSpeed(String sp) {
		speed = Double.parseDouble(sp);
	}

	private void setRadius(String rad) {
		radius = Double.parseDouble(rad);
	}
}

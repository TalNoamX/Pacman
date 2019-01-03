package GameData;

import Geom.Point3D;

public class Ghost {
	private timeData time;
	private Point3D point;
	private double speed;
	private double radius;
	private int ID;

	/**
	 * regular constructor.
	 * @param id for every "pacman" in the filed.
	 * @param lat is the x coordinate
	 * @param lon is the y coordinate
	 * @param alt is the z coordinate
	 * @param sp
	 * @param rad
	 */
	public Ghost(int id, double lat ,double lon , double alt, double sp ,double rad) {
		time = new timeData();
		point = new Point3D(lat,lon,alt);
		ID=id;
		speed=sp;
		radius=rad;
	}
	/**
	 * A constructor for Strings parameters, taken from a CSV file.
	 * @param id for every "pacman" in the filed.
	 * @param lat is the x coordinate
	 * @param lon is the y coordinate
	 * @param alt is the z coordinate
	 * @param sp
	 * @param rad
	 */
	public Ghost(String id, String lat ,String lon , String alt, String sp ,String rad) {
		time = new timeData();
		double dLat = Double.parseDouble(lat);
		double dAlt = Double.parseDouble(alt);
		double dLon = Double.parseDouble(lon);
		point=new Point3D(dLat,dLon,dAlt);		this.setID(id);
		this.setRadius(rad);
		this.setSpeed(sp);
	}
	/**
	 * Copy constructor
	 * @param Pacman, the pacman we copy from.
	 */
	public Ghost(Ghost ghost) {
		this.time=ghost.time;
		this.point=new Point3D(ghost.point);
		this.ID=ghost.ID;
		this.speed=ghost.speed;
		this.radius=ghost.radius;
	}
	/**
	 * addScore get's a fruit weight and add it to the total pacman's score  
	 * @param weight, is the fruit weight.
	 */
	public void addScore(double weight) {
	}
	/**
	 * Get a time. when we construct new class of this type it save the current time.
	 * @return time stamp.
	 */
	public String GetTime() {
		return time.getTime();
	}
	/**
	 * get pacman location.
	 * @return GPS point.
	 */
	public Point3D getPoint() {
		return point;
	}

	public int getID() {
		return ID;
	}

	public double getSpeed() {
		return speed;
	}

	public double getRadius() {
		return radius;
	}

	public void setPoint(Point3D p) {
		point = new Point3D(p.x(),p.y());
	}

	private void setSpeed(String sp) {
		speed = Double.parseDouble(sp);
	}

	private void setRadius(String rad) {
		radius = Double.parseDouble(rad);
	}

	private void setID(String id) {
		ID = Integer.parseInt(id);
	}

	public void setSpeed(int s) {
		this.speed=s;
	}

	public String toString() {
		return "Pacman info [ "+"Point: "+this.getPoint()+" ID:"+this.getID()+" Speed:"+this.getSpeed()+" Radius:"+this.getRadius()+"]";
	}

}

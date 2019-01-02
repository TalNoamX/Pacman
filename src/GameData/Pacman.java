package GameData;

import Geom.Point3D;
/**
 * A class that represents a "pacman" in the pacman game.
 * @author Oranit
 * @author Tal
 * @author Amitai
 */
public class Pacman {
	private timeData time;
	private Point3D point;
	private double speed;
	private double radius;
	private int ID;
	private int score;

	/**
	 * regular constructor.
	 * @param id for every "pacman" in the filed.
	 * @param lat is the x coordinate
	 * @param lon is the y coordinate
	 * @param alt is the z coordinate
	 * @param sp
	 * @param rad
	 */
	public Pacman(int id, double lat ,double lon , double alt, double sp ,double rad) {
		time = new timeData();
		point = new Point3D(lat,lon,alt);
		ID=id;
		speed=sp;
		radius=rad;
		score = 0;
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
	public Pacman(String id, String lat ,String lon , String alt, String sp ,String rad) {
		time = new timeData();
		double dLat = Double.parseDouble(lat);
		double dAlt = Double.parseDouble(alt);
		double dLon = Double.parseDouble(lon);
		point=new Point3D(dLat,dLon,dAlt);		
		this.setID(id);
		this.setRadius(rad);
		this.setSpeed(sp);
		score = 0;
	}
	/**
	 * Copy constructor
	 * @param Pacman, the pacman we copy from.
	 */
	public Pacman(Pacman pacman) {
		this.time=pacman.time;
		this.point=new Point3D(pacman.point);
		this.ID=pacman.ID;
		this.speed=pacman.speed;
		this.radius=pacman.radius;
		this.score=pacman.score;
	}
	/**
	 * addScore get's a fruit weight and add it to the total pacman's score  
	 * @param weight, is the fruit weight.
	 */
	public void addScore(double weight) {
		score += weight;
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

	public int getScore() {
		return score;
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

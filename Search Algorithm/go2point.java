import java.math.*;

public class go2point {
	private final int earth = 6371;
	Point current;
	Point destination;
	private double currentLat;
	private double currentLong;
	private double destLat;
	private double destLong;
	
	public go2point(Point current,Point destination){
		this.current = current;
		this.destination = destination;
		currentLat = current.getX();
		currentLong = current.getY();
		destLat = destination.getX();
		destLong = destination.getY();
	}
	/**
	 * ‘Haversine’ formula to calculate distance in KM
	 * @return
	 */
	public double distance(){
		double dx = current.getX() - destination.getX();
		double dy = current.getY() - destination.getY();
		return Math.sqrt(dx*dx + dy*dy);
		/*
		double diffLat = Math.toRadians(destLat-currentLat);
		double diffLong = Math.toRadians(destLong-currentLong);
		double lat1 = Math.toRadians(currentLat);
		double lat2 = Math.toRadians(destLat);
		
		double a = Math.sin(diffLat/2) * Math.sin(diffLat/2) + 
				   Math.sin(diffLong/2) * Math.sin(diffLong/2) * Math.cos(lat1) * Math.cos(lat2);
		
		double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		double distance = earth * c;
		
		return distance; //in KM
		*/
	}
	/**
	 * Calculates Relative Bearing in Degrees
	 * Rounds the number to 4 signficant figures
	 * @return
	 */
	public double bearing(){
		double diffLong = Math.toRadians(destLong-currentLong);
		double lat1 = Math.toRadians(currentLat);
		double lat2 = Math.toRadians(destLat);
		
		double y = Math.sin(diffLong) * Math.cos(lat2);
		double x = Math.cos(lat1)*Math.sin(lat2) -
		        Math.sin(lat1)*Math.cos(lat2)*Math.cos(diffLong);
		double brng = Math.atan2(y, x);
		
		double deg = Math.toDegrees(brng);
		System.out.println("The degrees to target is: " + deg);
		//In case the relative bearing is negative, just add 360
		if(deg < 0){
			deg = deg + 360;
		}
		System.out.println("This is the degrees after adjustment: " + deg);
		
		//Rounds the number to 4 significant figures
		double figs = Math.pow(10, Math.ceil(Math.log10(Math.abs(deg))) - 4);
	    return Math.round(deg/figs)*figs;
	}
	/** Creates waypoints along a line based on an interval value 
	 * The interval value is tied into the accuracy of movement of the rotor
	 * 
	 * The starting point should be the current lat/long
	 * The final way point should be the destination lat/long
	 * @param interval The interval period(in kilometers)
	 * @return The number of intervals
	 */
	public Point[] createWayPoints(double interval){
		double dist = distance();
		System.out.println("Interval is: " + interval + "\n");
		int numWayPoints = (int)(dist / interval) + 2; //Add two because we have the initial and final destination built in
		
		System.out.println("Number of waypoints: " + numWayPoints);
		double bearing = bearing();
		
		Point[] wayPoints = new Point[numWayPoints];
		wayPoints[0] = current;
		wayPoints[numWayPoints-1] = destination;	
		
		bearing = Math.toRadians(bearing);
		double x,y;
		Point temp = current;
		for(int i = 1;i<numWayPoints-1;i++){
			y = rounder(interval * Math.sin(bearing),2);
			x = rounder(interval * Math.cos(bearing),2);
			wayPoints[i] = new Point( (temp.getX()+x) , (temp.getY()+y) );
			temp = wayPoints[i];
		}
		
		for(int i = 0;i<numWayPoints;i++){
			System.out.println("Waypoint " + (i+1) + " is : " + wayPoints[i]);
		}
		return wayPoints;
	}
	public double rounder(double number,int sigfigs){
		double figs = Math.pow(10, Math.ceil(Math.log10(Math.abs(number))) - sigfigs);
		return Math.round(number/figs)*figs;
	}
	
	public static void main(String[] args){
		Point one = new Point(0,0);
		Point two = new Point(3,0);
		go2point g = new go2point(one,two);
		
		System.out.println("Distance between points is: " + g.distance());
		System.out.println("Bearing from Point 1 to Point 2 is: " + g.bearing() + " degrees \n");
		//g.createWayPoints(3*Math.sqrt(2));
		g.createWayPoints(1.5);
	}
	
}

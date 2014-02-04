import java.math.*;

public class go2point {
	private final int earth = 6371;
	private double currentLat;
	private double currentLong;
	private double destLat;
	private double destLong;
	
	public go2point(double cla, double clo,double dla,double dlo){
		currentLat = cla;
		currentLong = clo;
		destLat = dla;
		destLong = dlo;
	}
	/**
	 * ‘Haversine’ formula to calculate distance in KM
	 * @return
	 */
	public double distance(){
		double diffLat = Math.toRadians(destLat-currentLat);
		double diffLong = Math.toRadians(destLong-currentLong);
		double lat1 = Math.toRadians(currentLat);
		double lat2 = Math.toRadians(destLat);
		
		double a = Math.sin(diffLat/2) * Math.sin(diffLat/2) + 
				   Math.sin(diffLong/2) * Math.sin(diffLong/2) * Math.cos(lat1) * Math.cos(lat2);
		
		double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		double distance = earth * c;
		
		return distance; //in KM
	}
	/**
	 * Calculates Bearing in Degrees
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
		
		return Math.toDegrees(brng); 
	}
	public static void main(String[] args){
		go2point g = new go2point(38.898556,-77.037852,38.897147,-77.043934);
		go2point ex = new go2point(35,45,35,135);
		
		System.out.println(g.distance());
		System.out.println(ex.bearing());
	}
}

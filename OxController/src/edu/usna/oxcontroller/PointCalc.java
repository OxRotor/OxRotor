package edu.usna.oxcontroller;

import com.google.android.gms.maps.model.LatLng;

public class PointCalc
{
	private final double earthRadius = 6378;
	// The x corrdinate of a PointCalc
    private double x;
    // The y corrdinate of a PointCalc
    private double y;
    

	// Construct a PointCalc at the origin
	public PointCalc()
	{
		x = 0;
		y = 0;
	}
	
	// Construct a PointCalc at the specified location (xVal, yVal)
	public PointCalc(LatLng ll)
	{
		x = ll.latitude;
		y = ll.longitude;
	}
	
	public PointCalc(double xVal, double yVal)
	{
		x = xVal;
		y = yVal;
	}
	
	public LatLng getLL(){
		return new LatLng(x, y);
	}
	// Construct a PointCalc with the same location as the specified PointCalc
	public PointCalc(PointCalc PointCalc) 
	{
		x = PointCalc.x;
		y = PointCalc.y;
	}


	// Get the x corrdinate
	public double getX()
	{
		return x;
	}
	// Get the y corrdinate
	public double getY()
	{
		return y;
	}
	// Set the x corrdinate
	public void setX(double xVal)
	{
		x = xVal;
	}
	// Set the y corrdinate
	public void setY(double yVal)
	{
		y = yVal;
	}


    // Translate a PointCalc to the specified location
    public void translate(PointCalc PointCalc)
    {
		translate(PointCalc.x, PointCalc.y);
    }
    // Translate a PointCalc to the specified location (newX, newY)
    public void translate(double newX, double newY)
    {
		x = newX;
		y = newY;
    }
    // Offset a PointCalc along the x and y axes by dx and dy, respectively
    public void offset(double dx, double dy)
    {
		x += dx;
		y += dy;
    }
    // Calcualte the distance between two PointCalcs
    public double distance(PointCalc point)
    {
    	double R = earthRadius;
    	double dLat = Math.toRadians(point.x-this.x);
    	double dLon = Math.toRadians(point.y-this.y);
    	double lat1 = Math.toRadians(this.x);
    	double lat2 = Math.toRadians(point.x);

    	double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
    	        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
    	return R * c * 1000;
    }
    public LatLng newPoint(LatLng x, double b, double d){
    	double latitude = x.latitude;
    	double longitude = x.longitude;
    	
    	double rad = Math.toRadians(d/earthRadius);
    	latitude = Math.toRadians(latitude);
    	longitude = Math.toRadians(longitude);
    	b = Math.toRadians(b);
    	
        double lat = Math.asin(Math.sin(latitude)*Math.cos(rad) + Math.cos(latitude)*Math.sin(rad)*Math.cos(b));        
        double secondpart = Math.toDegrees(Math.atan2( Math.sin(b) * Math.sin(rad) * Math.cos(latitude),
                Math.cos(rad) - Math.sin(latitude) * Math.sin(lat)
              ));
        double lon = Math.toDegrees(longitude) + Math.toDegrees(secondpart);
        	
        return new LatLng(lat,lon);
    }
    // Calcualte the square of the distance between two PointCalcs
    public double distance2(PointCalc PointCalc)
    {
		double dx = x - PointCalc.x;
		double dy = y - PointCalc.y;
		return (dx*dx + dy*dy);
    }
    // Calculate the middle PointCalc between two PointCalcs
    public PointCalc midPointCalc(PointCalc y)
    {
    	double lat2 = Math.toRadians(y.getX());
    	double dLon = Math.toRadians(y.getY()-this.getY());
    	double lat1 = Math.toRadians(this.getX());
    	double lon1 = Math.toRadians(this.getY());
    	
    	double Bx = Math.cos(lat2) * Math.cos(dLon);
    	double By = Math.cos(lat2) * Math.sin(dLon);
    	double lat3 = Math.atan2(Math.sin(lat1)+Math.sin(lat2),
    	                      Math.sqrt( (Math.cos(lat1)+Bx)*(Math.cos(lat1)+Bx) + By*By ) ); 
    	double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);
    	
    	return new PointCalc(Math.toDegrees(lat3),Math.toDegrees(lon3));
    }   
    // Determine whether two PointCalcs are equal
    public boolean equals(PointCalc PointCalc)
    {
		return (x == PointCalc.x) && (y == PointCalc.y);
    }
    // Return a representation of a PointCalc as a string
    public String toString()
    {
		return "PointCalc = (" + x + "," + y + ")";
    }
}

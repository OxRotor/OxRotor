package edu.usna.oxcontroller;

import com.google.android.gms.maps.model.LatLng;

public class PointCalc
{
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
    public double distance(PointCalc PointCalc)
    {
		double dx = x - PointCalc.x;
		double dy = y - PointCalc.y;
		return Math.sqrt(dx*dx + dy*dy);
    }
    // Calcualte the square of the distance between two PointCalcs
    public double distance2(PointCalc PointCalc)
    {
		double dx = x - PointCalc.x;
		double dy = y - PointCalc.y;
		return (dx*dx + dy*dy);
    }
    // Calculate the middle PointCalc between two PointCalcs
    public PointCalc midPointCalc(PointCalc PointCalc)
    {
		return new PointCalc((x+PointCalc.x)/2, (y+PointCalc.y)/2);
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

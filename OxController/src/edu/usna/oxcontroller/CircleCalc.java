package edu.usna.oxcontroller;

import com.google.android.gms.maps.model.LatLng;

public class CircleCalc  
{
	// The center of a CircleCalc
    private PointCalc p;
    // The radius of a CircleCalc
    private double r;
    

	// Construct a CircleCalc without any specification
	public CircleCalc()
    {
		p = new PointCalc(0,0);
		r = 0;
    }
	// Construct a CircleCalc with the specified CircleCalc
	public CircleCalc(CircleCalc CircleCalc)
    {
		p = new PointCalc(CircleCalc.p);
		r = CircleCalc.r;
    }
	// Construct a CircleCalc with the specified center and radius
	public CircleCalc(PointCalc center, double radius)
    {
		p = new PointCalc(center);
		r = radius;
    }
    // Construct a CircleCalc based on one PointCalc
	public CircleCalc(PointCalc center)
    {
		p = new PointCalc(center);
		r = 0;
    }
    // Construct a CircleCalc based on two PointCalcs
    public CircleCalc(PointCalc p1, PointCalc p2)
    {
		p = p1.midPointCalc(p2);
		r = p1.distance(p);
    }
    // Construct a CircleCalc based on three PointCalcs
    public CircleCalc(PointCalc p1, PointCalc p2, PointCalc p3)
    {
		try
		{
			double x = (p3.getX()*p3.getX() * (p1.getY()-p2.getY()) + (p1.getX()*p1.getX() + (p1.getY()-p2.getY())*(p1.getY()-p3.getY())) 
			          * (p2.getY()-p3.getY()) + p2.getX()*p2.getX() * (-p1.getY()+p3.getY())) 
					  / (2 * (p3.getX() * (p1.getY()-p2.getY()) + p1.getX() * (p2.getY()-p3.getY()) + p2.getX() * (-p1.getY()+p3.getY())));
			double y = (p2.getY()+p3.getY())/2 - (p3.getX() - p2.getX())/(p3.getY()-p2.getY()) * (x - (p2.getX() + p3.getX())/2);	
			p = new PointCalc(x, y);
			r = p.distance(p1);
		}
		catch(Exception e)
		{
		}	
    }
    
	// Get the center
	public LatLng getCenter()
	{
		LatLng ll = new LatLng(p.getX(), p.getY());
		return ll;
	}
	public Double getX(){
		return p.getX();
	}
	public Double getY(){
		return p.getY();
	}
	// Get the radius
	public double getRadius()
	{
		return r;
	}
	// Set the center
	public void setCenter(PointCalc center)
	{
		p.translate(center);
	}
	// Set the radius
	public void setRadius(double radius)
	{
		r = radius;
	}

	// Translate the center of a CircleCalc to a specified PointCalc
    public void translate(PointCalc newCenter)
    {
		p.translate(newCenter);
    }    
    // Offset a CircleCalc along its radius by dr
    public void offset(double dr)
    {
		r += dr;
    }
    // Scale a CircleCalc along its radius by a factor
    public void scale(double factor)
    {
		r *= factor;
    }
    // Calculate the diameter of a CircleCalc
    public double getDiameter()
    {
		return (2*r);
    }
    // Calculate the circumference of a CircleCalc
    public double getCircumference()
    {
		return (Math.PI*2*r);
    }
    // Calcualte the area of a CircleCalc
    public double getArea()
    {
		return (Math.PI*r*r);
    }
	// Is a PointCalc in the CircleCalc
	public int contain(PointCalc PointCalc)
	{
		int answer = 0;
		double d = p.distance(PointCalc);
		if (d > r)
		{
			answer = 1;		// The PointCalc is outside the CircleCalc
		}
		else if (d == r)
		{
			answer = 0;		// The PointCalc is on the circumference of the CircleCalc
		}
		else
		{
			answer = -1;	// The PointCalc is inside the CircleCalc
		}
		return answer;
	}


	// Determine whether two PointCalcs are equal
    public boolean equals(CircleCalc CircleCalc)
    {
		return p.equals(CircleCalc.p) && (r == CircleCalc.r);
    }
    // Return a representation of a PointCalc as a string
    public String toString()
    {
		return "Center = (" + p.getX() + ", " + p.getY() + "); " + "Radius = " + r;
    }
}

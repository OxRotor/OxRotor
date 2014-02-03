

public class mincircle {
	private Point[] points;
	
	public mincircle(Point[] p){
		points = p;
	}
	/**
	 * This code is based on SEC.java
	 * The SEC class computes the Smallest Enclosing circle of a set of 2D points.
	 * 
	 * Created: August 16 2006
	 * Author: Xiumin Diao (xiumin@nmsu.edu)
	 */
	private static Circle findSec(int n, Point[] p, int m, Point[] b)
	{
		Circle sec = new Circle();
		
		// Compute the Smallest Enclosing Circle defined by B
		if(m == 1)
		{
			sec = new Circle(b[0]);
		}
		else if(m == 2)
		{
			sec = new Circle(b[0], b[1]);
		}
		else if(m == 3)
		{
			return new Circle( b[0], b[1], b[2]);
		}
	
		// Check if all the points in p are enclosed
		for(int i=0; i<n; i++)
		{
			if(sec.contain(p[i]) == 1)
			{
				// Compute B <--- B union P[i].
				b[m] = new Point(p[i]);	
				// Recurse
				sec = findSec(i, p, m+1, b);
			}
		}
		return sec;
	}
	public static void main(String [] args){
		Point one = new Point(3,0);
		Point two = new Point(3,1);
		Point three = new Point(2,0);
		Point four = new Point(4,0);
		Point[] points = new Point[4];
		points[0] = one; points[1] = two;points[2] = three;points[3] = four;
		int size = points.length;

		Point[] b = new Point[2];
		
		Circle c = findSec(size,points,0,b);
		System.out.println("Center is located at: " + c.getCenter());
		System.out.println("Area is: " + c.getArea());
		System.out.println("Diameter is: " + c.getDiameter());
		System.out.println("Radius is: " + c.getRadius());
		
	}
}

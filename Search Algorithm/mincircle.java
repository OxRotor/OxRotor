

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
	private static Point[] SER(Circle c){
		Point[] answer = new Point[2];
		double radius = c.getRadius();
		double centerX = c.getCenter().getX();
		double centerY = c.getCenter().getY();
		
		Point upperRight = new Point(centerX + radius, centerY + radius);
		Point lowerLeft = new Point(centerX - radius, centerY - radius);
		
		answer[0] = upperRight;answer[1] = lowerLeft;
		
		return answer;	
	}
	public static void main(String [] args){
		Point one = new Point(0,3);
		Point two = new Point(0,-3);
		Point three = new Point(1,1);
		Point four = new Point(-1,1);
		Point[] points = new Point[4];
		points[0] = one; points[1] = two;points[2] = three;points[3] = four;
		int size = points.length;

		Point[] b = new Point[2];
		
		Circle c = findSec(size,points,0,b);
		System.out.println("Center is located at: " + c.getCenter());
		System.out.println("Area is: " + c.getArea());
		System.out.println("Diameter is: " + c.getDiameter());
		System.out.println("Radius is: " + c.getRadius());
		
		Point[] rectangle = SER(c);
		System.out.println("Upper Right is: " + rectangle[0]);
		System.out.println("Lower Left is: " + rectangle[1]);
		go2point g = new go2point(rectangle[0],rectangle[1]);
		System.out.println("Distance in meters: " + g.distance());
		/**
		 * Draw on Google Maps Code snippet
		 * 
			  CircleOptions circle = new CircleOptions()
              		.center(c.getCenter())   //set center
              		.radius(c.getRadius())   //set radius in meters
              		.fillColor(Color.TRANSPARENT)  //default
              		.strokeColor(0x10000000)
              		.strokeWidth(5);
              myCircle = googleMap.addCircle(circle);
		 */
	}
}
